package com.example.weatherapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ForecastDao {
    @Query("SELECT * FROM forecast WHERE city = :city")
    suspend fun getForecastsForCity(city: String): List<ForecastEntity>

    @Query("DELETE FROM forecast WHERE city = :city")
    suspend fun deleteForCity(city: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(forecasts: List<ForecastEntity>)

}
