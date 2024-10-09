package com.gatorme.model

import com.gatorme.enum.ColorOption
import com.gatorme.enum.TextOption
import java.awt.Color
import java.io.PrintStream

data class TextFormatConfig(
    val colorOptions: Map<ColorOption, Color> = mapOf(),
    val textOptions: List<TextOption> = listOf(),
    val printStream: PrintStream = System.out,
)
