package breakingbbaddemoapp.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert
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


class FeedViewModelTest {

    private var viewModel: FeedViewModel? = null
    private var fakeGetVideosResponseGson: GetVideosResponse? = null
    private var fakeGetVideosError: Error? = null

    @Mock
    private val apiClient: ApiClient? = null

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setupTest() {

        // Inject Mocks
        MockitoAnnotations.initMocks(this)

        // Initialize the ViewModel
        viewModel = FeedViewModel(apiClient!!)

        // Prepare fake data
        val title = "fake/video/title"
        val videoDuration = 12345
        val corecategories = listOf("fake/video/corecategory")
        val thumbnailUrl = "fake/thumbnail/url"
        val videoId = "fake/video/id"
        val sectionName = "fake/section/name"
        val sectionId = "fake/section/id"

        // Prepare fake Gson (API) model objects
        val fakeMetaData = MetaData(corecategories, videoDuration, title)
        val fakeMediaData = MediaData(thumbnailUrl)
        val fakeVideo = Video(videoId, fakeMediaData, fakeMetaData)
        val fakeSection = Section(sectionId, sectionName, listOf(fakeVideo))
        fakeGetVideosResponseGson = GetVideosResponse(listOf(fakeSection))

        // Prepare fake Error object
        fakeGetVideosError = Error("fake/api/call/error")
    }

    @Test
    fun fetchAllVideosByFeedViewModel() {

        // Prepare API response
        val getAllVideosResponse = Calls.response(fakeGetVideosResponseGson!!)

        // Set testing conditions
        Mockito.`when`(apiClient?.getAllCharacters()).thenReturn(getAllVideosResponse)

        // Perform the action
        val receivedGetAllVideosResponse = viewModel?.getVideos()

        // Check results
        Assert.assertSame(getAllVideosResponse, receivedGetAllVideosResponse)
    }
}