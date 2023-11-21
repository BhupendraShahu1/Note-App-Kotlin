package com.example.notes.Update

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.notes.Entity.UserEntity
import com.example.notes.R
import com.example.notes.ViewModel.UserViewModel
import com.example.notes.databinding.ActivityUpdateBinding

class UpdateActivity : AppCompatActivity() {
    private val userViewModel: UserViewModel by viewModels()
    private lateinit var updateBinding: ActivityUpdateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        updateBinding =
            DataBindingUtil.setContentView(this@UpdateActivity, R.layout.activity_update)
        statusBar()
        val intent = intent
        val id = intent.getIntExtra("id", 0)
        val title = intent.getStringExtra("tittle")
        val content = intent.getStringExtra("content")
        val time = intent.getStringExtra("time")
        updateBinding.updateTittle.text = title
        updateBinding.updateContent.setText(content)
        updateBinding.setDate.text = time
        updateBinding.update.setOnClickListener {
            val get_content = updateBinding.updateContent.text.toString()
            if (get_content.trim().isEmpty()) {
                Toast.makeText(this@UpdateActivity, "content is empty", Toast.LENGTH_LONG).show()
            } else {
                userViewModel.update(
                    this@UpdateActivity, UserEntity(id, title!!, get_content, time!!)
                )
                onBackPressedDispatcher.onBackPressed()
            }
        }
        updateBinding.updateToBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun statusBar() {
        val statusBarColor = ContextCompat.getColor(
            this,
            if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) R.color.black else R.color.white
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().statusBarColor = statusBarColor
        }
    }
}