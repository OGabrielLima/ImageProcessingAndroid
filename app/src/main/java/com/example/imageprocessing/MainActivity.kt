package com.example.imageprocessing

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    //Grupo formado por:
    //Gabriel de Freitas Lima
    //Igor Rubio Lazarotto
    //João Vitor Perin
    //Thiago Jungles Caron

    val REQUEST_IMAGE_CAPTURE = 1
    private lateinit var imageView: ImageView
    private lateinit var take_picture_btn: Button
    private lateinit var apply_filter_btn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageView = findViewById(R.id.imageView)

        take_picture_btn = findViewById(R.id.take_picture)
        take_picture_btn.setOnClickListener{
            dispatchTakePictureIntent()
        }


        apply_filter_btn = findViewById(R.id.apply_filter)
        apply_filter_btn.setOnClickListener{
            imageView.buildDrawingCache()
            val bmap = imageView.drawingCache
            imageView.setImageBitmap(convertToGrayscale(bmap))
        }
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            imageView.setImageBitmap(imageBitmap)
        }
    }

    private fun convertToGrayscale(bitmap: Bitmap): Bitmap {
        val width = bitmap.width
        val height = bitmap.height

        val grayscaleBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        for (x in 0 until width) {
            for (y in 0 until height) {
                val pixel = bitmap.getPixel(x, y)
                val red = Color.red(pixel)
                val green = Color.green(pixel)
                val blue = Color.blue(pixel)

                val gray = (red + green + blue) / 3
                val grayPixel = Color.rgb(gray, gray, gray)

                grayscaleBitmap.setPixel(x, y, grayPixel)
            }
        }

        return grayscaleBitmap
    }

}