package com.example.notes.Add

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.notes.R
import com.example.notes.Entity.UserEntity
import com.example.notes.ViewModel.UserViewModel
import com.example.notes.databinding.ActivityAddNotesBinding
import java.text.SimpleDateFormat
import java.util.Date

class AddNotes : AppCompatActivity() {
    val userViewModel: UserViewModel by viewModels()
    private lateinit var addBinding: ActivityAddNotesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_notes)
        val sdf = SimpleDateFormat("E,MMMM yy, hh:mm")
        val currentDate = sdf.format(Date())
        addBinding.setDate.setText(currentDate)
        addBinding.backImage.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        addData()
        statusBar()
    }

    private fun addData() {
        val sdf = SimpleDateFormat("dd MMMM, y")
        val currentDate = sdf.format(Date())
        addBinding.add.setOnClickListener {
            val tittle = addBinding.addTittle.text.toString()
            val content = addBinding.addContent.text.toString()
            if (tittle.trim().isEmpty()) {
                Toast.makeText(this, "tittle is empty", Toast.LENGTH_LONG).show()
            } else if (content.trim().isEmpty()) {
                Toast.makeText(this, "content is empty", Toast.LENGTH_LONG).show()
            } else {
                userViewModel.insert(this@AddNotes, UserEntity(tittle, content, currentDate))
                onBackPressedDispatcher.onBackPressed()
            }
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