package com.kehes.intents

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.kehes.intents.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpSayHelloButton()
        setUpTakePhotoButton()
        setUpOpenBrowserButton()
        setUpSendTextButton()
    }
    private fun setUpSayHelloButton() {
        binding.sayHelloBtn.setOnClickListener {
            if (isValid()) {
                val intent = Intent(this, HelloActivity::class.java)
                intent.putExtra(ArgumentKey.NAME.name, binding.nameInputView.text.toString())
                startActivity(intent)
            } else {
                Toast.makeText(this, "You need input your name", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setUpTakePhotoButton() {
        binding.takePhotoBtn.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            cameraResult.launch(intent)
        }
    }

    private val cameraResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val photo = it.data?.extras?.get("data") as? Bitmap?
                photo?.let {
                    binding.newImageView.setImageBitmap(it)
                }
            }
        }

    private fun setUpOpenBrowserButton() {
        binding.openBrowserBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/vampirish"))
            startActivity(intent)
        }
    }

    private fun setUpSendTextButton() {
        binding.sendTextBtn.setOnClickListener {
            if (isValid()) {
                val intent = Intent(Intent.ACTION_SEND)
                intent.putExtra(Intent.EXTRA_TEXT, binding.nameInputView.text.toString())
                intent.type = "text/plain"
                val choseIntent = Intent.createChooser(intent, "Title")
                startActivity(choseIntent)
            } else {
                Toast.makeText(this, "You need input your name", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isValid() = !binding.nameInputView.text.isNullOrBlank()
}

enum class ArgumentKey() {
    NAME
}