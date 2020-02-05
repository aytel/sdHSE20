package com.aytel.sd.cli.commands

import com.aytel.sd.cli.Environment
import java.nio.file.FileSystems

class PwdCommand: Command() {
    override fun run(args: List<String>, stdin: String, env: Environment): Pair<Status, String> {
        return Status.OK to FileSystems.getDefault().getPath(".").toAbsolutePath().toString()
    }
}