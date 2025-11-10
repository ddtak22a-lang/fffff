package com.arabiclaw.education.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "law_categories")
data class LawCategory(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nameAr: String,
    val descriptionAr: String,
    val iconRes: String,
    val color: String,
    val order: Int,
    val totalLessons: Int = 0,
    val totalQuizzes: Int = 0
) : Parcelable

enum class CategoryType(val nameAr: String) {
    CIVIL("القانون المدني"),
    CRIMINAL("القانون الجنائي"),
    ADMINISTRATIVE("القانون الإداري"),
    COMMERCIAL("القانون التجاري"),
    CONSTITUTIONAL("القانون الدستوري"),
    LABOR("قانون العمل"),
    INTERNATIONAL("القانون الدولي"),
    FAMILY("قانون الأسرة"),
    TAX("القانون الضريبي"),
    MARITIME("القانون البحري")
}
