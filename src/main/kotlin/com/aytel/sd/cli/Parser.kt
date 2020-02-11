package com.aytel.sd.cli

import BashBaseVisitor
import BashLexer
import BashParser
import com.aytel.sd.cli.commands.CmdRunner
import com.aytel.sd.cli.commands.Command
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream

/**
 * Parses commands and visits tokens.
 */
class Parser(private val env: Environment, private val cmdRunner: CmdRunner): BashBaseVisitor<Pair<Command.Status, String>>() {
    override fun visitSetVariable(ctx: BashParser.SetVariableContext): Pair<Command.Status, String> {
        env.set(ctx.`var`.text, ctx.`val`.text)
        return Command.Status.OK to ""
    }

    override fun visitCommands(ctx: BashParser.CommandsContext): Pair<Command.Status, String> {
        var currentOut = ""
        var currentExit = Command.Status.OK
        for (cmd in ctx.command()) {
            val cmdName = cmd.commandName().text
            var parsedArgs = ""

            for (block in cmd.args) {
                val cur = block.text
                parsedArgs += if (cur[0] == '\'' || cur[0] == '"') {
                    cur.substring(1, cur.length - 1)
                } else {
                    cur
                }
            }

            val result: Pair<Command.Status, String> = cmdRunner.run(cmdName,
                parsedArgs.split(" ").filter { it.isNotEmpty() }, currentOut)
            if (result.first == Command.Status.ERR) {
                return result
            }
            if (result.first == Command.Status.EXIT) {
                currentExit = Command.Status.EXIT
            }
            currentOut = result.second
        }
        return currentExit to currentOut
    }

    fun handle(cmd: String): Pair<Command.Status, String> {
        val lexer = BashLexer(CharStreams.fromString(cmd))

        val parser = BashParser(CommonTokenStream(lexer))

        return this.visit(parser.line())
    }
}