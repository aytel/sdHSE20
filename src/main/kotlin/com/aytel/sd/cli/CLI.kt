package com.aytel.sd.cli

import com.aytel.sd.cli.commands.*
import java.io.*

/**
 * Main class to run CLI.
 */
class CLI {
    companion object {
        private val env: Environment = Environment()

        private val cmdMap = mapOf<String, Command>(
            "cat" to CatCommand(),
            "echo" to EchoCommand(),
            "wc" to WcCommand(),
            "pwd" to PwdCommand(),
            "exit" to ExitCommand(),
            "cd" to CdCommand(),
            "ls" to LsCommand()
        )

        private val parser: Parser = Parser(env, CmdRunner(cmdMap, env))
        private val preprocessor = Preprocessor(env)

        /**
         * Handles [cmd]. Prints its stdout to [outputStream] and its stderr to [errorStream].
         */
        fun handle(cmd: String, outputStream: PrintStream, errorStream: PrintStream): Boolean {
            try {
                val preprocessed = preprocessor.handle(cmd)
                val (status: Command.Status, result: String) = parser.handle(preprocessed)
                if (status == Command.Status.ERR) {
                    errorStream.println(result)
                } else {
                    outputStream.println(result)
                }
                if (status == Command.Status.EXIT) {
                    return true
                }
            } catch (e: Exception) {
                errorStream.println(e.message)
            }
            outputStream.flush()
            errorStream.flush()
            return false
        }

        /**
         * Reads and runs command from [inputStream].
         * Prints their stdout to [outputStream] and its stderr to [errorStream].
         */
        fun run(inputStream: InputStream = System.`in`, outputStream: PrintStream = System.out,
                errorStream: PrintStream = System.err) {
            while (true) {
                val reader = BufferedReader(InputStreamReader(inputStream))
                val input = reader.readLine() ?: continue
                if (input.isEmpty()) {
                    outputStream.println()
                    continue
                }
                if (handle(input, outputStream, errorStream)) {
                    return
                }
            }
        }

        /**
         * Clears environment.
         */
        fun resetEnv() {
            env.map().clear()
            env.resetDirectory()
        }
    }
}

fun main(args: Array<String>) {
    CLI.run()
}