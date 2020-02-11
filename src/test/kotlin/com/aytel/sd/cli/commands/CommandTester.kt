package com.aytel.sd.cli.commands

import com.aytel.sd.cli.Environment
import org.junit.jupiter.api.Assertions.assertEquals
import java.io.File

class CommandTester {
    companion object {
        val pathToFile = "src" + File.separator + "test" + File.separator +
            "resources" + File.separator + "TestFile.txt"

        fun testCommand(cmd: Command, args: List<String>,
                        stdin: String, env: Environment,
                        expectedOutput: String, expectedStatus: Command.Status) {
            val (status, output) = cmd.run(args, stdin, env)
            assertEquals(expectedStatus, status)
            if (status == Command.Status.OK) {
                assertEquals(expectedOutput, output)
            }
        }
    }
}