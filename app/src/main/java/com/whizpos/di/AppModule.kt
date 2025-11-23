package com.whizpos.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.whizpos.data.local.database.WhizPosDatabase
import com.whizpos.data.managers.SettingsManager
import com.whizpos.data.remote.WhizPosApiService
import com.whizpos.data.repository.*
import com.whizpos.domain.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideWhizPosDatabase(@ApplicationContext context: Context): WhizPosDatabase {
        return Room.databaseBuilder(
            context,
            WhizPosDatabase::class.java,
            "whizpos.db"
        ).fallbackToDestructiveMigration()
        .build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }

    @Provides
    @Singleton
    fun provideTransactionRepository(db: WhizPosDatabase, gson: Gson): TransactionRepository {
        return TransactionRepositoryImpl(db.transactionDao(), db.syncQueueDao(), gson)
    }

    @Provides
    @Singleton
    fun provideExpenseRepository(db: WhizPosDatabase, gson: Gson): ExpenseRepository {
        return ExpenseRepositoryImpl(db.expenseDao(), db.syncQueueDao(), gson)
    }

    @Provides
    @Singleton
    fun provideCreditCustomerRepository(db: WhizPosDatabase, gson: Gson): CreditCustomerRepository {
        return CreditCustomerRepositoryImpl(db.creditCustomerDao(), db.syncQueueDao(), gson)
    }

    // ... (rest of the file is unchanged)
}