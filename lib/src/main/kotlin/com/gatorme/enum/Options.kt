package com.gatorme.enum

/**
 * Text options like bold, italics etc. for terminal. May not
 * work in all terminals.
 */
enum class TextOption(val code: Int, val reset: Int) {
    BOLD(1, 22),
    FAINT(2, 22),
    ITALIC(3, 23),
    UNDERLINE(4, 24),
    BLINK(5, 25),
    REVERSE_COLORS(7, 27),
    HIDDEN(8, 28),
    STRIKETHROUGH(9, 29),
    BOLD_UNDERLINE(21, 24),
}

/**
 * Color options for text and background. Colors may display
 * differently in different terminals or be unsupported
 * altogether.
 */
enum class ColorOption(val code: Int, val reset: Int) {
    TEXT(38, 39),
    BACKGROUND(48, 49),
}
