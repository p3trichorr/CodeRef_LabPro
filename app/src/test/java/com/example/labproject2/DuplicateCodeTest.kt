package com.example.labproject2

import com.example.labproject2.flaw.DuplicateCode
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class DuplicateCodeTest {

    @Test
    fun dateFormat_yearInMonth_falseReturn() {
        val result = DuplicateCode.isDateFormatValid(
            "23-2004-03"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun dateFormat_dateOutOfRange_falseReturn() {
        val result = DuplicateCode.isDateFormatValid(
            "32-03-2004"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun dateFormat_wrongDatePattern_falseReturn() {
        val result = DuplicateCode.isDateFormatValid(
            "23.03.2004"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun dateFormat_rightFormat_trueReturn() {
        val result = DuplicateCode.isDateFormatValid(
            "23-03-2004"
        )
        assertThat(result).isTrue()
    }

    @Test
    fun addDebts_wrongDateFormat_falseReturn() {
        val result = DuplicateCode.isDebtAddedValid(
            "Joe",
            23,
            "ruble",
            "20.03.2004",
            20040320
        )
        assertThat(result).isFalse()
    }

    @Test
    fun addDebts_wrongDateInteger_falseReturn() {
        val result = DuplicateCode.isDebtAddedValid(
            "Joe",
            23,
            "ruble",
            "20-03-2004",
            2004320
        )
        assertThat(result).isFalse()
    }

    @Test
    fun addDebts_noName_falseReturn() {
        val result = DuplicateCode.isDebtAddedValid(
            "",
            23,
            "ruble",
            "20-03-2004",
            20040320
        )
        assertThat(result).isFalse()
    }

    @Test
    fun addDebts_noAmount_falseReturn() {
        val result = DuplicateCode.isDebtAddedValid(
            "Joe",
            0,
            "ruble",
            "20-03-2004",
            20040320
        )
        assertThat(result).isFalse()
    }

    @Test
    fun addDebts_rightInput_trueReturn() {
        val result = DuplicateCode.isDebtAddedValid(
            "Joe",
            23,
            "ruble",
            "20-03-2004",
            20040320
        )
        assertThat(result).isTrue()
    }
}