package com.example.moodtracker_zadanie

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date
import java.util.UUID

@Parcelize
data class MoodEntry(
    val id: UUID = UUID.randomUUID(),
    val opisDnia: String,
    val nastroj: MoodType,
    val wydarzenia: String,
    val spalemDobrze: Boolean,
    val aktywnyFizycznie: Boolean,
    val spotkalemSie: Boolean,
    val rating: Float,
    val czyWazne: Boolean,
    val date: Date = Date()
) : Parcelable

enum class MoodType {
    Wesoły, Przeciętny, Smutny
}
