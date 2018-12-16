package dynamicdrillers.soundcast

import android.app.Application
import com.parse.Parse

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Parse.initialize(
            Parse.Configuration.Builder(this)
                .applicationId("VSPdpDKRMND382hqIRFIaiVLgbkhM0E1rL32l1SQ")
                .clientKey("NnOwo2ejzrpQJD98uF9weupAo2AFH305DCOLVaBQ")
                .server("https://parseapi.back4app.com/")
                .build()
        )
    }
}