package com.deiaa.qrcodeapp

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.MotionEvent
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main2.*
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)


        MobileAds.initialize(this)
        val adBuilder=AdRequest.Builder().build()
        adView1.loadAd(adBuilder)



        val intent=intent
        val text=intent.getStringExtra("text")
        val num =intent.getStringExtra("num")



        if(num.equals("1"))
            imageView4.setImageResource(R.drawable.whatsup_logo)
        else if(num.equals("2"))
            imageView4.setImageResource(R.drawable.telegram_logo)

        //Toast.makeText(this,text.toString(), Toast.LENGTH_SHORT).show()

        val writer= QRCodeWriter()
        try
        {
            val bitMatrix=writer.encode(text.toString(),
                BarcodeFormat.QR_CODE,512,512)
            //Toast.makeText(this,ccp.textView_selectedCountry.toString(),Toast.LENGTH_SHORT).show()
            val bmp = Bitmap.createBitmap(bitMatrix.width,bitMatrix.height, Bitmap.Config.RGB_565)

            for(x in 0 until  bitMatrix.height){
                for(y in 0 until  bitMatrix.height) {
                    bmp.setPixel(x, y,
                        if (bitMatrix[x,y])
                            Color.BLACK
                        else
                            Color.WHITE
                    )
                }
            }
            imageView3.setImageBitmap(bmp)
        }
        catch (e: WriterException)
        {
            e.printStackTrace()
        }
        //Toast.makeText(this,text.toString(), Toast.LENGTH_SHORT).show()

        button.setOnTouchListener(object : View.OnTouchListener {

            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                when (event?.action) {
                    MotionEvent.ACTION_DOWN ->
                        button.startAnimation(
                            AnimationUtils.loadAnimation(
                                this@MainActivity2,
                                R.anim.scale_up
                            )
                        )

                    MotionEvent.ACTION_UP ->
                        button.startAnimation(
                            AnimationUtils.loadAnimation(
                                this@MainActivity2,
                                R.anim.scale_down
                            )
                        )

                }

                return v?.onTouchEvent(event) ?: true
            }
        })

    }



    fun SaveImg(view: View) {

        val bitmap = (imageView3.drawable as BitmapDrawable).bitmap

        saveToGallery(this,bitmap,"QR")

//        Toast.makeText(
//            this@MainActivity2,
//            "Saved",
//            Toast.LENGTH_LONG
//        ).show()
    }

    fun saveToGallery(context: Context, bitmap: Bitmap, albumName: String) {
        val filename = "${System.currentTimeMillis()}.png"
        val write: (OutputStream) -> Boolean = {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
                put(MediaStore.MediaColumns.RELATIVE_PATH, "${Environment.DIRECTORY_DCIM}/$albumName")
            }

            context.contentResolver.let {
                it.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)?.let { uri ->
                    it.openOutputStream(uri)?.let(write)
                }
            }
        } else {
            val imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString() + File.separator + albumName
            val file = File(imagesDir)
            if (!file.exists()) {
                file.mkdir()
            }
            val image = File(imagesDir, filename)
            write(FileOutputStream(image))
        }
    }


}