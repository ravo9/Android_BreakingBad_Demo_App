package breakingbbaddemoapp.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.mock.Calls
import breakingbbaddemoapp.models.*
import breakingbbaddemoapp.network.ApiClient
import breakingbbaddemoapp.utils.DataFetchingCallback
import breakingbbaddemoapp.utils.FilteringTools

class FeedViewModelTest {

    private var viewModel: FeedViewModel? = null
    private var fakeCharactersList: List<SimplifiedCharacterObject>? = null

    @Mock
    private val apiClient: ApiClient? = null

    @Mock
    private val filteringTools: FilteringTools? = null

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setupTest() {

        // Inject Mocks
        MockitoAnnotations.initMocks(this)

        // Initialize the ViewModel
        viewModel = FeedViewModel(apiClient!!, filteringTools!!)

        // Prepare fake characters list
        val character1 = SimplifiedCharacterObject(0, "John Smith", "Johnny", null, listOf(3), listOf(1, 2, 3))
        val character2 = SimplifiedCharacterObject(1, "John Connery", "J.C.", null, listOf(1, 2), listOf(1))
        val character3 = SimplifiedCharacterObject(2, "Sue Connery", "S.C.", null, listOf(1, 2), listOf(1))
        fakeCharactersList = listOf(character1, character2, character3)
    }

    @Test
    fun fetchAllCharactersByFeedViewModel() {

        // Prepare API response
        val fakeGetCharactersResponseObject = Calls.response(fakeCharactersList!!)

        // Set testing conditions
        Mockito.`when`(apiClient?.getAllCharacters()).thenReturn(fakeGetCharactersResponseObject)
        Mockito.`when`(filteringTools?.filterResults(fakeCharactersList!!, null, null)).thenReturn(fakeCharactersList)

        // Prepare fake callback
        val fakeCallback: DataFetchingCallback = Mockito.mock(DataFetchingCallback::class.java)

        // Perform the action
        viewModel!!.getCharacters(fakeCallback, null, null)

        // Check whether correct callback method has been called
        Mockito.verify(fakeCallback).fetchingSuccessful(fakeCharactersList!!)
    }
}