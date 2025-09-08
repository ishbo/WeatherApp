import android.content.Context
import com.example.weatherapp.data.local.ForecastDao
import com.example.weatherapp.data.local.toEntity
import com.example.weatherapp.model.DailyForecast
import com.example.weatherapp.network.WeatherApiService
import com.example.weatherapp.util.ForecastAggregator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

import com.example.weatherapp.data.local.toEntity
import com.example.weatherapp.data.local.toDailyForecast


class WeatherRepository(
    private val api: WeatherApiService,
    private val dao: ForecastDao,
    private val context: Context
) {
    suspend fun get3DayForecastForCity(city: String): Result<List<DailyForecast>> {
        return withContext(Dispatchers.IO) {
            try {
                val resp = api.getForecastForCity(city)
                val daily = ForecastAggregator.aggregate(resp)

                // Save to DB
                dao.deleteForCity(resp.city.name)
                dao.insertAll(daily.map { it.toEntity() })

                Result.success(daily)
            } catch (e: Exception) {
                // fallback: get cached forecasts
                val cached = dao.getForecastsForCity(city)
                if (cached.isNotEmpty()) {
                    Result.success(cached.map { it.toDailyForecast() })
                } else {
                    Result.failure(e)
                }
            }
        }
    }
}
