package com.example.data.util

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Date

class DateUtilsTest {

    @Test
    fun formatDate_shouldReturnCorrectFormat_whenValidDateProvided() {
        val expected = "2025-03-25"
        val actual = DateUtils.formatDate(Date())

        assertEquals(expected, actual)
    }
}