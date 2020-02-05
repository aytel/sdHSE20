package com.aytel.sd.cli.commands

import com.aytel.sd.cli.Environment

class CmdRunner(private val map: Map<String, Command>, private val env: Environment) {
    fun run(cmdName: String, args: List<String>, stdin: String): Pair<Command.Status, String> {
        return if (map.containsKey(cmdName)) {
            map.getValue(cmdName).run(args, stdin, env)
        } else {
            ExternalCommand().runCmd(cmdName, args, stdin, env)
        }
    }
}
