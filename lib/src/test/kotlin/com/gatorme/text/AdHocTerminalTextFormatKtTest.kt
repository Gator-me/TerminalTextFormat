package com.gatorme.text

import com.gatorme.enum.ColorOption
import com.gatorme.enum.TextOption
import com.gatorme.getExpectedStringLength
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.awt.Color
import java.io.ByteArrayOutputStream
import java.io.PrintStream

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AdHocTerminalTextFormatKtTest {
    companion object {
        const val TEST_STRING = "test"
    }

    private lateinit var baos: ByteArrayOutputStream
    private lateinit var printStream: PrintStream

    private fun provideTestParams(): List<Arguments> {
        val noColor = mapOf<ColorOption, Color>()
        val noText = listOf<TextOption>()
        val shortColorMap = mapOf(ColorOption.TEXT to Color.RED)
        val shortTextList = listOf(TextOption.ITALIC)
        val allColorOpts = mapOf(ColorOption.TEXT to Color.ORANGE, ColorOption.BACKGROUND to Color.MAGENTA)
        val longTextList = listOf(TextOption.ITALIC, TextOption.BOLD, TextOption.STRIKETHROUGH)
        val allTextOpts = listOf(
            TextOption.ITALIC,
            TextOption.BOLD,
            TextOption.REVERSE_VIDEO,
            TextOption.UNDERLINE,
            TextOption.STRIKETHROUGH
        )

        // don't use combo of noColor/noText here, that's a special case and tested elsewhere
        return listOf(
            Arguments.of(noColor, shortTextList),
            Arguments.of(noColor, longTextList),
            Arguments.of(noColor, allTextOpts),
            Arguments.of(shortColorMap, noText),
            Arguments.of(shortColorMap, shortTextList),
            Arguments.of(shortColorMap, longTextList),
            Arguments.of(shortColorMap, allTextOpts),
            Arguments.of(allColorOpts, noText),
            Arguments.of(allColorOpts, shortTextList),
            Arguments.of(allColorOpts, longTextList),
            Arguments.of(allColorOpts, allTextOpts)
        )
    }

    @BeforeEach
    fun setup() {
        baos = ByteArrayOutputStream()
        printStream = PrintStream(baos)
    }


    @AfterEach
    fun teardown() {
        baos.flush()
        printStream.close()
    }


    @ParameterizedTest
    @MethodSource("provideTestParams")
    fun WHEN_print_EXPECT_correct_length_output(colorOptions: Map<ColorOption, Color>, textOptions: List<TextOption>) {
        // Arrange
        val textColor = colorOptions[ColorOption.TEXT]
        val backgroundColor = colorOptions[ColorOption.BACKGROUND]

        // Act
        AdHocTerminalTextFormat.print(TEST_STRING, textColor, backgroundColor, textOptions, printStream)

        // Assert
        Assertions.assertEquals(
            getExpectedStringLength(colorOptions, textOptions, newLine = false),
            baos.size()
        )
    }

    @ParameterizedTest
    @MethodSource("provideTestParams")
    fun WHEN_println_EXPECT_correct_length_output(colorOptions: Map<ColorOption, Color>, textOptions: List<TextOption>) {
        // Arrange
        val textColor = colorOptions[ColorOption.TEXT]
        val backgroundColor = colorOptions[ColorOption.BACKGROUND]

        // Act
        AdHocTerminalTextFormat.println(TEST_STRING, textColor, backgroundColor, textOptions, printStream)

        // Assert
        Assertions.assertEquals(
            getExpectedStringLength(colorOptions, textOptions, newLine = true),
            baos.size()
        )
    }
}