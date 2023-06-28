package com.example.movies.data.datasource

interface LocationDataSource {
    suspend fun findLastRegion(): String?
}