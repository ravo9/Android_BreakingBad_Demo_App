package breakingbbaddemoapp.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import breakingbbaddemoapp.R
import breakingbbaddemoapp.constants.SeriesSpecificConstants
import breakingbbaddemoapp.utils.DataFetchingCallback
import breakingbbaddemoapp.dependencyinjection.BreakingBadDemoApp
import breakingbbaddemoapp.models.SimplifiedCharacterObject
import breakingbbaddemoapp.viewmodels.FeedViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main_top_panel.*
import kotlinx.android.synthetic.main.loading_badge.*
import javax.inject.Inject


// Main ('feed') view
class FeedActivity : AppCompatActivity(), DataFetchingCallback {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: FeedViewModel

    private val STATE_FILTERING_IN_PROGRESS = "STATE_FILTERING_IN_PROGRESS"
    private val STATE_LOADING_ERROR = "STATE_LOADING_ERROR"
    private val STATE_CONTENT_LOADED = "STATE_CONTENT_LOADED"

    init {
        BreakingBadDemoApp.mainComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize ViewModel
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(FeedViewModel::class.java)

        // Initialize RecyclerView (feed items)
        setupRecyclerView()

        // Initialize Search Panel
        setupSearchPanel()

        // Fetch feed items from the back-end and load them into the view
        fetchCharacters()
    }


    // UI setup methods

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        main_feed_recyclerview.layoutManager = layoutManager
        main_feed_recyclerview.adapter = CharactersListAdapter(this) { selectedCharacterId: Int ->
            displayDetailedView(selectedCharacterId)
        }
    }

    private fun setupSeasonDropdown() {
        val itemZero = getString(R.string.all_seasons)
        val breakingBadItems = ArrayList<String>()
        for (seasonNumber in 1 .. SeriesSpecificConstants.NUMBER_OF_LAST_BREAKING_BAD_SEASON_EVER_CREATED) {
            val stringItem = getString(R.string.breaking_bad_season) + ' ' + seasonNumber
            breakingBadItems.add(stringItem)
        }
        val betterCallSaulItems = ArrayList<String>()
        for (seasonNumber in 1 .. SeriesSpecificConstants.NUMBER_OF_LAST_BETTER_CALL_SAUL_EVER_CREATED) {
            val stringItem = getString(R.string.better_call_saul_season) + ' ' + seasonNumber
            betterCallSaulItems.add(stringItem)
        }
        val dropDownItems = ArrayList<String>()
        dropDownItems.add(itemZero)
        dropDownItems.addAll(breakingBadItems)
        dropDownItems.addAll(betterCallSaulItems)
        val dropDownAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, dropDownItems)
        seasons_dropdown.adapter = dropDownAdapter
    }

    private fun setupSearchPanel() {
        setupSeasonDropdown()

        btn_search.setOnClickListener {
            val searchNamePhrase = search_engine.text.toString()
            val seasonNumber = seasons_dropdown.selectedItemPosition
            setViewState(STATE_FILTERING_IN_PROGRESS)
            updateCharacters(searchNamePhrase, seasonNumber)
        }
    }


    // UI management methods

    private fun displayDetailedView(selectedCharacterId: Int) {
        val fragment = DetailedViewFragment()
        val bundle = Bundle()
        bundle.putInt("selectedCharacterId", selectedCharacterId)
        fragment.arguments = bundle

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.main_content_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun loadItemsIntoList(items: List<SimplifiedCharacterObject>) {
        (main_feed_recyclerview.adapter as CharactersListAdapter).setItems(items)
    }

    fun displayErrorDialog(tryAgainAction: () -> Unit) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.loading_problem_check_the_internet_connection)
        builder.setPositiveButton(R.string.try_again) { _, _ ->
            tryAgainAction()
        }
        builder.create().show()
    }

    private fun setViewState(state: String) {
        when(state) {
            STATE_FILTERING_IN_PROGRESS -> setupLoadingView()
            STATE_LOADING_ERROR -> setupLoadingErrorView()
            STATE_CONTENT_LOADED -> setupContentLoadedView()
        }
    }

    private fun setupLoadingView() {
        btn_search.isEnabled = false
    }

    private fun setupLoadingErrorView() {
        displayErrorDialog {
            fetchCharacters()
        }
    }

    private fun setupContentLoadedView() {
        loading_container.visibility = View.GONE
        btn_search.isEnabled = true
    }


    // Data fetching methods

    private fun fetchCharacters() {
        viewModel.getCharacters(this, null, null)
    }

    private fun updateCharacters(filterNamePhrase: String?, filterSeason: Int?) {
        viewModel.getCharacters(this, filterNamePhrase, filterSeason)
    }


    // Data Fetching Callback interface methods

    override fun fetchingSuccessful(payload: Any) {
        if ((payload as? List<SimplifiedCharacterObject>) != null) {
            loadItemsIntoList(payload)
            setViewState(STATE_CONTENT_LOADED)
        } else {
            setViewState(STATE_LOADING_ERROR)
        }
    }

    override fun fetchingError() {
        setViewState(STATE_LOADING_ERROR)
    }
}