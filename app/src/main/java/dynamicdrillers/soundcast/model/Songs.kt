package dynamicdrillers.soundcast.model
import com.google.gson.annotations.SerializedName


data class Songs(
    @SerializedName("results")
    val results: List<Result> = listOf()
)

data class Result(
    @SerializedName("ACL")
    val aCL: ACL = ACL(),
    @SerializedName("createdAt")
    val createdAt: String = "",
    @SerializedName("link")
    val link: String = "",
    @SerializedName("music_file")
    val musicFile: MusicFile = MusicFile(),
    @SerializedName("objectId")
    val objectId: String = "",
    @SerializedName("thumbnail")
    val thumbnail: String = "",
    @SerializedName("thumbnail_file")
    val thumbnailFile: ThumbnailFile = ThumbnailFile(),
    @SerializedName("title")
    val title: String = "",
    @SerializedName("updatedAt")
    val updatedAt: String = ""
)

data class ACL(
    @SerializedName("*")
    val x: X = X(),
    @SerializedName("Dft58Zy6Dv")
    val dft58Zy6Dv: Dft58Zy6Dv = Dft58Zy6Dv()
)

data class X(
    @SerializedName("read")
    val read: Boolean = false,
    @SerializedName("write")
    val write: Boolean = false
)

data class Dft58Zy6Dv(
    @SerializedName("read")
    val read: Boolean = false,
    @SerializedName("write")
    val write: Boolean = false
)

data class MusicFile(
    @SerializedName("__type")
    val type: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("url")
    val url: String = ""
)

data class ThumbnailFile(
    @SerializedName("__type")
    val type: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("url")
    val url: String = ""
)