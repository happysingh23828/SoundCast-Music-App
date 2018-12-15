package dynamicdrillers.soundcast.mvp

import dynamicdrillers.soundcast.model.Songs
import dynamicdrillers.soundcast.network.ApiInterface
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory





class ApiCaller : ApiWrapper {

    companion object ApiCaller {
        const val BASE_URL = "https://soundcast.back4app.io"
    }

    override fun getSongsList(callback: ApiWrapper.CallBack<Songs>) {
        retrofitBuilder().getSongsList().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Songs>{

                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: Songs) {
                    t.let {
                        callback.onSuccess(t)
                    }
                }

                override fun onError(e: Throwable) {
                    e.message?.let {
                        callback.onFailure(e.message.toString())
                    }
                }
            })
    }


    private fun retrofitBuilder() : ApiInterface {

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        return retrofit.create(ApiInterface::class.java)
    }


}