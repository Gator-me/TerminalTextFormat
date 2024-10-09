package com.gatorme.text

import com.gatorme.model.TextFormatConfig

open class TextFormat(private val config: TextFormatConfig = TextFormatConfig()) {
    companion object AnsiArgs {
        const val ANSI_RESET = "\u001b[0m"
        const val ESC = "\u001b["
        const val SEP = ";"
        const val M = "m"
        const val RGB_ARG = "2"
    }

    fun getAnsiEscapeSequence(): String {
        val format = StringBuilder(ESC)

        val options = this.config.colorOptions.flatMap {
            listOf(it.key.code, RGB_ARG, it.value.red, it.value.green, it.value.blue)
        } + this.config.textOptions.map { it.code }

        format.append(options.joinToString(separator = SEP))
        format.append(M)

        return format.toString()
    }
}