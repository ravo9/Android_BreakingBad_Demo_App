package breakingbbaddemoapp.network

import breakingbbaddemoapp.models.CompleteCharacterObject
import retrofit2.Call
import retrofit2.http.GET
import breakingbbaddemoapp.models.SimplifiedCharacterObject
import retrofit2.http.Path

// External gate for communication with API endpoints (operated by Retrofit)
interface ApiClient {

    @GET(NetworkConstants.GET_ALL_CHARACTERS_ENDPOINT_URL)
    fun getAllCharacters(): Call<List<SimplifiedCharacterObject>>

    @GET(NetworkConstants.GET_SINGLE_CHARACTER_BY_ID_ENDPOINT_URL)
    fun getSingleCharacterById(@Path("id") id: Int): Call<List<CompleteCharacterObject>>
}