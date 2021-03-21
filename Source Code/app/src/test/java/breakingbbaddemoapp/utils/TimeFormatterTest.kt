package breakingbbaddemoapp.utils

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule


class TimeFormatterTest {

    private var timeFormatter: TimeFormatter? = null

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setupTest() {

        // Initialize the TimeFormatter
        timeFormatter = TimeFormatter()
    }

    @Test
    fun formatActualValue() {

        // Prepare expected string
        val expectedTimeString = "0:02:03"

        // Perform the action
        val receivedTimeString = timeFormatter?.formatTimeToDisplay(123)

        // Check results
        Assert.assertEquals(expectedTimeString, receivedTimeString)
    }

    @Test
    fun formatNullValue() {

        // Prepare expected string
        val expectedTimeString = "0:00:00"

        // Perform the action
        val receivedTimeString = timeFormatter?.formatTimeToDisplay(null)

        // Check results
        Assert.assertEquals(expectedTimeString, receivedTimeString)
    }
}