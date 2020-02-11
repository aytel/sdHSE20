package com.aytel.sd.cli.commands

import com.aytel.sd.cli.Environment

/**
 * Stops CLI.
 */
class ExitCommand: Command() {
    override fun run(args: List<String>, stdin: String, env: Environment): Pair<Status, String> {
        return Status.EXIT to ""
    }
}