package com.gatorme.text

import com.gatorme.enum.ColorOption
import com.gatorme.enum.TextOption
import com.gatorme.exception.InvalidTextOptionException
import com.gatorme.model.TextFormatConfig

class PersistentTerminalTextFormat(val config: TextFormatConfig): TextFormat(config) {
    init {
        if (this.config.colorOptions.isEmpty() && this.config.textOptions.isEmpty()) {
            throw InvalidTextOptionException("TerminalText cannot be constructed with no color or text options.")
        }
    }

    private var ansiEscapeSequence = getAnsiEscapeSequence()

    fun print(text: Any = "") {
        this.config.printStream.print("${ansiEscapeSequence}$text$ANSI_RESET")
    }

    fun println(text: Any = "") {
        this.print("$text\n")
    }

    override fun reset(colorOption: ColorOption?,
                       textOption: TextOption?,
                       all: Boolean) {
        super.reset(colorOption, textOption, all)
        this.ansiEscapeSequence = getAnsiEscapeSequence()
    }
}
