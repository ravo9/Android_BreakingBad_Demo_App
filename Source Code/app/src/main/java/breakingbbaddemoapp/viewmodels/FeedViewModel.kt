package breakingbbaddemoapp.viewmodels

import androidx.lifecycle.ViewModel
import breakingbbaddemoapp.utils.DataFetchingCallback
import breakingbbaddemoapp.models.SimplifiedCharacterObject
import breakingbbaddemoapp.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class FeedViewModel @Inject constructor(private val apiClient: ApiClient)
    : ViewModel() {

    fun fetchAllCharacters(callback: DataFetchingCallback) {
        apiClient.getAllCharacters().enqueue(object: Callback<List<SimplifiedCharacterObject>> {

            override fun onResponse(call: Call<List<SimplifiedCharacterObject>>?,
                                    response: Response<List<SimplifiedCharacterObject>>?) {
                response?.let {
                    if (it.isSuccessful && it.body() != null) {
                        callback.fetchingSuccessful(it.body()!!)
                    } else {
                        callback.fetchingError()
                    }
                }
            }

            override fun onFailure(call: Call<List<SimplifiedCharacterObject>>?, t: Throwable?) {
                callback.fetchingError()
            }
        })
    }
}