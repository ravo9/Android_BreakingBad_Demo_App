package breakingbbaddemoapp.viewmodels

import androidx.lifecycle.ViewModel
import breakingbbaddemoapp.models.CompleteCharacterObject
import breakingbbaddemoapp.network.ApiClient
import breakingbbaddemoapp.utils.DataFetchingCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class DetailedViewViewModel @Inject constructor(private val apiClient: ApiClient)
    : ViewModel() {

    fun fetchSelectedCharacter(callback: DataFetchingCallback, characterId: Int) {
        apiClient.getSingleCharacterById(characterId).enqueue(object: Callback<List<CompleteCharacterObject>> {

            override fun onResponse(call: Call<List<CompleteCharacterObject>>?,
                                    response: Response<List<CompleteCharacterObject>>?) {
                response?.let {
                    if (it.isSuccessful && !it.body().isNullOrEmpty()) {
                        callback.fetchingSuccessful(it.body()!!.first())
                    } else {
                        callback.fetchingError()
                    }
                }
            }

            override fun onFailure(call: Call<List<CompleteCharacterObject>>?, t: Throwable?) {
                callback.fetchingError()
            }
        })
    }
}