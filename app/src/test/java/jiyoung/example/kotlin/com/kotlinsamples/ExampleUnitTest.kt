package jiyoung.example.kotlin.com.kotlinsamples

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    fun setInfo(a: String, b: String?) {
        System.out.println(a + b)
    }

    @Test
    fun test() {
        setInfo("a", null)
    }
}
