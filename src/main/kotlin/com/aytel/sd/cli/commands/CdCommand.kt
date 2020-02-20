package com.aytel.sd.cli.commands

import com.aytel.sd.cli.Environment
import java.nio.file.Path

/**
 * Changes the current working directory.
 * Takes 0 or 1 argument either from stdin or from the argument list.
 * cd with 0 arguments does nothing, with 1 argument attempts to change
 * the current working directory to the one specified by the argument.
 */
class CdCommand: Command() {
    override fun run(args: List<String>, stdin: String, env: Environment): Pair<Status, String> {
        return when {
            args.isEmpty() -> {
                changeDirectory(env, stdin)
            }
            args.size == 1 -> {
                changeDirectory(env, args[0])
            }
            else -> {
                Status.ERR to "cd: too many arguments"
            }
        }
    }

    private fun changeDirectory(env: Environment, pathAddition: String): Pair<Status, String> {
        val newDirectory: Path = env.getPath(pathAddition)
        return if (!newDirectory.toFile().exists()) {
            Status.ERR to "$newDirectory does not exist"
        } else if (!newDirectory.toFile().isDirectory) {
            Status.ERR to "$newDirectory is not a directory"
        } else {
            env.setDirectory(newDirectory.toString())
            Status.OK to ""
        }
    }
}
