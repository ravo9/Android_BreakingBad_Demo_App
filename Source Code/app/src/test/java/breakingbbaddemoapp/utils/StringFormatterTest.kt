package breakingbbaddemoapp.utils

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule


class StringFormatterTest {

    private var stringFormatter: StringFormatter? = null

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setupTest() {

        // Initialize the formatter
        stringFormatter = StringFormatter()
    }

    @Test
    fun formatSimpleBaseAndValueString() {

        // Prepare expected string
        val expectedString = "Base: Value"

        // Perform the action
        val base = "Base"
        val value = "Value"
        val receivedString = stringFormatter?.formatBaseAndValueString(base, value)

        // Check results
        Assert.assertEquals(expectedString, receivedString)
    }

    @Test
    fun formatListString_NonEmptyList() {

        // Prepare expected string
        val expectedString = "Base: value1, value2, value3"

        // Perform the action
        val base = "Base"
        val values = listOf<String>("value1", "value2", "value3")
        val receivedString = stringFormatter?.formatListString(base, "", values)

        // Check results
        Assert.assertEquals(expectedString, receivedString)
    }

    @Test
    fun formatListString_EmptyList() {

        // Prepare expected string
        val expectedString = "Base: no values"

        // Perform the action
        val base = "Base"
        val fallback = "no values"
        val values = emptyList<String>()
        val receivedString = stringFormatter?.formatListString(base, fallback, values)

        // Check results
        Assert.assertEquals(expectedString, receivedString)
    }
}