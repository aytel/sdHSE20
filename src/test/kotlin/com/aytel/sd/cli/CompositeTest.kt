package com.aytel.sd.cli

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import java.nio.charset.Charset

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
    fun testLocationDependentCommandsAfterCd() {
        testCmd(
            "cd src/test/resources | cat TestFile.txt",
            "string\n" +
                    "another string\n" + System.lineSeparator(),
            false
        )
    }
}