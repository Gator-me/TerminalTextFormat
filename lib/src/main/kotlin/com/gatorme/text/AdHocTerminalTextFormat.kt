package com.gatorme.text

import com.gatorme.enum.ColorOption
import com.gatorme.enum.TextOption
import java.awt.Color
import java.io.PrintStream

sealed class AdHocTerminalTextFormat: TextFormat() {
    companion object {
        @JvmStatic
        fun print(text: String, textColor: Color? = null,
                           backgroundColor: Color? = null,
                           textOptions: List<TextOption> = listOf(),
                            printStream: PrintStream = System.out) {
            val colorOptions = mutableMapOf<ColorOption, Color>()
            if (textColor != null) colorOptions[ColorOption.TEXT] = textColor
            if (backgroundColor != null) colorOptions[ColorOption.BACKGROUND] = backgroundColor

            val ansiEscape = TextFormat().getAnsiEscapeSequence(colorOptions = colorOptions, textOptions = textOptions)
            printStream.print("$ansiEscape$text$ANSI_RESET")
        }

        @JvmStatic
        fun println(text: String, textColor: Color? = null,
                    backgroundColor: Color? = null,
                    textOptions: List<TextOption> = listOf(),
                    printStream: PrintStream = System.out) {
            print("$text\n", textColor, backgroundColor, textOptions, printStream)
        }
    }
}