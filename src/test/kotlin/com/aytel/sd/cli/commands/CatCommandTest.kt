package com.aytel.sd.cli.commands

import com.aytel.sd.cli.Environment
import org.junit.jupiter.api.Test

class CatCommandTest {
    @Test
    fun testFile() {
        CommandTester.testCommand(
            CatCommand(),
            listOf(CommandTester.pathToFile),
            "any",
            Environment(),
            "string\nanother string\n",
            Command.Status.OK
        )
    }

    @Test
    fun testPipe() {
        CommandTester.testCommand(
            CatCommand(),
            listOf(),
            "text\nwith spaces",
            Environment(),
            "text\nwith spaces",
            Command.Status.OK
        )
    }
}