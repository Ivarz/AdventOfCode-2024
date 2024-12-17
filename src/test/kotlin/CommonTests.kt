import org.junit.jupiter.api.Test
import org.infovars.stirling

class CommonTests {
    @Test
    fun testStirling() {
        for (i in 0..100) {
            println("$i ${stirling(i)}")
        }
    }
}