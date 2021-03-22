package breakingbbaddemoapp.utils

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import breakingbbaddemoapp.models.SimplifiedCharacterObject
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule


class FilteringToolsTest {

    private var filteringTools: FilteringTools? = null

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setupTest() {

        // Initialize the tools
        filteringTools = FilteringTools()
    }

    @Test
    fun filterResults_filteringNamePhraseNull_seasonNull() {

        // Prepare fake characters list
        val character1 = SimplifiedCharacterObject(0, "John Smith", "Johnny", null, listOf(3), listOf(1, 2, 3))
        val character2 = SimplifiedCharacterObject(1, "John Connery", "J.C.", null, listOf(1, 2), listOf(1))
        val character3 = SimplifiedCharacterObject(2, "Sue Connery", "S.C.", null, listOf(1, 2), listOf(1))
        val characterList = listOf(character1, character2, character3)

        // Prepare fake filtering values
        val filterPhrase = null
        val filterSeason = null

        // Perform the action
        val filteredList = filteringTools!!.filterResults(characterList, filterPhrase, filterSeason)

        // Check results
        val expectedListSize = 3
        val actualListSize = filteredList.size
        Assert.assertEquals(expectedListSize, actualListSize)
    }

    @Test
    fun filterResults_filteringNamePhraseNull_filterBySeason() {

        // Prepare fake characters list
        val character1 = SimplifiedCharacterObject(0, "John Smith", "Johnny", null, listOf(3), listOf(1, 2, 3))
        val character2 = SimplifiedCharacterObject(1, "John Connery", "J.C.", null, listOf(1, 2), listOf(1))
        val character3 = SimplifiedCharacterObject(2, "Sue Connery", "S.C.", null, listOf(1, 2), listOf(1))
        val characterList = listOf<SimplifiedCharacterObject>(character1, character2, character3)

        // Prepare fake filtering values
        val filterPhrase = null
        val filterSeason = 3

        // Perform the action
        val filteredList = filteringTools!!.filterResults(characterList, filterPhrase, filterSeason)

        // Check results
        val expectedListSize = 1
        val actualListSize = filteredList.size
        Assert.assertEquals(expectedListSize, actualListSize)

        val expectedFilteredCharacterId = 0
        val actualFilteredCharacterId = filteredList.first().id
        Assert.assertEquals(expectedFilteredCharacterId, actualFilteredCharacterId)
    }

    @Test
    fun filterResults_filterByName_seasonNull() {

        // Prepare fake characters list
        val character1 = SimplifiedCharacterObject(0, "John Smith", "Johnny", null, listOf(3), listOf(1, 2, 3))
        val character2 = SimplifiedCharacterObject(1, "John Connery", "J.C.", null, listOf(1, 2), listOf(1))
        val character3 = SimplifiedCharacterObject(2, "Sue Connery", "S.C.", null, listOf(1, 2), listOf(1))
        val characterList = listOf<SimplifiedCharacterObject>(character1, character2, character3)

        // Prepare fake filtering values
        val filterPhrase = "John"
        val filterSeason = null

        // Perform the action
        val filteredList = filteringTools!!.filterResults(characterList, filterPhrase, filterSeason)

        // Check results
        val expectedListSize = 2
        val actualListSize = filteredList.size
        Assert.assertEquals(expectedListSize, actualListSize)
    }

    @Test
    fun filterResults_filterByNameAndSeason() {

        // Prepare fake characters list
        val character1 = SimplifiedCharacterObject(0, "John Smith", "Johnny", null, listOf(3), listOf(1, 2, 3))
        val character2 = SimplifiedCharacterObject(1, "John Connery", "J.C.", null, listOf(1, 2), listOf(1))
        val character3 = SimplifiedCharacterObject(2, "Sue Connery", "S.C.", null, listOf(1, 2), listOf(1))
        val characterList = listOf<SimplifiedCharacterObject>(character1, character2, character3)

        // Prepare fake filtering values
        val filterPhrase = "John"
        val filterSeason = 2

        // Perform the action
        val filteredList = filteringTools!!.filterResults(characterList, filterPhrase, filterSeason)

        // Check results
        val expectedListSize = 1
        val actualListSize = filteredList.size
        Assert.assertEquals(expectedListSize, actualListSize)

        val expectedFilteredCharacterId = 1
        val actualFilteredCharacterId = filteredList.first().id
        Assert.assertEquals(expectedFilteredCharacterId, actualFilteredCharacterId)
    }

}