package com.gatorme.text

import com.gatorme.enum.ColorOption
import com.gatorme.enum.TextOption
import com.gatorme.model.TextFormatConfig
import com.gatorme.text.TextFormat.AnsiArgs.ANSI_RESET
import java.awt.Color
import java.io.PrintStream

sealed class AdHocTerminalTextFormat {
    companion object {
        @JvmStatic
        fun print(text: String, config: TextFormatConfig) {
            val ansiEscape = TextFormat(config).getAnsiEscapeSequence()
            config.printStream.print("$ansiEscape$text$ANSI_RESET")
        }

        @JvmStatic
        fun println(text: String, config: TextFormatConfig) {
            print("$text\n", config)
        }

        @JvmStatic
        fun print(text: String,
                  textColor: Color? = null,
                  backgroundColor: Color? = null,
                  textOptions: Set<TextOption> = setOf(),
                  printStream: PrintStream = System.out) {
            val colorOptions = mutableMapOf<ColorOption, Color>()
            if (textColor != null) colorOptions[ColorOption.TEXT] = textColor
            if (backgroundColor != null) colorOptions[ColorOption.BACKGROUND] = backgroundColor
            val config = TextFormatConfig(
                colorOptions = colorOptions,
                textOptions = textOptions,
                printStream = printStream
            )
            print(text, config)
        }

        @JvmStatic
        fun println(text: String,
                    textColor: Color? = null,
                    backgroundColor: Color? = null,
                    textOptions: Set<TextOption> = setOf(),
                    printStream: PrintStream = System.out) {
            print("$text\n", textColor, backgroundColor, textOptions, printStream)
        }
    }
}
