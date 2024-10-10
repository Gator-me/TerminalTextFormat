package com.gatorme.text

import com.gatorme.enum.ColorOption
import com.gatorme.enum.TextOption
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

        val options =
            this.config.colorOptions.flatMap {
                listOf(it.key.code, RGB_ARG, it.value.red, it.value.green, it.value.blue)
            } + this.config.textOptions.map { it.code }

        format.append(options.joinToString(separator = SEP))
        format.append(M)

        return format.toString()
    }

    /**
     * Resets enabled options for color and text.
     */
    open fun reset(
        colorOption: ColorOption? = null,
        textOption: TextOption? = null,
        all: Boolean = false,
    ) {
        if (colorOption != null) {
            this.config.colorOptions -= colorOption
            print("$ESC${colorOption.reset}$M")
        }
        if (textOption != null) {
            this.config.textOptions -= textOption
            print("$ESC${textOption.reset}$M")
        }
        // keep printing to the same stream
        if (all) {
            this.config.colorOptions = mapOf()
            this.config.textOptions = setOf()
            print(ANSI_RESET)
        }
    }
}
