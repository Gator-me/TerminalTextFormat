package com.gatorme.text

import com.gatorme.TEST_PARAMS
import com.gatorme.TEST_STRING
import com.gatorme.enum.ColorOption
import com.gatorme.enum.TextOption
import com.gatorme.getExpectedStringLength
import com.gatorme.text.AdHocTerminalTextFormat as ah
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.awt.Color
import java.io.ByteArrayOutputStream
import java.io.PrintStream

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AdHocTerminalTextFormatKtTest {
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
    fun WHEN_print_EXPECT_correct_length_output(colorOptions: Map<ColorOption, Color>, textOptions: Set<TextOption>) {
        // Arrange
        val textColor = colorOptions[ColorOption.TEXT]
        val backgroundColor = colorOptions[ColorOption.BACKGROUND]

        // Act
        ah.print(TEST_STRING, textColor, backgroundColor, textOptions, printStream =  printStream)

        // Assert
        Assertions.assertEquals(
            getExpectedStringLength(colorOptions, textOptions, newLine = false),
            baos.size()
        )
    }

    @ParameterizedTest
    @MethodSource(TEST_PARAMS)
    fun WHEN_println_EXPECT_correct_length_output(colorOptions: Map<ColorOption, Color>, textOptions: Set<TextOption>) {
        // Arrange
        val textColor = colorOptions[ColorOption.TEXT]
        val backgroundColor = colorOptions[ColorOption.BACKGROUND]

        // Act
        ah.println(TEST_STRING, textColor, backgroundColor, textOptions, printStream = printStream)

        // Assert
        Assertions.assertEquals(
            getExpectedStringLength(colorOptions, textOptions, newLine = true),
            baos.size()
        )
    }
}