package breakingbbaddemoapp.dependencyinjection

import breakingbbaddemoapp.ui.DetailedViewFragment
import dagger.Component
import breakingbbaddemoapp.ui.FeedActivity
import breakingbbaddemoapp.viewmodels.DetailedViewViewModel
import breakingbbaddemoapp.viewmodels.FeedViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class, FeedModule::class, ViewModelModule::class))
interface MainComponent {
    fun inject(feedActivity: FeedActivity)
    fun inject(feedFragment: DetailedViewFragment)
    fun inject(feedViewModel: FeedViewModel)
    fun inject(detailedViewViewModel: DetailedViewViewModel)
}