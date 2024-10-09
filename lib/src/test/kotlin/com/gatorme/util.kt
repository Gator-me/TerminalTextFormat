package com.gatorme

import com.gatorme.enum.ColorOption
import com.gatorme.enum.TextOption
import com.gatorme.text.TextFormat
import org.junit.jupiter.params.provider.Arguments
import java.awt.Color

const val TEST_STRING = "test"
const val TEST_PARAMS = "com.gatorme.UtilKt#provideTestParams"

fun provideTestParams(): List<Arguments> {
    val noColor = mapOf<ColorOption, Color>()
    val noText = setOf<TextOption>()
    val shortColorMap = mapOf(ColorOption.TEXT to Color.RED)
    val shortTextSet = setOf(TextOption.ITALIC)
    val allColorOpts = mapOf(ColorOption.TEXT to Color.ORANGE, ColorOption.BACKGROUND to Color.MAGENTA)
    val longTextSet = setOf(TextOption.ITALIC, TextOption.BOLD, TextOption.STRIKETHROUGH)
    val allTextOpts = setOf(
        TextOption.ITALIC,
        TextOption.BOLD,
        TextOption.REVERSE_COLORS,
        TextOption.UNDERLINE,
        TextOption.STRIKETHROUGH,
        TextOption.BOLD_UNDERLINE,
        TextOption.BLINK,
        TextOption.FAINT,
        TextOption.HIDDEN
    )

    // don't use combo of noColor/noText here, that's a special case and tested elsewhere
    return listOf(
        Arguments.of(noColor, shortTextSet),
        Arguments.of(noColor, longTextSet),
        Arguments.of(noColor, allTextOpts),
        Arguments.of(shortColorMap, noText),
        Arguments.of(shortColorMap, shortTextSet),
        Arguments.of(shortColorMap, longTextSet),
        Arguments.of(shortColorMap, allTextOpts),
        Arguments.of(allColorOpts, noText),
        Arguments.of(allColorOpts, shortTextSet),
        Arguments.of(allColorOpts, longTextSet),
        Arguments.of(allColorOpts, allTextOpts)
    )
}

private fun intsLength(vararg integers: Int): Int = integers.map { it.toString().length }.reduceOrNull(Integer::sum) ?: 0

fun getExpectedStringLength(colorOptions: Map<ColorOption, Color>,
                                    textOptions: Set<TextOption>,
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