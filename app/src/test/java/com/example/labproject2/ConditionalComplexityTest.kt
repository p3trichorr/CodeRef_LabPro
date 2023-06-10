package com.example.labproject2

import com.example.labproject2.flaw.ConditionalComplexity
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ConditionalComplexityTest {

    @Mock
    private lateinit var mockDependency: ConditionalComplexity.DebtFunCall

    @Test
    fun checkFields_notAllFilled_falseReturn() {

        val conditionalComplexity = ConditionalComplexity(mockDependency)

        val result = conditionalComplexity.isDebtSavedOrUpdated(
            false,
            "",
            ""
        )
        assertThat(result).isFalse()
    }

    @Test
    fun checkFields_allFilledNoState_falseReturn() {

        val conditionalComplexity = ConditionalComplexity(mockDependency)

        val result = conditionalComplexity.isDebtSavedOrUpdated(
            true,
            "",
            ""
        )
        assertThat(result).isFalse()
        verify(mockDependency, times(0)).addData()
        verify(mockDependency, times(0)).updateDebt()
    }

    @Test
    fun callFunction_addCommandCallUpdateFunction_falseOutput() {

        val conditionalComplexity = ConditionalComplexity(mockDependency)

        val result = conditionalComplexity.isDebtSavedOrUpdated(
            true,
            "Add",
            "Update"
        )
        assertThat(result).isTrue()
        verify(mockDependency, times(0)).addData()
        verify(mockDependency, times(1)).updateDebt()
    }

    @Test
    fun callFunction_addCommandFunctional_trueReturn() {

        val conditionalComplexity = ConditionalComplexity(mockDependency)

        val result = conditionalComplexity.isDebtSavedOrUpdated(
            true,
            "Add",
            "Add"
        )
        assertThat(result).isTrue()
        verify(mockDependency, times(1)).addData()
        verify(mockDependency, times(0)).updateDebt()
    }
}