package com.aytel.sd.cli.commands

import com.aytel.sd.cli.Environment

/**
 * Counts number of lines, words and bytes in input (or in file, given in args).
 */
class WcCommand: Command() {
    override fun run(args: List<String>, stdin: String, env: Environment): Pair<Status, String> {
        if (args.isEmpty()) {
            val (bytes, words, lines) = count(stdin)
            return Status.OK to "$lines $words $bytes"
        } else {
            return try {
                var bytes = 0
                var words = 0
                var lines = 0

                for (name in args) {
                    val (cbytes, cwords, clines) = count(env.getPath(name).toFile().readText())
                    bytes += cbytes
                    words += cwords
                    lines += clines
                }
                Status.OK to "$lines $words $bytes"
            } catch (e: Exception) {
                Status.ERR to (e.message ?: "")
            }
        }
    }

    private fun count(s: String): Triple<Int, Int, Int> {
        val bytes = s.length
        val words = s.count {
            when (it) {
                ' ', System.lineSeparator()[0] -> true
                else -> false
            }
        } + 1
        val lines = s.count { it == System.lineSeparator()[0] } + 1
        return Triple(lines, words, bytes)
    }
}