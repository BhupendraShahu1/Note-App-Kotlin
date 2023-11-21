package com.example.notes.Adopter

import android.app.Application
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.Entity.UserEntity
import com.example.notes.R
import com.example.notes.Update.UpdateActivity
import com.example.notes.ViewModel.UserViewModel

class NoteAdopter(private val context: Context, private var arrayList: ArrayList<UserEntity>) :
    RecyclerView.Adapter<NoteAdopter.setItem>() {
    //    var userViewModel: UserViewModel by view
    var userViewModel: UserViewModel? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): setItem {
        return setItem(LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false))

    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: setItem, position: Int) {
        val user: UserEntity = arrayList[position]
        holder.time.text = user.time
        holder.tittle.text = user.tittle
        holder.content.text = user.content
        // swipe delete item


        //
        holder.linearLayout.setOnClickListener {
            val move = Intent(context, UpdateActivity::class.java)
            var id = arrayList.get(position).id
            var tittle = arrayList.get(position).tittle
            var content = arrayList.get(position).content
            var time = arrayList.get(position).time
            move.putExtra("id", id)
            move.putExtra("tittle", tittle)
            move.putExtra("content", content)
            move.putExtra("time", time)
            context.startActivity(move)
        }
    }

    fun setData(userEntities: java.util.ArrayList<UserEntity>) {
        this.arrayList = userEntities
        notifyDataSetChanged()
    }

    inner class setItem(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tittle: TextView = itemView.findViewById(R.id.id_tittle)
        val content: TextView = itemView.findViewById(R.id.id_content)
        val time: TextView = itemView.findViewById(R.id.id_time)
        val linearLayout: LinearLayout = itemView.findViewById(R.id.id_linearLayout)
    }

    fun deleteItem(i: Int) {
//        var id = arrayList.get(i).id
//        var tittle = arrayList.get(i).tittle
//        var content = arrayList.get(i).content
//        var time = arrayList.get(i).time
        userViewModel =
            ViewModelProvider.AndroidViewModelFactory.getInstance(context.applicationContext as Application)
                .create(UserViewModel::class.java)
        val data = UserEntity(
            arrayList.get(i).id,
            arrayList.get(i).tittle,
            arrayList.get(i).content,
            arrayList.get(i).time,
        )
        userViewModel!!.delete(context, data)
        arrayList.removeAt(i)
        notifyDataSetChanged()
    }

//    fun add(pos: Int) {
//        userViewModel =
//            ViewModelProvider.AndroidViewModelFactory.getInstance(context.applicationContext as Application)
//                .create(UserViewModel::class.java)
//        val data = UserEntity(
//            arrayList.get(pos).id,
//            arrayList.get(pos).tittle,
//            arrayList.get(pos).content,
//            arrayList.get(pos).time,
//        )
//        userViewModel!!.insert(context, data)
//        arrayList.add(pos, data)
//        notifyDataSetChanged()
//    }
}