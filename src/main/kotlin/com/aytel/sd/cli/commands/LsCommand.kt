package com.aytel.sd.cli.commands

import com.aytel.sd.cli.Environment
import java.io.File
import java.nio.file.Path

/**
 * Returns the contents of a directory.
 * Takes 0 or 1 argument either from stdin or from the argument list.
 * ls with 0 arguments prints the contents of the current directory,
 * with 1 argument prints the contents of the directory specified by the argument.
 */
class LsCommand: Command() {
    override fun run(args: List<String>, stdin: String, env: Environment): Pair<Status, String> {
        return when {
            args.isEmpty() -> {
                list(env.getDirectory(stdin))
            }
            args.size == 1 -> {
                list(env.getDirectory(args[0]))
            }
            else -> {
                Status.ERR to "ls: too many arguments"
            }
        }
    }

    private fun list(directory: Path): Pair<Status, String> {
        return if (!directory.toFile().exists()) {
            Status.ERR to "$directory does not exist"
        } else if (!directory.toFile().isDirectory) {
            Status.ERR to "$directory is not a directory"
        } else {
            Status.OK to (File(directory.toUri()).listFiles()?.joinToString(System.lineSeparator()) { it.name } ?: "")
        }
    }
}
