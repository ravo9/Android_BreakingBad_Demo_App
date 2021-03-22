package breakingbbaddemoapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import breakingbbaddemoapp.R
import breakingbbaddemoapp.dependencyinjection.BreakingBadDemoApp
import breakingbbaddemoapp.models.CompleteCharacterObject
import breakingbbaddemoapp.utils.DataFetchingCallback
import breakingbbaddemoapp.utils.StringFormatter
import breakingbbaddemoapp.viewmodels.DetailedViewViewModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.detailed_view.*
import javax.inject.Inject

// Detailed view for displaying selected item
class DetailedViewFragment : Fragment(), DataFetchingCallback {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: DetailedViewViewModel

    @Inject
    lateinit var stringFormatter: StringFormatter

    private val STATE_LOADING_ERROR = "STATE_LOADING_ERROR"
    private val STATE_CONTENT_LOADED = "STATE_CONTENT_LOADED"

    init {
        BreakingBadDemoApp.mainComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Initialize ViewModel
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(DetailedViewViewModel::class.java)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.detailed_view, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Fetch selected item from the back-end and load it into the view
        fetchSelectedCharacter()

        // Setup Cross Button
        val closingOnClickListener = View.OnClickListener{ activity?.onBackPressed() }
        btn_cross.setOnClickListener(closingOnClickListener)

        // Setup closing on the grey fields' click
        spacing_top.setOnClickListener(closingOnClickListener)
        spacing_bottom.setOnClickListener(closingOnClickListener)
    }


    // UI setup methods

    private fun setViewState(state: String, characterObject: CompleteCharacterObject? = null) {
        when(state) {
            STATE_LOADING_ERROR -> setupLoadingErrorView()
            STATE_CONTENT_LOADED -> characterObject?.let { setupContentLoadedView(it) }
        }
    }

    private fun setupLoadingErrorView() {
        (activity as? FeedActivity)?.displayErrorDialog {
            fetchSelectedCharacter()
        }
    }

    private fun setupContentLoadedView(characterObject: CompleteCharacterObject) {
        progressBar.visibility = View.GONE
        imageView_picture.let {
            Glide.with(this)
                .load(characterObject.imageUrl)
                .into(it)
        }
        textView_name.text = characterObject.name
        textView_nickname.text = characterObject.nickname
        context?.let {
            textView_category.text = stringFormatter.formatBaseAndValueString(
                it.getString(R.string.category),
                characterObject.category
            )
            textView_breakingBadSeasonAppearance.text = stringFormatter.formatListString(
                it.getString(R.string.breaking_bad_seasons_appearance),
                it.getString(R.string.none),
                characterObject.breakingBadSeasonAppearance
            )
            textView_betterCallSaulSeasonAppearance.text = stringFormatter.formatListString(
                it.getString(R.string.better_call_saul_seasons_appearance),
                it.getString(R.string.none),
                characterObject.betterCallSaulSeasonAppearance
            )
            textView_birthday.text = stringFormatter.formatBaseAndValueString(
                it.getString(R.string.birthday),
                characterObject.birthday
            )
            textView_occupations.text = stringFormatter.formatListString(
                it.getString(R.string.occupations),
                it.getString(R.string.none),
                characterObject.occupations
            )
            textView_status.text = stringFormatter.formatBaseAndValueString(
                it.getString(R.string.status),
                characterObject.status
            )
            textView_portrayed.text = stringFormatter.formatBaseAndValueString(
                it.getString(R.string.portrayed_by),
                characterObject.portrayed
            )
        }
    }


    // Data fetching methods

    private fun fetchSelectedCharacter() {
        val selectedCharacterId = this.arguments?.getInt("selectedCharacterId")
        selectedCharacterId?.let {
            viewModel.fetchSelectedCharacter(this, it)
        }
    }


    // Data Fetching Callback interface methods

    override fun fetchingSuccessful(payload: Any) {
        if ((payload as? CompleteCharacterObject) != null) {
            setViewState(STATE_CONTENT_LOADED, payload)
        } else {
            setViewState(STATE_LOADING_ERROR)
        }
    }

    override fun fetchingError() {
        setViewState(STATE_LOADING_ERROR)
    }
}