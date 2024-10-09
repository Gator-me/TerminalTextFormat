package com.gatorme.text

import com.gatorme.enum.ColorOption
import com.gatorme.enum.TextOption
import com.gatorme.exception.InvalidTextOptionException
import java.awt.Color
import java.io.PrintStream

class PersistentTerminalTextFormat (
    colorOptions: Map<ColorOption, Color> = mapOf(),
    textOptions: List<TextOption> = listOf(TextOption.REVERSE_VIDEO),
    private val defaultColorBackgroundLineFull: Boolean = false,
    private val printStream: PrintStream = System.out
): TextFormat(colorOptions, textOptions) {
    init {
        if (colorOptions.isEmpty() && textOptions.isEmpty()) {
            throw InvalidTextOptionException("TerminalText cannot be constructed with no color or text options.")
        }
    }

    private val ansiEscapeSequence = getAnsiEscapeSequence()

    fun print(text: Any = "", colorBackgroundLineFull: Boolean = this.defaultColorBackgroundLineFull) {
        printStream.print("$ansiEscapeSequence${text}${if (colorBackgroundLineFull) "" else ANSI_RESET}")
        if (colorBackgroundLineFull) printStream.print(ANSI_RESET)
    }

    fun println(text: Any = "", colorBackgroundLineFull: Boolean = this.defaultColorBackgroundLineFull) {
        print("$text\n", colorBackgroundLineFull)
    }
}
