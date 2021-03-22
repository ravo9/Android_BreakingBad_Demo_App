package breakingbbaddemoapp.dependencyinjection

import dagger.Module
import dagger.Provides
import breakingbbaddemoapp.network.ApiClient
import breakingbbaddemoapp.network.ApiClientBuilder
import breakingbbaddemoapp.utils.FilteringTools
import breakingbbaddemoapp.utils.StringFormatter
import javax.inject.Singleton

@Module
class FeedModule {

    @Provides
    @Singleton
    fun providesApiClient(): ApiClient {
        return ApiClientBuilder.apiClient()
    }

    @Provides
    @Singleton
    fun providesStringFormatter(): StringFormatter {
        return StringFormatter()
    }

    @Provides
    @Singleton
    fun providesFilteringTools(): FilteringTools {
        return FilteringTools()
    }
}