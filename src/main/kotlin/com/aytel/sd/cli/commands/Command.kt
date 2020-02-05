package com.aytel.sd.cli.commands

import com.aytel.sd.cli.Environment

abstract class Command {
    abstract fun run(args: List<String>, stdin: String, env: Environment): Pair<Status, String>

    enum class Status {
        OK,
        ERR,
        EXIT
    }
}