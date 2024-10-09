package com.gatorme.text

import com.gatorme.enum.ColorOption
import com.gatorme.enum.TextOption
import com.gatorme.exception.InvalidTextOptionException
import com.gatorme.getExpectedStringLength
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.awt.Color
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.test.Test

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PersistentTerminalTextFormatKtTest {
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
        val tx = PersistentTerminalTextFormat(colorOptions = colorOptions, textOptions = textOptions, printStream = printStream)

        // Act
        tx.print(TEST_STRING)

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
        val tx = PersistentTerminalTextFormat(colorOptions = colorOptions, textOptions = textOptions, printStream = printStream)

        // Act
        tx.println(TEST_STRING)

        // Assert
        Assertions.assertEquals(
            getExpectedStringLength(colorOptions, textOptions, newLine = true),
            baos.size()
        )
    }

    @Test
    @DisplayName("Create with no parameters passed")
    fun WHEN_no_parameters_passed_EXPECT_succeed_init() {
        // Act
        var tx: PersistentTerminalTextFormat? = null
        Assertions.assertDoesNotThrow {
            tx = PersistentTerminalTextFormat()
            tx?.println("testing 1..2...3")
        }

        // Assert
        Assertions.assertNotNull(tx)
    }

    @Test
    @DisplayName("Fail creating with no color or text options")
    fun WHEN_no_color_or_text_options_EXPECT_throw_exception() {
        // Arrange
        val emptyColorOptions: Map<ColorOption, Color> = mapOf()
        val emptyTextOptions: List<TextOption> = listOf()

        // Act
        org.junit.jupiter.api.assertThrows<InvalidTextOptionException> {
            PersistentTerminalTextFormat(
                colorOptions = emptyColorOptions,
                textOptions = emptyTextOptions
            )
        }
    }
}