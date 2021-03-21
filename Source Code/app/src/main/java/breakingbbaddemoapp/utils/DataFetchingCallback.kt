package breakingbbaddemoapp.utils

interface DataFetchingCallback {
    fun fetchingSuccessful(payload: Any)
    fun fetchingError()
}