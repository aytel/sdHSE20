package com.aytel.sd.cli.commands

import com.aytel.sd.cli.Environment
import com.ibm.icu.impl.locale.AsciiUtil
import org.apache.commons.cli.*
import java.io.File

class GrepCommand: Command() {
    private val options = Options()
    val parser = DefaultParser()

    init {
        options.addOption("i", "i", false, "case insensitive")
        options.addOption("w", "w", false, "search for whole words")
        options.addOption("A", "A", true, "show first n lines")
    }

    override fun run(args: List<String>, stdin: String, env: Environment): Pair<Status, String> {
        val cmd = parser.parse(options, args.toTypedArray())
        val caseInsensitive = cmd.hasOption("i")
        val wholeWords = cmd.hasOption("w")
        val n = cmd.getOptionValue("A", "-1").toInt()

        if (cmd.args.isEmpty() || cmd.args[0].isEmpty()) {
            return Status.ERR to "no pattern provided"
        }

        val ans = if (cmd.args.size == 1) {
            grep(cmd.args[0], caseInsensitive, wholeWords, stdin)
        } else {
            IntRange(1, cmd.args.size - 1).map {
                grep(cmd.args[0], caseInsensitive, wholeWords,
                    File(cmd.args[it]).readText(), if (cmd.args.size > 2) cmd.args[it] + ":" else "")
            }.flatten()
        }

        return Status.OK to (if (n == -1) {
            ans
        } else {
            ans.subList(0, n)
        }).joinToString("\n")
    }

    private fun grep(pattern: String, caseInsensitive: Boolean, wholeWords: Boolean,
                     text: String, addName: String = ""): List<String> {
        val strings = text.split("\n")
        val result = mutableListOf<String>()

        val regex = Regex(pattern,
            if (caseInsensitive) setOf(RegexOption.IGNORE_CASE) else setOf() )

        for (string in strings) {
            if (if (wholeWords) {
                    string.split(" ", "\t").any { regex.find(it)?.value == it }
            } else {
                regex.find(string) != null
                }) {
                result.add(string)
            }
        }

        return result.map { addName + it }
    }
}