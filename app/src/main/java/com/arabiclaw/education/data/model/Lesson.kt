package com.arabiclaw.education.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(
    tableName = "lessons",
    foreignKeys = [
        ForeignKey(
            entity = LawCategory::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("categoryId")]
)
data class Lesson(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val categoryId: Long,
    val titleAr: String,
    val contentAr: String,
    val summaryAr: String,
    val keyPoints: List<String>,
    val examples: List<String>,
    val difficulty: DifficultyLevel,
    val order: Int,
    val estimatedReadingTime: Int, // in minutes
    val relatedLessonIds: List<Long> = emptyList(),
    val tags: List<String> = emptyList(),
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
) : Parcelable

enum class DifficultyLevel(val nameAr: String) {
    BEGINNER("مبتدئ"),
    INTERMEDIATE("متوسط"),
    ADVANCED("متقدم")
}
