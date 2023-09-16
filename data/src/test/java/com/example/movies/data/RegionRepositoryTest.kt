package com.example.movies.data

import com.example.movies.data.datasource.LocationDataSource
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

@RunWith(MockitoJUnitRunner::class)
class RegionRepositoryTest {

    @Test
    fun `Returns default region when coarse permission not granted`(): Unit = runBlocking {
        val regionRepository = buildRegionRepository(
            permissionChecker = mock { on { check(PermissionChecker.Permission.COARSE_LOCATION) } doReturn false }
        )

        val region = regionRepository.findLastRegion()

        assertEquals(RegionRepository.DEFAULT_REGION, region)
    }

    @Test
    fun `Returns region from location data source when permission granted`(): Unit = runBlocking {
        val regionRepository = buildRegionRepository(
            locationDataSource = mock { onBlocking { findLastRegion() } doReturn "ES" },
            permissionChecker = mock { on { check(PermissionChecker.Permission.COARSE_LOCATION) } doReturn true }
        )

        val region = regionRepository.findLastRegion()

        assertEquals("ES", region)
    }
}

private fun buildRegionRepository(
    locationDataSource: LocationDataSource = mock(),
    permissionChecker: PermissionChecker = mock()
) = RegionRepository(locationDataSource, permissionChecker)