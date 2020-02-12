package com.aytel.sd.cli.commands

import com.aytel.sd.cli.Environment
import org.junit.jupiter.api.Test

class GrepCommandTest {
    @Test
    fun testFile() {
        CommandTester.testCommand(
            GrepCommand(),
            listOf("string", CommandTester.pathToFile),
            "any",
            Environment(),
            "string\nanother string",
            Command.Status.OK
        )
    }

    @Test
    fun testFiles() {
        CommandTester.testCommand(
            GrepCommand(),
            listOf("s.r", CommandTester.pathToFile, CommandTester.pathToFile),
            "any",
            Environment(),
            CommandTester.pathToFile + ":" + "string\n" +
                    CommandTester.pathToFile + ":" + "another string\n" +
                    CommandTester.pathToFile + ":" + "string\n" +
                    CommandTester.pathToFile + ":" + "another string",
            Command.Status.OK
        )
    }

    @Test
    fun testPipe() {
        CommandTester.testCommand(
            GrepCommand(),
            listOf("-w", "-i", "A"),
            "a\nA\naa A\nab",
            Environment(),
            "a\nA\naa A",
            Command.Status.OK
        )
    }
}