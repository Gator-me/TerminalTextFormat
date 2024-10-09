package com.gatorme.text

import com.gatorme.exception.InvalidTextOptionException
import com.gatorme.model.TextFormatConfig

class PersistentTerminalTextFormat(private val config: TextFormatConfig): TextFormat(config) {
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
        this.print("$text\n")
    }
}
