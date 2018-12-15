package dynamicdrillers.soundcast.model
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Songs(
    @SerializedName("results")
    val results: ArrayList<Result> = ArrayList()
) : Parcelable

@Parcelize
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
) : Parcelable

@Parcelize
data class ACL(
    @SerializedName("*")
    val x: X = X(),
    @SerializedName("Dft58Zy6Dv")
    val dft58Zy6Dv: Dft58Zy6Dv = Dft58Zy6Dv()
) : Parcelable

@Parcelize
data class X(
    @SerializedName("read")
    val read: Boolean = false,
    @SerializedName("write")
    val write: Boolean = false
) : Parcelable

@Parcelize
data class Dft58Zy6Dv(
    @SerializedName("read")
    val read: Boolean = false,
    @SerializedName("write")
    val write: Boolean = false
) : Parcelable

@Parcelize
data class MusicFile(
    @SerializedName("__type")
    val type: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("url")
    val url: String = ""
) : Parcelable

@Parcelize
data class ThumbnailFile(
    @SerializedName("__type")
    val type: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("url")
    val url: String = ""
) : Parcelable