package com.gatorme.enum

enum class TextOption(val code: Int) {
    BOLD(1),
    ITALIC(3),
    UNDERLINE(4),
    REVERSE_VIDEO(7),
    STRIKETHROUGH(9)
}

enum class ColorOption(val code: Int) {
    TEXT(38),
    BACKGROUND(48)
}
