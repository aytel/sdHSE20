package com.aytel.sd.cli.commands

import com.aytel.sd.cli.Environment

/**
 * Return path of current directory.
 */
class PwdCommand: Command() {
    override fun run(args: List<String>, stdin: String, env: Environment): Pair<Status, String> {
        return Status.OK to env.getPath().toString()
    }
}