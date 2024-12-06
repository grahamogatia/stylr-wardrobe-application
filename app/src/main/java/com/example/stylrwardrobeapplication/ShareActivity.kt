
package com.example.stylrwardrobeapplication


import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.facebook.CallbackManager
import com.facebook.FacebookSdk
import com.facebook.share.widget.ShareDialog
import android.content.Intent
import com.example.stylrwardrobeapplication.databinding.ActivityShareBinding
import com.facebook.share.model.*

class ShareActivity : AppCompatActivity() {
    private val REQUEST_VIDEO_CODE: Int = 1000
    lateinit var activityShareBinding: ActivityShareBinding
    lateinit var shareDialog: ShareDialog
    lateinit var callbackManager: CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize view binding
        activityShareBinding = ActivityShareBinding.inflate(layoutInflater)

        // Initialize the Facebook SDK
        FacebookSdk.sdkInitialize(applicationContext)

        // Set the content view to the activity's layout
        setContentView(activityShareBinding.root)

        // Create an instance of CallbackManager
        callbackManager = CallbackManager.Factory.create()

        // Create a ShareDialog
        shareDialog = ShareDialog(this)

        // Share a link to Facebook
        activityShareBinding.btnShareLink.setOnClickListener {
            val linkContent = ShareLinkContent.Builder()
                .setContentUrl(Uri.parse("https://www.youtube.com/"))
                .setQuote("Useful link")
                .build()

            // Check if ShareLinkContent can be shown and display it
            if (ShareDialog.canShow(ShareLinkContent::class.java)) {
                shareDialog.show(linkContent)
            }
        }

        // Share a photo to Facebook
        activityShareBinding.btnSharePhoto.setOnClickListener {
            val image: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_head)
            val photo = SharePhoto.Builder()
                .setBitmap(image)
                .build()

            // Check if SharePhotoContent can be shown and display it
            if (ShareDialog.canShow(SharePhotoContent::class.java)) {
                val photoContent = SharePhotoContent.Builder()
                    .addPhoto(photo)
                    .build()
                shareDialog.show(photoContent)
            }
        }

        // Share a video to Facebook
        activityShareBinding.btnShareVideo.setOnClickListener {
            // Open video selector
            val sharingIntent = Intent(Intent.ACTION_GET_CONTENT).apply {
                type = "video/*"
            }
            startActivityForResult(
                Intent.createChooser(sharingIntent, "Select a video"),
                REQUEST_VIDEO_CODE
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // Pass the activity result to the Facebook SDK's CallbackManager
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && requestCode == REQUEST_VIDEO_CODE) {
            val videoUri = data?.data

            val video = ShareVideo.Builder()
                .setLocalUrl(videoUri)
                .build()

            val videoContent = ShareVideoContent.Builder()
                .setVideo(video)
                .setContentTitle("This is a useful video")
                .setContentDescription("Video made by me")
                .build()

            // Check if ShareVideoContent can be shown and display it
            if (ShareDialog.canShow(ShareVideoContent::class.java)) {
                shareDialog.show(videoContent)
            }
        }
    }
}
