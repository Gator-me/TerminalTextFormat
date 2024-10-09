package com.gatorme.text

import com.gatorme.exception.InvalidTextOptionException
import com.gatorme.model.TextFormatConfig

class PersistentTerminalTextFormat (private val config: TextFormatConfig): TextFormat(config) {
    init {
        if (this.config.colorOptions.isEmpty() && this.config.textOptions.isEmpty()) {
            throw InvalidTextOptionException("TerminalText cannot be constructed with no color or text options.")
        }
    }

    private val ansiEscapeSequence = getAnsiEscapeSequence()

    fun print(text: Any = "", colorBackgroundLineFull: Boolean = this.config.colorBackgroundLineFull) {
        this.config.printStream.print("$ansiEscapeSequence${text}${if (colorBackgroundLineFull) "" else ANSI_RESET}")
        if (colorBackgroundLineFull) this.config.printStream.print(ANSI_RESET)
    }

    fun println(text: Any = "", colorBackgroundLineFull: Boolean = this.config.colorBackgroundLineFull) {
        print("$text\n", colorBackgroundLineFull)
    }
}
