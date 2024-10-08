package com.kehes.intents

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kehes.intents.databinding.ActivityHelloBinding

class HelloActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHelloBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHelloBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.extras?.let {
            val name = it.getString(ArgumentKey.NAME.name)
            val newName = it.getString(Intent.EXTRA_TEXT)

            var showName: String? = null
            showName = newName ?: name
            binding.helloView.text = getString(R.string.hello_any, showName)
        }
    }

}