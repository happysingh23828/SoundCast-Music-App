package dynamicdrillers.soundcast.network

import dynamicdrillers.soundcast.model.Songs
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers


interface ApiInterface {

    @Headers(
        "X-Parse-Application-Id: VSPdpDKRMND382hqIRFIaiVLgbkhM0E1rL32l1SQ",
        "X-Parse-REST-API-Key: E4ZeObhQv3XoHaQ3Q6baHGgbDPOkuO9jPlY9gzgA"
    )
    @GET("/classes/songs_library")
    fun getSongsList(): Observable<Songs>

}