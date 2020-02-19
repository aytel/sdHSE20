package com.aytel.sd.cli.commands

import com.aytel.sd.cli.Environment
import java.io.File

/**
 * Returns text of given file.
 */
class CatCommand: Command() {
    override fun run(args: List<String>, stdin: String, env: Environment): Pair<Status, String> {
        return if (args.isEmpty()) {
            Status.OK to stdin
        } else {
            try {
                val result = args.joinToString("\n") {
                    env.getDirectory(it).toFile().readText()
                }
                Status.OK to result
            } catch (e: Exception) {
                Status.ERR to (e.message ?: "")
            }
        }
    }
}