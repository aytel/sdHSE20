package com.aytel.sd.cli

/**
 * Makes substitutions of variables.
 */
class Preprocessor(private val env: Environment) {
    fun handle(cmd: String): String {
        var result = ""
        var quotes = ' '

        val stop = setOf<Char>(
            '\n',
            ' ',
            '\'',
            '"'
        )

        var i = 0
        while (i < cmd.length) {
            val c = cmd[i]
            when (c) {
                '$' -> {
                    if (quotes == '\'') {
                        result += c
                    } else {
                        var j = i + 1
                        while ((j + 1 < cmd.length) && (!stop.contains(cmd[j + 1]))) {
                            j++
                        }
                        result += env.get(cmd.substring(i + 1, j + 1))
                        i = j
                    }
                }
                '\'', '"' -> {
                    when (quotes) {
                        ' ' -> quotes = c
                        c -> quotes = ' '
                        else -> result += c
                    }
                }
                else -> result += c
            }
            i++
        }
        return result
    }

}
