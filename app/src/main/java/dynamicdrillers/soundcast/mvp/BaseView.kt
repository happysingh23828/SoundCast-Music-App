package dynamicdrillers.soundcast.mvp

interface BaseView<in T> {
    fun setPresenter(presenter: T)
}