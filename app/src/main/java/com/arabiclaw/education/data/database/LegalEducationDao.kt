package com.arabiclaw.education.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.arabiclaw.education.data.model.*

@Dao
interface LegalEducationDao {

    // Category operations
    @Query("SELECT * FROM law_categories ORDER BY `order`")
    fun getAllCategories(): LiveData<List<LawCategory>>

    @Query("SELECT * FROM law_categories WHERE id = :categoryId")
    suspend fun getCategoryById(categoryId: Long): LawCategory?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(categories: List<LawCategory>)

    // Lesson operations
    @Query("SELECT * FROM lessons WHERE categoryId = :categoryId ORDER BY `order`")
    fun getLessonsByCategory(categoryId: Long): LiveData<List<Lesson>>

    @Query("SELECT * FROM lessons WHERE id = :lessonId")
    suspend fun getLessonById(lessonId: Long): Lesson?

    @Query("SELECT * FROM lessons WHERE titleAr LIKE '%' || :query || '%' OR contentAr LIKE '%' || :query || '%'")
    fun searchLessons(query: String): LiveData<List<Lesson>>

    @Query("SELECT * FROM lessons ORDER BY `order` LIMIT :limit")
    fun getRecentLessons(limit: Int = 10): LiveData<List<Lesson>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLessons(lessons: List<Lesson>)

    // Quiz operations
    @Query("SELECT * FROM quizzes WHERE categoryId = :categoryId ORDER BY `order`")
    fun getQuizzesByCategory(categoryId: Long): LiveData<List<Quiz>>

    @Query("SELECT * FROM quizzes WHERE id = :quizId")
    suspend fun getQuizById(quizId: Long): Quiz?

    @Query("SELECT * FROM quizzes")
    fun getAllQuizzes(): LiveData<List<Quiz>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuizzes(quizzes: List<Quiz>)

    // Question operations
    @Query("SELECT * FROM questions WHERE quizId = :quizId ORDER BY `order`")
    suspend fun getQuestionsByQuizId(quizId: Long): List<Question>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestions(questions: List<Question>)

    // Lesson progress operations
    @Query("SELECT * FROM lesson_progress WHERE lessonId = :lessonId")
    suspend fun getLessonProgress(lessonId: Long): LessonProgress?

    @Query("SELECT * FROM lesson_progress WHERE isCompleted = 1")
    fun getCompletedLessons(): LiveData<List<LessonProgress>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLessonProgress(progress: LessonProgress)

    @Query("DELETE FROM lesson_progress WHERE lessonId = :lessonId")
    suspend fun deleteLessonProgress(lessonId: Long)

    // Quiz attempt operations
    @Query("SELECT * FROM quiz_attempts WHERE quizId = :quizId ORDER BY attemptDate DESC")
    fun getQuizAttempts(quizId: Long): LiveData<List<QuizAttempt>>

    @Query("SELECT * FROM quiz_attempts ORDER BY attemptDate DESC LIMIT :limit")
    fun getRecentQuizAttempts(limit: Int = 10): LiveData<List<QuizAttempt>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuizAttempt(attempt: QuizAttempt): Long

    // Bookmark operations
    @Query("SELECT * FROM bookmarks ORDER BY createdAt DESC")
    fun getAllBookmarks(): LiveData<List<Bookmark>>

    @Query("SELECT * FROM bookmarks WHERE lessonId = :lessonId")
    suspend fun getBookmarkByLessonId(lessonId: Long): Bookmark?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(bookmark: Bookmark)

    @Delete
    suspend fun deleteBookmark(bookmark: Bookmark)

    @Query("DELETE FROM bookmarks WHERE lessonId = :lessonId")
    suspend fun deleteBookmarkByLessonId(lessonId: Long)

    // Achievement operations
    @Query("SELECT * FROM achievements")
    fun getAllAchievements(): LiveData<List<Achievement>>

    @Query("SELECT * FROM achievements WHERE isUnlocked = 1")
    fun getUnlockedAchievements(): LiveData<List<Achievement>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAchievements(achievements: List<Achievement>)

    @Update
    suspend fun updateAchievement(achievement: Achievement)

    // Statistics operations
    @Query("SELECT * FROM user_statistics WHERE id = 1")
    fun getUserStatistics(): LiveData<UserStatistics>

    @Query("SELECT * FROM user_statistics WHERE id = 1")
    suspend fun getUserStatisticsSync(): UserStatistics?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserStatistics(statistics: UserStatistics)

    @Update
    suspend fun updateUserStatistics(statistics: UserStatistics)

    // Complex queries
    @Query("""
        SELECT l.* FROM lessons l
        INNER JOIN bookmarks b ON l.id = b.lessonId
        ORDER BY b.createdAt DESC
    """)
    fun getBookmarkedLessons(): LiveData<List<Lesson>>

    @Query("""
        SELECT COUNT(*) FROM lessons
        WHERE categoryId = :categoryId
    """)
    suspend fun getLessonCountByCategory(categoryId: Long): Int

    @Query("""
        SELECT COUNT(*) FROM quizzes
        WHERE categoryId = :categoryId
    """)
    suspend fun getQuizCountByCategory(categoryId: Long): Int
}
