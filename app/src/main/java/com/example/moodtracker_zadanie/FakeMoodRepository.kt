package com.example.moodtracker_zadanie

import java.util.UUID

object FakeMoodRepository {
    private val moodList = mutableListOf<MoodEntry>()

    fun addMood(entry: MoodEntry) {
        moodList.add(0, entry)
    }

    fun getAllMoods(): List<MoodEntry> {
        return moodList
    }

    fun getMoodById(id: UUID): MoodEntry? {
        return moodList.find { it.id == id }
    }

    fun removeMood(id: UUID) {
        moodList.removeAll { it.id == id }
    }

    fun clear() {
        moodList.clear()
    }
}
