package com.aytel.sd.cli

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import java.lang.System.getProperty
import java.nio.file.FileSystems

class CompositeTest {
    private fun testCmd(cmd: String, expectedOutput: String, expectedError: Boolean) {
        val baos = ByteArrayOutputStream()
        val baes = ByteArrayOutputStream()
        val outputStream = PrintStream(baos)
        val errorStream = PrintStream(baes)
        CLI.handle(cmd, outputStream, errorStream)
        assertEquals(expectedOutput, String(baos.toByteArray()))
        assertEquals(expectedError, baes.toByteArray().isNotEmpty())
    }

    @BeforeEach
    fun clear() {
        CLI.resetEnv()
    }

    @Test
    fun testSimplePipe() {
        testCmd(
            "echo str | cat",
            "str\n",
            false
        )
    }

    @Test
    fun testSimpleEnv() {
        testCmd(
            "A=str",
            "\n",
            false
        )
        testCmd("echo \$A",
            "str\n",
            false
        )
    }

    @Test
    fun testQuotes() {
        testCmd(
            "A=str",
            "\n",
            false
        )
        testCmd("echo B\"\$A\"\$A",
            "Bstrstr\n",
            false
        )
        testCmd("echo B'\$AB",
            "B\$AB\n",
            false
        )
    }

    @Test
    fun testError() {
        testCmd(
            "cat NoSuchFile",
            "",
            true
        )
    }

    @Test
    fun testLocationDependentCatAfterCd() {
        testCmd(
            "cd src/test/resources | cat TestFile.txt",
            "string\n" +
                    "another string\n" + System.lineSeparator(),
            false
        )
    }

    @Test
    fun testLocationDependentExternalAfterCd() {
        if (getProperty("os.name") == "Linux") {
            testCmd(
                "cd src/test/resources | find TestFile.txt",
                "TestFile.txt" + System.lineSeparator() + System.lineSeparator(),
                false
            )
        }
    }

    @Test
    fun testLocationDependentLsAfterCd() {
        testCmd(
            "cd src/test/resources | ls .",
            "TestFile.txt" + System.lineSeparator(),
            false
        )
    }

    @Test
    fun testLocationDependentPwdAfterCd() {
        val currentPath: String =  FileSystems.getDefault().getPath(".").toAbsolutePath().normalize().toString()
        testCmd(
            "cd src/test/resources | pwd",
            currentPath + "/src/test/resources" + System.lineSeparator(),
            false
        )
    }

    @Test
    fun testLocationDependentWcAfterCd() {
        testCmd(
            "cd src/test/resources | wc TestFile.txt",
            "22 4 3" + System.lineSeparator(),
            false
        )
    }
}