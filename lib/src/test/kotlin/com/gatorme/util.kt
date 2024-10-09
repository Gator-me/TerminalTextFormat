package com.gatorme

import com.gatorme.text.PersistentTerminalTextFormatKtTest.Companion.TEST_STRING
import com.gatorme.enum.ColorOption
import com.gatorme.enum.TextOption
import com.gatorme.text.TextFormat
import java.awt.Color

private fun intsLength(vararg integers: Int): Int = integers.map { it.toString().length }.reduceOrNull(Integer::sum) ?: 0

fun getExpectedStringLength(colorOptions: Map<ColorOption, Color>,
                                    textOptions: List<TextOption>,
                                    newLine: Boolean): Int {
    return TextFormat.ESC.length +
            (colorOptions.keys.map { intsLength(it.code) }.reduceOrNull(Integer::sum) ?: 0) +
            (colorOptions.values.map { intsLength(it.red, it.green, it.blue) }.reduceOrNull(Integer::sum) ?: 0) +
            colorOptions.size +
            colorOptions.size * 5 + textOptions.size - 1 +      // semicolon count
            (textOptions.map { intsLength(it.code) }.reduceOrNull(Integer::sum) ?: 0) +
            TextFormat.M.length +
            TEST_STRING.length +
            (if (newLine) 1 else 0) +
            TextFormat.ANSI_RESET.length
}