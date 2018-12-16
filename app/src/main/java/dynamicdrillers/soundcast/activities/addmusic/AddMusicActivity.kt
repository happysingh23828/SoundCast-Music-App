package dynamicdrillers.soundcast.activities.addmusic

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.OpenableColumns
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.widget.Toast
import com.kaopiz.kprogresshud.KProgressHUD
import com.parse.ParseFile
import com.parse.ParseObject
import com.parse.SaveCallback
import dynamicdrillers.soundcast.R
import kotlinx.android.synthetic.main.activity_add_music.*
import java.io.*


class AddMusicActivity : AppCompatActivity() {

    var imageFileName: String = ""
    var audioFileName: String = ""
    var imageBytes: ByteArray? = null
    var audioBytes: ByteArray? = null
    lateinit var progressDialog: KProgressHUD

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_music)
        init()
        onClicks()

    }

    private fun init() {
        progressDialog = KProgressHUD(this)
        progressDialog.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setLabel("Wait").setCancellable(false)
            .setDetailsLabel("Uploading.....").setAnimationSpeed(2).setDimAmount(0.5f)
    }

    private fun onClicks() {

        upload.setOnClickListener {
            if (validateInputs()) {
                uploadFileToServer()
            } else {
                Toast.makeText(this, "Please Enter All Fields", Toast.LENGTH_SHORT).show()
            }
        }

        music_file.setOnClickListener {
            if (checkStroagePermission()) {
                openGalleryForAudio()
            } else {
                requestPermission()
            }
        }

        image_file.setOnClickListener {
            if (checkStroagePermission()) {
                openGalleryForThumbnail()
            } else {
                requestPermission()
            }
        }

        back.setOnClickListener {
            onBackPressed()
        }


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            PICK_PHOTO_REQUEST -> {
                imageFileName = getFileName(data!!.data!!)
                image_file.text = imageFileName.toEditable()
                imageBytes = convertImageToByte(data.data!!)
                thumbnail_selected.setImageBitmap(BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes!!.size))
            }
            PICK_AUDIO_REQUEST -> {
                audioFileName = getFileName(data!!.data!!)
                music_file.text = audioFileName.toEditable()

                audioBytes = convertStreamToByteArray(contentResolver.openInputStream(data.data!!)!!)
            }
        }
    }

    private fun convertImageToByte(uri: Uri): ByteArray? {
        var data: ByteArray? = null
        try {
            val cr = baseContext.contentResolver
            val inputStream = cr.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos)
            data = baos.toByteArray()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }

        return data
    }

    @Throws(IOException::class)
    fun convertStreamToByteArray(`is`: InputStream): ByteArray {
        val baos = ByteArrayOutputStream()
        val buff = ByteArray(10240)
        var i = Integer.MAX_VALUE
        i = `is`.read(buff, 0, buff.size)
        while ((i) > 0) {
            baos.write(buff, 0, i)
            i = `is`.read(buff, 0, buff.size)
        }

        return baos.toByteArray() // be sure to close InputStream in calling function
    }

    fun fileToBytes(file: File): ByteArray {
        var bytes = ByteArray(0)
        try {
            FileInputStream(file).use { inputStream ->
                bytes = ByteArray(inputStream.available())

                inputStream.read(bytes)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return bytes
    }

    private fun uploadFileToServer() {
        progressDialog.show()
        val entity = ParseObject("songs_library")
        entity.put("title", name.text.toString())
        entity.put("link", "Dummy Link")
        entity.put("thumbnail", imageFileName)
        entity.put("music_file", ParseFile(audioFileName.replace(" ", ""), audioBytes))
        entity.put("thumbnail_file", ParseFile(imageFileName.replace(" ", ""), imageBytes))

        val audioFile = ParseFile(audioFileName, audioBytes)
        val thumbFile = ParseFile(imageFileName, imageBytes)

        audioFile.saveInBackground(SaveCallback { it ->
            if (it == null) {
                thumbFile.saveInBackground(SaveCallback { it1 ->
                    if (it1 == null) {
                        entity.saveInBackground {
                            if (it == null) {
                                Toast.makeText(this, "Uploaded Successfully", Toast.LENGTH_SHORT).show()
                                finish()
                            } else {
                                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        Toast.makeText(this, it1.message, Toast.LENGTH_SHORT).show()
                    }
                })
            } else {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
        })

    }


    fun getValidFileName(name : String) : String {
        val invalidChars = Regex("[^A-Za-z0-9 ]")
        return name.replace(invalidChars,"")
    }




    private fun openGalleryForAudio() {

        val audioIntent = Intent()
        audioIntent.type = "audio/*"
        audioIntent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(audioIntent, PICK_AUDIO_REQUEST)
    }

    private fun openGalleryForThumbnail() {
        val photoIntent = Intent()
        photoIntent.type = "image/jpg*"
        photoIntent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(photoIntent, PICK_PHOTO_REQUEST)
    }


    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                PERMISSION_REQUEST_CODE
            )
        }
    }

    private fun checkStroagePermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }


    private fun validateInputs(): Boolean {
        if (name.text.toString() == "")
            return false
        if (music_file.text.toString() == "")
            return false
        if (image_file.text.toString() == "")
            return false

        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGalleryForAudio()
            }
        }

    }

    fun getFileName(uri: Uri): String {
        var result: String? = null
        if (uri.scheme == "content") {
            val cursor = contentResolver.query(uri, null, null, null, null)
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                }
            } finally {
                cursor!!.close()
            }
        }
        if (result == null) {
            result = uri.path
            val cut = result!!.lastIndexOf('/')
            if (cut != -1) {
                result = result.substring(cut + 1)
            }
        }


        return getValidFileName(result)
    }

    fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

    companion object {
        const val PICK_AUDIO_REQUEST = 102
        const val PICK_PHOTO_REQUEST = 103

        const val PERMISSION_REQUEST_CODE = 212
    }

}



