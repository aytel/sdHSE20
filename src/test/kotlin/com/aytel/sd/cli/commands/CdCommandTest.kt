package com.aytel.sd.cli.commands

import com.aytel.sd.cli.Environment
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class CdCommandTest {
    @Test
    fun fromArgs() {
        val environment = Environment()
        val oldPath: String = environment.getDirectory().toString()
        CommandTester.testCommand(
            CdCommand(),
            listOf("src/test/resources"),
            "",
            environment,
            "",
            Command.Status.OK
        )
        assertEquals("$oldPath/src/test/resources", environment.getDirectory().toString())
    }

    @Test
    fun fromStdin() {
        val environment = Environment()
        val oldPath: String = environment.getDirectory().toString()
        CommandTester.testCommand(
            CdCommand(),
            listOf("src/test/resources"),
            "",
            environment,
            "",
            Command.Status.OK
        )
        assertEquals("$oldPath/src/test/resources", environment.getDirectory().toString())
    }

    @Test
    fun nonexistentDirectory() {
        val environment = Environment()
        val oldPath: String = environment.getDirectory().toString()
        CommandTester.testCommand(
            CdCommand(),
            listOf("source"),
            "",
            environment,
            "source does not exist",
            Command.Status.ERR
        )
        assertEquals(oldPath, environment.getDirectory().toString())
    }

    @Test
    fun file() {
        val environment = Environment()
        val oldPath: String = environment.getDirectory().toString()
        CommandTester.testCommand(
            CdCommand(),
            listOf("gradlew"),
            "",
            environment,
            "gradlew is not a directory",
            Command.Status.ERR
        )
        assertEquals(oldPath, environment.getDirectory().toString())
    }

    @Test
    fun sameDirectory() {
        val environment = Environment()
        val oldPath: String = environment.getDirectory().toString()
        CommandTester.testCommand(
            CdCommand(),
            listOf(),
            ".",
            environment,
            "",
            Command.Status.OK
        )
        assertEquals("$oldPath/.", environment.getDirectory().toString())
    }

    @Test
    fun sameDirectoryNoArguments() {
        val environment = Environment()
        val oldPath: String = environment.getDirectory().toString()
        CommandTester.testCommand(
            CdCommand(),
            listOf(),
            "",
            environment,
            "",
            Command.Status.OK
        )
        assertEquals(oldPath, environment.getDirectory().toString())
    }

    @Test
    fun longPathSpec() {
        val environment = Environment()
        val oldPath: String = environment.getDirectory().toString()
        CommandTester.testCommand(
            CdCommand(),
            listOf(),
            "./src/test/../test/resources",
            environment,
            "",
            Command.Status.OK
        )
        assertEquals("$oldPath/./src/test/../test/resources", environment.getDirectory().toString())
    }

    @Test
    fun tooManyArguments() {
        val environment = Environment()
        val oldPath: String = environment.getDirectory().toString()
        CommandTester.testCommand(
            CdCommand(),
            listOf("src", "src"),
            "",
            environment,
            "cd: too many arguments",
            Command.Status.ERR
        )
        assertEquals(oldPath, environment.getDirectory().toString())
    }

    @Test
    fun severalCds() {
        val environment = Environment()
        val oldPath: String = environment.getDirectory().toString()
        CommandTester.testCommand(
            CdCommand(),
            listOf("src/test/resources"),
            "",
            environment,
            "",
            Command.Status.OK
        )
        assertEquals("$oldPath/src/test/resources", environment.getDirectory().toString())
        CommandTester.testCommand(
            CdCommand(),
            listOf(),
            "..",
            environment,
            "",
            Command.Status.OK
        )
        assertEquals("$oldPath/src/test/resources/..", environment.getDirectory().toString())
    }
}