package com.aytel.sd.cli.commands

import com.aytel.sd.cli.Environment

/**
 * Runs command which CLI doesn't have in itself.
 */
class ExternalCommand {
    fun runCmd(cmdName: String, args: List<String>, stdin: String, env: Environment): Pair<Command.Status, String> {
        val processBuilder = ProcessBuilder(mutableListOf(cmdName).apply { addAll(args) })
        processBuilder.environment().putAll(env.map())
        val process = processBuilder.start()
        process.outputStream.write(stdin.toByteArray())
        val result = process.waitFor() != 0
        return if (result) {
            Command.Status.ERR to String(process.errorStream.readBytes())
        } else {
            Command.Status.OK to String(process.inputStream.readBytes())
        }
    }
}