package com.gatorme.text

import com.gatorme.enum.ColorOption
import com.gatorme.enum.TextOption
import java.awt.Color

open class TextFormat(private val colorOptions: Map<ColorOption, Color> = mapOf(),
                      private val textOptions: List<TextOption> = listOf(TextOption.REVERSE_VIDEO)) {
    companion object AnsiArgs {
        const val ANSI_RESET = "\u001b[0m"
        const val ESC = "\u001b["
        const val SEP = ";"
        const val M = "m"
        const val RGB_ARG = "2"
    }

    fun getAnsiEscapeSequence(colorOptions: Map<ColorOption, Color> = this.colorOptions,
                                         textOptions: List<TextOption> = this.textOptions): String {
        val format = StringBuilder(ESC)


        val options = colorOptions.flatMap {
            listOf(it.key.code, RGB_ARG, it.value.red, it.value.green, it.value.blue)
        } + textOptions.map { it.code }

        format.append(options.joinToString(separator = SEP))
        format.append(M)

        return format.toString()
    }
}