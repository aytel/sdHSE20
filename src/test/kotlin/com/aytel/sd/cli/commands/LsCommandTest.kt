package com.aytel.sd.cli.commands

import com.aytel.sd.cli.Environment
import org.junit.jupiter.api.Test

class LsCommandTest {
    @Test
    fun fromArgs() {
        CommandTester.testCommand(
            LsCommand(),
            listOf("src/test/resources"),
            "",
            Environment(),
            "TestFile.txt",
            Command.Status.OK
        )
    }

    @Test
    fun fromStdin() {
        CommandTester.testCommand(
            LsCommand(),
            listOf("src/test/resources"),
            "",
            Environment(),
            "TestFile.txt",
            Command.Status.OK
        )
    }

    @Test
    fun nonexistentDirectory() {
        CommandTester.testCommand(
            LsCommand(),
            listOf("source"),
            "",
            Environment(),
            "source does not exist",
            Command.Status.ERR
        )
    }

    @Test
    fun file() {
        CommandTester.testCommand(
            LsCommand(),
            listOf("gradlew"),
            "",
            Environment(),
            "gradlew is not a directory",
            Command.Status.ERR
        )
    }

    @Test
    fun sameDirectory() {
        val testEnvironment = Environment()
        testEnvironment.setDirectory(testEnvironment.getPath("/src/test/resources").toString())
        CommandTester.testCommand(
            LsCommand(),
            listOf(),
            ".",
            testEnvironment,
            "TestFile.txt",
            Command.Status.OK
        )
    }

    @Test
    fun sameDirectoryNoArguments() {
        val testEnvironment = Environment()
        testEnvironment.setDirectory(testEnvironment.getPath("/src/test/resources").toString())
        CommandTester.testCommand(
            LsCommand(),
            listOf(),
            "",
            testEnvironment,
            "TestFile.txt",
            Command.Status.OK
        )
    }

    @Test
    fun longPathSpec() {
        CommandTester.testCommand(
            LsCommand(),
            listOf(),
            "./src/test/../test/resources",
            Environment(),
            "TestFile.txt",
            Command.Status.OK
        )
    }

    @Test
    fun tooManyArguments() {
        CommandTester.testCommand(
            LsCommand(),
            listOf("src", "src"),
            "",
            Environment(),
            "ls: too many arguments",
            Command.Status.ERR
        )
    }

    @Test
    fun severalFiles() {
        CommandTester.testCommand(
            LsCommand(),
            listOf("src/test/"),
            "",
            Environment(),
            "kotlin" + System.lineSeparator() +
                    "resources",
            Command.Status.OK
        )
    }
}