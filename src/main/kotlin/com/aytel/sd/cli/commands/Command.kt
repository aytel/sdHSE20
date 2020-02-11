package com.aytel.sd.cli.commands

import com.aytel.sd.cli.Environment

/**
 * Base class for commands.
 */
abstract class Command {
    /**
     * Returns result or running command.
     * Second member of return value is command's output.
     * First is status:
     * [Status.OK] – everything is ok, continue.
     * [Status.EXIT] – everything is ok, but CLI should be stopped.
     * [Status.ERR] – Something's gone wrong.
     */
    abstract fun run(args: List<String>, stdin: String, env: Environment): Pair<Status, String>

    enum class Status {
        OK,
        ERR,
        EXIT
    }
}