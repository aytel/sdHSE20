package com.aytel.sd.cli

import com.aytel.sd.cli.commands.*
import java.io.*

class CLI {
    companion object {
        private val env: Environment = Environment()

        private val cmdMap = mapOf<String, Command>(
            "cat" to CatCommand(),
            "echo" to EchoCommand(),
            "wc" to WcCommand(),
            "pwd" to PwdCommand(),
            "exit" to ExitCommand()
        )

        private val parser: Parser = Parser(env, CmdRunner(cmdMap, env))
        private val preprocessor = Preprocessor(env)

        fun handle(cmd: String, outputStream: PrintStream, errorStream: PrintStream): Boolean {
            try {
                val preprocessed = preprocessor.handle(cmd)
                val (status: Command.Status, result: String) = parser.handle(preprocessed)
                outputStream.println(result)
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
    }
}

fun main(args: Array<String>) {
    CLI.run()
}