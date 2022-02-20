package ch.keepcalm.demo

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.cloud.function.context.test.FunctionalSpringBootTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.function.context.FunctionCatalog
import reactor.core.publisher.Flux
import reactor.test.StepVerifier
import java.util.function.Function

@FunctionalSpringBootTest
internal class CloudFunctionsTests {

    @Autowired
    private val catalog: FunctionCatalog? = null

    @Test
    fun `Test uppercase`() {
        val input = "Spring Cloud"
        val expectedOutput = "SPRING CLOUD"
        val uppercaseFunction = catalog!!.lookup<Function<String, String>>(Function::class.java, "uppercase")
        val actualOutput = uppercaseFunction.apply(input)
        Assertions.assertThat(actualOutput).isEqualTo(expectedOutput)
    }

    @Test
    fun `Test reverse`() {
        val input = "Spring Cloud"
        val expectedOutput = "duolC gnirpS"
        val reverseFunction = catalog!!.lookup<Function<String, String>>(Function::class.java, "reverse")
        val actualOutput = reverseFunction.apply(input)
        Assertions.assertThat(actualOutput).isEqualTo(expectedOutput)
    }

    @Test
    @DisplayName("Function composition with same data type")
    fun `Test uppercase then reverse`() {
        val input = "Spring Cloud"
        val expectedOutput = "DUOLC GNIRPS"
        val upperThenReverse = catalog!!.lookup<Function<String, String>>(Function::class.java, "uppercase|reverse")
        val actualOutput = upperThenReverse.apply(input)
        Assertions.assertThat(actualOutput).isEqualTo(expectedOutput)
    }

    @Test
    @DisplayName("Function composition with different data type")
    fun `Test uppercase then reverse with message`() {
        val input = "Spring Cloud"
        val expectedOutput = "DUOLC GNIRPS"
        val upperThenReverse = catalog!!.lookup<Function<String, String>>(Function::class.java, "uppercase|reverseWithMessage")
        val actualOutput = upperThenReverse.apply(input)
        Assertions.assertThat(actualOutput).isEqualTo(expectedOutput)
    }

    @Test
    @DisplayName("Longer function composition with different data type")
    fun `Test uppercase then Reverse with Message then reverse`() {
        val input = "Spring Cloud"
        val expectedOutput = "SPRING CLOUD"
        val upperThenReverse = catalog!!.lookup<Function<String, String>>(Function::class.java, "uppercase|reverseWithMessage|reverse")
        val actualOutput = upperThenReverse.apply(input)
        Assertions.assertThat(actualOutput).isEqualTo(expectedOutput)
    }

    @Test
    @DisplayName("Function composition with both imperative and reactive types")
    fun `Test uppercase then reverseReactive`() {
        val input = "Spring Cloud"
        val expectedOutput = "DUOLC GNIRPS"
        val upperThenReverse = catalog!!.lookup<Function<Flux<String>, Flux<String>>>(Function::class.java, "uppercase|reverseReactive")
        StepVerifier.create(upperThenReverse.apply(Flux.just(input)))
            .expectNextMatches { actualOuput: String -> actualOuput == expectedOutput }
            .verifyComplete()
    }
}
