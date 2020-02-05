package com.aytel.sd.cli.commands

import com.aytel.sd.cli.Environment
import org.junit.jupiter.api.Test

class WcCommandTest {
    @Test
    fun testFile() {
        CommandTester.testCommand(
            WcCommand(),
            listOf(CommandTester.pathToFile),
            "any",
            Environment(),
            "22 4 3",
            Command.Status.OK
        )
    }

    @Test
    fun testPipe() {
        CommandTester.testCommand(
            WcCommand(),
            listOf(),
            "text\nwith spaces",
            Environment(),
            "16 3 2",
            Command.Status.OK
        )
    }
}