package com.arabiclaw.education.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.arabiclaw.education.data.model.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        LawCategory::class,
        Lesson::class,
        Quiz::class,
        Question::class,
        LessonProgress::class,
        QuizAttempt::class,
        Bookmark::class,
        Achievement::class,
        UserStatistics::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class LegalEducationDatabase : RoomDatabase() {

    abstract fun legalEducationDao(): LegalEducationDao

    companion object {
        @Volatile
        private var INSTANCE: LegalEducationDatabase? = null

        fun getDatabase(context: Context): LegalEducationDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LegalEducationDatabase::class.java,
                    "legal_education_database"
                )
                    .addCallback(DatabaseCallback(context))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class DatabaseCallback(private val context: Context) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                CoroutineScope(Dispatchers.IO).launch {
                    populateDatabase(database.legalEducationDao())
                }
            }
        }

        private suspend fun populateDatabase(dao: LegalEducationDao) {
            // Insert initial data
            val categories = DataProvider.getCategories()
            dao.insertCategories(categories)

            val lessons = DataProvider.getLessons()
            dao.insertLessons(lessons)

            val quizzes = DataProvider.getQuizzes()
            dao.insertQuizzes(quizzes)

            val questions = DataProvider.getQuestions()
            dao.insertQuestions(questions)

            val achievements = DataProvider.getAchievements()
            dao.insertAchievements(achievements)

            // Initialize user statistics
            dao.insertUserStatistics(UserStatistics())
        }
    }
}
