package com.arabiclaw.education.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(
    tableName = "quizzes",
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
data class Quiz(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val categoryId: Long,
    val titleAr: String,
    val descriptionAr: String,
    val difficulty: DifficultyLevel,
    val passingScore: Int = 70,
    val timeLimit: Int? = null, // in minutes, null for unlimited
    val order: Int,
    val relatedLessonId: Long? = null
) : Parcelable

@Parcelize
@Entity(
    tableName = "questions",
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
data class Question(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val quizId: Long,
    val questionTextAr: String,
    val questionType: QuestionType,
    val options: List<String>, // For multiple choice
    val correctAnswer: String, // Index for multiple choice, text for others
    val explanationAr: String,
    val points: Int = 1,
    val order: Int
) : Parcelable

enum class QuestionType(val nameAr: String) {
    MULTIPLE_CHOICE("اختيار من متعدد"),
    TRUE_FALSE("صح أو خطأ"),
    FILL_BLANK("املأ الفراغ"),
    MATCHING("مطابقة")
}
