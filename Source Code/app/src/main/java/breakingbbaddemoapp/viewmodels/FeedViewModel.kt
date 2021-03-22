package breakingbbaddemoapp.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import breakingbbaddemoapp.constants.LogTags
import breakingbbaddemoapp.utils.DataFetchingCallback
import breakingbbaddemoapp.models.SimplifiedCharacterObject
import breakingbbaddemoapp.network.ApiClient
import breakingbbaddemoapp.utils.FilteringTools
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class FeedViewModel @Inject constructor(private val apiClient: ApiClient, private val filteringTools: FilteringTools)
    : ViewModel() {

    private var cachedAllCharactersList: List<SimplifiedCharacterObject>? = null

    fun getCharacters(callback: DataFetchingCallback, filterNamePhrase: String?, filterSeason: Int?) {

        if (cachedAllCharactersList != null) {
            var results = cachedAllCharactersList!!
            results = filteringTools.filterResults(results, filterNamePhrase, filterSeason)
            callback.fetchingSuccessful(results)
        } else {
            apiClient.getAllCharacters().enqueue(object: Callback<List<SimplifiedCharacterObject>> {

                override fun onResponse(call: Call<List<SimplifiedCharacterObject>>?,
                                        response: Response<List<SimplifiedCharacterObject>>?
                ) {
                    response?.let {
                        if (it.isSuccessful && it.body() != null) {
                            cachedAllCharactersList = it.body()

                            var results = it.body()!!
                            results = filteringTools.filterResults(results, filterNamePhrase, filterSeason)
                            callback.fetchingSuccessful(results)
                        } else {
                            callback.fetchingError()
                            it.errorBody()?.let {
                                Log.e(LogTags.NETWORK_ERROR, it.string())
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<List<SimplifiedCharacterObject>>?, t: Throwable?) {
                    callback.fetchingError()
                    t?.let {
                        Log.e(LogTags.NETWORK_ERROR, it.message)
                    }
                }
            })
        }
    }
}