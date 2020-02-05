package com.aytel.sd.cli.commands

import com.aytel.sd.cli.Environment
import org.junit.jupiter.api.Test

class EchoCommandTest {
    @Test
    fun emptyArgs() {
        CommandTester.testCommand(
            EchoCommand(),
            listOf(),
            "any",
            Environment(),
            "",
            Command.Status.OK
        )
    }

    @Test
    fun singleArg() {
        CommandTester.testCommand(
            EchoCommand(),
            listOf("arg"),
            "any",
            Environment(),
            "arg",
            Command.Status.OK
        )
    }

    @Test
    fun twoArgs() {
        CommandTester.testCommand(
            EchoCommand(),
            listOf("arg", "anotherArg"),
            "any",
            Environment(),
            "arg anotherArg",
            Command.Status.OK
        )
    }
}