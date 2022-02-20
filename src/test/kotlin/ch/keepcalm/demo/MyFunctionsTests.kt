package ch.keepcalm.demo

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test

internal class MyFunctionsTests {

    @Test
    fun `Positive testing - upper case`() {
        val input = "Spring Cloud"
        val expectedOutput = "SPRING CLOUD"

        val actualOutput = MyFunctions().uppercase().apply(input)
        assertEquals(expectedOutput, actualOutput)
    }

    @Test
    fun `Negative testing - upper case`() {
        val input = "Spring Cloud"
        val unexpectedOutput = "spring cloud"

        val actualOutput = MyFunctions().uppercase().apply(input)
        assertNotEquals(unexpectedOutput, actualOutput)
    }

    @Test
    fun `Positive testing - reverse`() {
        val input = "Spring Cloud"
        val expectedOutput = "duolC gnirpS"

        val actualOutput = MyFunctions().reverse().apply(input)
        assertEquals(expectedOutput, actualOutput)
    }

    @Test
    fun `Negative testing - reverse`() {
        val input = "Spring Cloud"
        val unexpectedOutput = "Spring Cloud"

        val actualOutput = MyFunctions().reverse().apply(input)
        assertNotEquals(unexpectedOutput, actualOutput)
    }

    @Test
    fun `Positive testing - uppercase and reverse`() {
        val input = "Spring Cloud"
        val expectedOutput = "DUOLC GNIRPS"

        val actualOutput = MyFunctions().uppercase().andThen(MyFunctions().reverse()).apply(input)
        assertEquals(expectedOutput, actualOutput)
    }


    @Test
    fun `Positive testing - reverse with Message`() {
        val input = Message("Spring Cloud")
        val expectedOutput = "duolC gnirpS"

        val actualOutput = MyFunctions().reverseWitMessage().apply(input)
        assertEquals(expectedOutput, actualOutput)
    }
}
