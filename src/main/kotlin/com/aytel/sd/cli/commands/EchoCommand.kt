package com.aytel.sd.cli.commands

import com.aytel.sd.cli.Environment

/**
 * Returns its arguments joined with spaces.
 */
class EchoCommand: Command() {
    override fun run(args: List<String>, stdin: String, env: Environment): Pair<Status, String> {
        return Status.OK to args.joinToString(" ") { it }
    }
}