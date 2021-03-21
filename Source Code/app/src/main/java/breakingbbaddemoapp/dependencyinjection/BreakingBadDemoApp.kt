package breakingbbaddemoapp.dependencyinjection

import android.app.Application
import android.content.Context

class BreakingBadDemoApp : Application() {

    companion object {
        lateinit var appContext: Context
        lateinit var mainComponent: MainComponent
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this
        mainComponent = DaggerMainComponent.builder()
            .appModule(AppModule(this)).feedModule(FeedModule()).build()
    }
}