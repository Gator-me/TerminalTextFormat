package com.gatorme.text

import com.gatorme.enum.ColorOption
import com.gatorme.exception.InvalidTextOptionException
import com.gatorme.model.TextFormatConfig
import java.awt.Color

class PersistentTerminalTextFormat (private val config: TextFormatConfig): TextFormat(config) {
    init {
        if (this.config.colorOptions.isEmpty() && this.config.textOptions.isEmpty()) {
            throw InvalidTextOptionException("TerminalText cannot be constructed with no color or text options.")
        }
    }

    private val ansiEscapeSequence = getAnsiEscapeSequence()

    fun print(text: Any = "") {
        this.config.printStream.print("$ansiEscapeSequence$text$ANSI_RESET")
    }

    fun println(text: Any = "") {
        print("$text\n")
    }
}
