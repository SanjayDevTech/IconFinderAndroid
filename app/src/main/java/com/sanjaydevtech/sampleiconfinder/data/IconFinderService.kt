package com.sanjaydevtech.sampleiconfinder.data

import com.sanjaydevtech.sampleiconfinder.data.models.CategoryResponse
import com.sanjaydevtech.sampleiconfinder.data.models.Icon
import com.sanjaydevtech.sampleiconfinder.data.models.IconResponse
import com.sanjaydevtech.sampleiconfinder.data.models.IconsetResponse
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IconFinderService {

    @GET("categories")
    suspend fun listAllCategories(
        @Query("count") count: Int = 50,
        @Query("after") after: String = "",
    ): CategoryResponse

    @GET("categories/{id}/iconsets")
    suspend fun listIconSetsInACategory(
        @Path("id") identifier: String,
        @Query("vector") vector: String = "false",
        @Query("count") count: Int = 50,
        @Query("after") after: String = "",
    ): IconsetResponse

    @GET("iconsets/{id}/icons")
    suspend fun listAllIconsInAIconSet(
        @Path("id") iconsetId: Int,
        @Query("query") query: String = "",
        @Query("vector") vector: String = "false",
        @Query("count") count: Int = 20,
    ): IconResponse

    @GET("icons/{icon_id}")
    suspend fun getIconDetails(
        @Path("icon_id") iconId: Int,
    ): Icon?

    @GET("icons/{icon_id}/formats/{format}/{size}/download")
    suspend fun downloadIcon(
        @Path("icon_id") iconId: Int,
        @Path("format") format: String,
        @Path("size") size: Int,
    ): ResponseBody

}