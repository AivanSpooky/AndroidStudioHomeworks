package com.lection.homework_2

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.io.IOException

class PunkApiException(message: String) : IOException(message)

suspend fun loadDataFromApi(): List<BeerData> {
    val rapidABV = 10
    val baseUrl = "https://api.punkapi.com/v2/"

    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val beerService = retrofit.create(BeerService::class.java)

    try {
        val response = beerService.getTrendingBeers(rapidABV)
        if (response.isSuccessful) {
            val responseBody = response.body()
            if (responseBody != null) {
                return responseBody ?: emptyList()
            } else {
                // Тело ответа равно null
                return emptyList()
            }
        } else {
            throw PunkApiException("Failed to load data from Beer API: ${response.code()}")
        }
    } catch (e: IOException) {
        throw PunkApiException("Network error: ${e.message}")
    }
}

interface BeerService {
    @GET("beers")
    suspend fun getTrendingBeers(
        @Query("abv_gt") abv: Int
    ): Response<List<BeerData>>
}

data class BeerData(
    val id: Int,
    val image_url: String
)