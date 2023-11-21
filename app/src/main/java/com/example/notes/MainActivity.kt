package com.example.notes

import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.Add.AddNotes
import com.example.notes.Adopter.NoteAdopter
import com.example.notes.Class.SwipeGesture
import com.example.notes.Entity.UserEntity
import com.example.notes.Search.SearchNotes
import com.example.notes.ViewModel.UserViewModel
import com.example.notes.databinding.ActivityMainBinding
import java.util.Locale


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val userViewModel: UserViewModel by viewModels()
    private lateinit var userAdopter: NoteAdopter
    private lateinit var list: ArrayList<UserEntity>

    //    private var sharedPreferences: SharedPreferences? = null
//    private var editor: SharedPreferences.Editor? = null
    private lateinit var editor: SharedPreferences.Editor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        search()
        sharedPreferences()
        floatingButton()
        changeMode()
        binding.setting.setOnClickListener {
            changeLanguage()
        }
        userAdopter = NoteAdopter(this@MainActivity, ArrayList<UserEntity>())
        binding.recycle.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = userAdopter
        }
        //

        val touchHelper = ItemTouchHelper(swipeGesture)
        touchHelper.attachToRecyclerView(binding.recycle)
        //
        userViewModel.getAllUserData(this)?.observe(this, Observer {
            userAdopter.setData(it as ArrayList<UserEntity>)
//            binding.recycle.scrollToPosition(0)
            list = ArrayList(it)
        })
    }


    private fun search() {
        binding.buttonSearch.setOnClickListener {
            val move = Intent(this, SearchNotes::class.java)
            startActivity(move)
        }
    }

    private fun floatingButton() {
        binding.floatingButton.setOnClickListener {
            val changePage = Intent(this, AddNotes::class.java)
            startActivity(changePage)
        }
    }

    val swipeGesture = object : SwipeGesture() {
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            when (direction) {
                ItemTouchHelper.LEFT -> {
                    userAdopter.deleteItem(viewHolder.adapterPosition)
                }
                ItemTouchHelper.RIGHT -> {
                    userAdopter.deleteItem(viewHolder.adapterPosition)

                }

            }
        }
    }

    private fun changeMode() {
        val statusBarColor = ContextCompat.getColor(
            this,
            if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) R.color.black else R.color.white
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().statusBarColor = statusBarColor
        }
        var sharedPreferences = getSharedPreferences("mode", MODE_PRIVATE)
        val b: Boolean = sharedPreferences.getBoolean("night", true)
        if (b) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            //            activityMainBinding.switcher.setBackgroundColor(R.color);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        binding.switcher.setOnClickListener(View.OnClickListener {
            if (b) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

                editor = sharedPreferences.edit()
                editor.putBoolean("night", false)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                editor = sharedPreferences.edit()
                editor.putBoolean("night", true)
            }
            editor.apply()
        })
    }

    private fun changeLanguage() {
        val language = arrayOf("English", "Hindi")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Choose Language")
        builder.setSingleChoiceItems(
            language, -1
        ) { dialog, which ->
            if (which == 0) {
                setLocale("")
                recreate()
            } else {
                setLocale("hi")
                recreate()
            }
        }
        builder.create()
        builder.show()
    }

    private fun setLocale(language: String) {
        val locale = Locale(language)
        val configuration = Configuration()
        configuration.locale = locale
        baseContext.resources.updateConfiguration(
            configuration, baseContext.resources.displayMetrics
        )
        val editor = getSharedPreferences("setting", MODE_PRIVATE).edit()
        editor.putString("hindi", language)
        editor.apply()
    }

    private fun sharedPreferences() {
        val editor = getSharedPreferences("setting", MODE_PRIVATE)
        setLocale(editor.getString("hindi", "")!!)
    }
}