package com.example.notes.Search

import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notes.Adopter.NoteAdopter
import com.example.notes.Entity.UserEntity
import com.example.notes.R
import com.example.notes.ViewModel.UserViewModel
import com.example.notes.databinding.ActivitySearchNotesBinding

class SearchNotes : AppCompatActivity() {
    private lateinit var searchBinding: ActivitySearchNotesBinding
    private val userViewModel: UserViewModel by viewModels()
    private lateinit var userAdopter: NoteAdopter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchBinding =
            DataBindingUtil.setContentView(this@SearchNotes, R.layout.activity_search_notes)
        statusBar()
        backButton()
        searchData()
        userAdopter = NoteAdopter(this@SearchNotes, ArrayList<UserEntity>())
        searchBinding.showSearchRecycle.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@SearchNotes)
            adapter = userAdopter
        }
    }

    private fun searchData() {
        searchBinding.buttonSearchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String): Boolean {
                val text = newText.trim()
                userViewModel.search(this@SearchNotes, text)?.observe(this@SearchNotes, Observer {
                        userAdopter.setData(it as ArrayList<UserEntity>)
                    })
                return true
            }
        })
    }
    private fun backButton() {
        searchBinding.backImageButton.setOnClickListener {
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