package com.example.labproject2

import com.example.labproject2.flaw.ConditionalComplexity
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class ConditionalComplexityTest {

    @Test
    fun checkFields_notAllFilled_falseReturn() {
        val result = ConditionalComplexity.isDebtSavedOrUpdated(
            false,
            "Add",
            false
        )
        assertThat(result).isFalse()
    }

    @Test
    fun checkFields_allFilled_trueReturn() {
        val result = ConditionalComplexity.isDebtSavedOrUpdated(
            true,
            "Add",
            false
        )
        assertThat(result).isTrue()
    }

    @Test
    fun addDebts_idPassed_falseReturn() {
        val result = ConditionalComplexity.isDebtSavedOrUpdated(
            true,
            "Add",
            true
        )
        assertThat(result).isFalse()
    }

    @Test
    fun addDebts_idNotPassed_trueReturn() {
        val result = ConditionalComplexity.isDebtSavedOrUpdated(
            true,
            "Add",
            false
        )
        assertThat(result).isTrue()
    }

    @Test
    fun updateDebts_idPassed_trueReturn() {
        val result = ConditionalComplexity.isDebtSavedOrUpdated(
            true,
            "Update",
            true
        )
        assertThat(result).isTrue()
    }

    @Test
    fun updateDebts_idNotPassed_falseReturn() {
        val result = ConditionalComplexity.isDebtSavedOrUpdated(
            true,
            "Update",
            false
        )
        assertThat(result).isFalse()
    }
}