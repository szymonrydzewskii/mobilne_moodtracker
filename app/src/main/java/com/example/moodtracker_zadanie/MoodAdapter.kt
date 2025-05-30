package com.example.moodtracker_zadanie.adapter

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moodtracker_zadanie.R
import com.example.moodtracker_zadanie.MoodEntry
import com.example.moodtracker_zadanie.MoodType
import com.example.moodtracker_zadanie.OnMoodClickListener
import java.text.SimpleDateFormat
import java.util.*


class MoodAdapter(private val moodList: List<MoodEntry>, private val listener: OnMoodClickListener) :
    RecyclerView.Adapter<MoodAdapter.MoodViewHolder>() {

    class MoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dateText: TextView = itemView.findViewById(R.id.date_text)
        val descriptionText: TextView = itemView.findViewById(R.id.description_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoodViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_mood_entry, parent, false)
        return MoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: MoodViewHolder, position: Int) {

        val moodEntry = moodList[position]
        holder.descriptionText.text = moodEntry.opisDnia

        val backgroundColor = when (moodEntry.nastroj) {
            MoodType.Wesoły -> Color.parseColor("#C8E6C9")
            MoodType.Przeciętny -> Color.parseColor("#FFF9C4")
            MoodType.Smutny -> Color.parseColor("#BBDEFB")
        }

        holder.itemView.background = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = 32f
            setColor(backgroundColor)
        }
        holder.itemView.apply {
            elevation = 8f
            outlineProvider = ViewOutlineProvider.BACKGROUND
            clipToOutline = true
        }


        holder.itemView.setOnClickListener {
            listener.onMoodClick(moodEntry)
        }

        val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        holder.dateText.text = formatter.format(moodEntry.date)

        val iconView = holder.itemView.findViewById<ImageView>(R.id.nastrojIkona)
        val iconRes = when (moodEntry.nastroj){
            MoodType.Wesoły -> R.drawable.happy
            MoodType.Przeciętny -> R.drawable.mid
            MoodType.Smutny -> R.drawable.sad
        }
        iconView.setImageResource(iconRes)
    }

    override fun getItemCount(): Int = moodList.size
}
