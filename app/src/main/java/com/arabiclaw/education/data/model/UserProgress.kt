package com.arabiclaw.education.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "lesson_progress",
    foreignKeys = [
        ForeignKey(
            entity = Lesson::class,
            parentColumns = ["id"],
            childColumns = ["lessonId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("lessonId")]
)
data class LessonProgress(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val lessonId: Long,
    val isCompleted: Boolean = false,
    val progress: Int = 0, // 0-100
    val lastAccessedAt: Long = System.currentTimeMillis(),
    val completedAt: Long? = null,
    val timeSpent: Long = 0 // in seconds
)

@Entity(
    tableName = "quiz_attempts",
    foreignKeys = [
        ForeignKey(
            entity = Quiz::class,
            parentColumns = ["id"],
            childColumns = ["quizId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("quizId")]
)
data class QuizAttempt(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val quizId: Long,
    val score: Int,
    val totalQuestions: Int,
    val correctAnswers: Int,
    val timeSpent: Long, // in seconds
    val isPassed: Boolean,
    val attemptDate: Long = System.currentTimeMillis(),
    val answers: Map<Long, String> // questionId to answer
)

@Entity(tableName = "bookmarks")
data class Bookmark(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val lessonId: Long,
    val createdAt: Long = System.currentTimeMillis(),
    val note: String? = null
)

@Entity(tableName = "achievements")
data class Achievement(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val titleAr: String,
    val descriptionAr: String,
    val iconRes: String,
    val isUnlocked: Boolean = false,
    val unlockedAt: Long? = null,
    val requirement: String, // JSON string describing requirement
    val category: String
)

@Entity(tableName = "user_statistics")
data class UserStatistics(
    @PrimaryKey
    val id: Long = 1,
    val totalLessonsCompleted: Int = 0,
    val totalQuizzesTaken: Int = 0,
    val totalQuizzesPassed: Int = 0,
    val totalTimeSpent: Long = 0, // in seconds
    val currentStreak: Int = 0,
    val longestStreak: Int = 0,
    val lastActivityDate: Long = System.currentTimeMillis(),
    val totalPoints: Int = 0,
    val level: Int = 1
)
