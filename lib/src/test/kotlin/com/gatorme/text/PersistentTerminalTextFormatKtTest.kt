package com.gatorme.text

import com.gatorme.TEST_PARAMS
import com.gatorme.TEST_STRING
import com.gatorme.enum.ColorOption
import com.gatorme.enum.TextOption
import com.gatorme.exception.InvalidTextOptionException
import com.gatorme.getExpectedStringLength
import com.gatorme.model.TextFormatConfig
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import org.junit.jupiter.params.provider.MethodSource
import java.awt.Color
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.test.Test
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PersistentTerminalTextFormatKtTest {
    private lateinit var baos: ByteArrayOutputStream
    private lateinit var printStream: PrintStream

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
    @MethodSource(TEST_PARAMS)
    fun print_multiple_options_correct_length_output(
        colorOptions: Map<ColorOption, Color>,
        textOptions: Set<TextOption>,
    ) {
        // Arrange
        val config =
            TextFormatConfig(
                colorOptions = colorOptions,
                textOptions = textOptions,
                printStream = printStream,
            )
        val tx = PersistentTerminalTextFormat(config)

        // Act
        tx.print(TEST_STRING)

        // Assert
        Assertions.assertEquals(
            getExpectedStringLength(colorOptions, textOptions, newLine = false),
            baos.size(),
        )
    }

    @ParameterizedTest
    @MethodSource(TEST_PARAMS)
    fun println_multiple_options_correct_length_output(
        colorOptions: Map<ColorOption, Color>,
        textOptions: Set<TextOption>,
    ) {
        // Arrange
        val config =
            TextFormatConfig(
                colorOptions = colorOptions,
                textOptions = textOptions,
                printStream = printStream,
            )
        val tx = PersistentTerminalTextFormat(config)

        // Act
        tx.println(TEST_STRING)

        // Assert
        Assertions.assertEquals(
            getExpectedStringLength(colorOptions, textOptions, newLine = true),
            baos.size(),
        )
    }

    @Test
    @DisplayName("Fail creating with no color or text options")
    fun init_no_color_or_text_options_throws_exception() {
        // Arrange
        val emptyColorOptions: Map<ColorOption, Color> = mapOf()
        val emptyTextOptions: Set<TextOption> = setOf()

        val config = TextFormatConfig(colorOptions = emptyColorOptions, textOptions = emptyTextOptions)

        // Act
        org.junit.jupiter.api.assertThrows<InvalidTextOptionException> {
            PersistentTerminalTextFormat(config)
        }
    }

    @ParameterizedTest
    @EnumSource(ColorOption::class)
    fun reset_color_removed_from_config_and_correct_output_length(colorOption: ColorOption) {
        // Arrange
        val colorOptions =
            mapOf(
                colorOption to Color.BLUE,
                ColorOption.TEXT to Color.BLACK,
                ColorOption.BACKGROUND to Color.BLACK,
            )

        val textOptions = setOf(TextOption.BOLD, TextOption.UNDERLINE)
        val config =
            TextFormatConfig(
                colorOptions = colorOptions,
                textOptions = textOptions,
                printStream = printStream,
            )

        val tx = PersistentTerminalTextFormat(config)

        // Act + Assert
        tx.println(TEST_STRING)
        val startingSize = getExpectedStringLength(tx.config.colorOptions, tx.config.textOptions, newLine = true)
        Assertions.assertEquals(startingSize, baos.size())
        assertEquals(2, tx.config.colorOptions.size)

        tx.reset(colorOption)
        tx.println(TEST_STRING)
        val finalSize =
            startingSize +
                getExpectedStringLength(
                    tx.config.colorOptions,
                    tx.config.textOptions,
                    newLine = true,
                )
        assertEquals(finalSize, baos.size())
        assertEquals(1, tx.config.colorOptions.size)
    }

    @ParameterizedTest
    @EnumSource(value = TextOption::class, mode = EnumSource.Mode.EXCLUDE, names = ["BOLD"])
    fun reset_text_option_removed_from_config_and_correct_output_length(textOption: TextOption) {
        // Arrange
        val colorOptions =
            mapOf(
                ColorOption.TEXT to Color.BLACK,
                ColorOption.BACKGROUND to Color.BLACK,
            )

        val textOptions = setOf(textOption, TextOption.BOLD)

        val config =
            TextFormatConfig(
                colorOptions = colorOptions,
                textOptions = textOptions,
                printStream = printStream,
            )

        val tx = PersistentTerminalTextFormat(config)

        // Act + Assert
        tx.println(TEST_STRING)
        val startingSize = getExpectedStringLength(tx.config.colorOptions, tx.config.textOptions, newLine = true)
        Assertions.assertEquals(startingSize, baos.size())
        assertEquals(2, tx.config.textOptions.size)

        tx.reset(textOption = textOption)
        tx.println(TEST_STRING)
        val finalSize =
            startingSize +
                getExpectedStringLength(
                    tx.config.colorOptions,
                    tx.config.textOptions,
                    newLine = true,
                )
        assertEquals(finalSize, baos.size())
        assertEquals(1, tx.config.textOptions.size)
    }
}
