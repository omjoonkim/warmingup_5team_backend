package com.depromeet.where_cats.service

import com.depromeet.where_cats.Dummies
import com.depromeet.where_cats.db.DummyDbSpec
import com.depromeet.where_cats.db.InMemoryDbSpec
import com.depromeet.where_cats.model.entity.*
import com.depromeet.where_cats.util.Quadruple
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner
import java.time.ZonedDateTime

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class ServiceTest {

    @MockBean
    private lateinit var inMemoryDB: InMemoryDbSpec
    @MockBean
    private lateinit var dummyDB: DummyDbSpec
    private lateinit var service: ServiceSpec

    @Before
    fun before() {
        service = Service(
            inMemoryDB,
            dummyDB
        )
    }

    @Test
    fun testGetNearBy() {
        val testRange = 200.0

        fun assert(
            location: Location,
            result: Quadruple<Array<Cat>, Array<DistributionPoint>, Array<Shelter>, Array<Hospital>>
        ) = result.let { (cats, distributionPoints, shelters, hospitals) ->
            Assert.assertTrue(
                cats.none {
                    Math.abs(it.location.x - location.x) > testRange
                        && Math.abs(it.location.y - location.y) > testRange
                } && distributionPoints.none {
                    Math.abs(it.location.x - location.x) > testRange
                        && Math.abs(it.location.y - location.y) > testRange
                } && hospitals.none {
                    Math.abs(it.location.x - location.x) > testRange
                        && Math.abs(it.location.y - location.y) > testRange
                } && shelters.none {
                    Math.abs(it.location.x - location.x) > testRange
                        && Math.abs(it.location.y - location.y) > testRange
                }
            )
        }

        `when`(inMemoryDB.getCats()).thenReturn(
            Dummies.makeDummyCats(200)
        )
        `when`(inMemoryDB.getDistributionPoints()).thenReturn(
            Dummies.makeDummyDistributionPoints(200)
        )
        `when`(dummyDB.getHospital()).thenReturn(
            Dummies.makeDummyHospitals(200)
        )
        `when`(dummyDB.getShelter()).thenReturn(
            Dummies.makeDummyShelters(200)
        )


        val testLocationBasic = Location(200.0, 400.0)
        assert(
            testLocationBasic,
            service.getNearBy(
                testLocationBasic.x,
                testLocationBasic.y,
                testRange
            )
        )

        val testLocationMax = Location(Double.MIN_VALUE, Double.MIN_VALUE)
        assert(
            testLocationMax,
            service.getNearBy(
                testLocationMax.x,
                testLocationMax.y,
                testRange
            )
        )


    }

    @Test
    fun testGetRecentlySearchWords() {
        val count = 20
        val dummy = Dummies.makeDummySearchWords(20)

        `when`(inMemoryDB.getSearchWords()).thenReturn(dummy)

        val result = service.getRecentlySearchWords(count)

        Assert.assertArrayEquals(
            dummy, result
        )
    }

    @Test
    fun testGetRelatedSearchWordTest() {
        val keyword = "안녕"
        val dummy = Dummies.makeDummySearchWords(20)

        `when`(inMemoryDB.getSearchWords()).thenReturn(dummy)

        val result = service.getRelatedSearchWords(keyword)

        Assert.assertEquals(
            dummy.filter {
                it.text.contains(keyword)
            }.size,
            result.filter {
                it.text.contains(keyword)
            }.size
        )
        Assert.assertTrue(
            result.filterNot {
                it.text.contains(keyword)
            }.isEmpty()
        )
    }

    @Test
    fun testGetSearch() {
        val keyword = "안녕"

        val dummyCats = Dummies.makeDummyCats(200)
        `when`(inMemoryDB.getCats()).thenReturn(
            dummyCats
        )

        val dummyDistributionPoints = Dummies.makeDummyDistributionPoints(200)
        `when`(inMemoryDB.getDistributionPoints()).thenReturn(
            dummyDistributionPoints
        )

        val dummyHospitals = Dummies.makeDummyHospitals(200)
        `when`(dummyDB.getHospital()).thenReturn(
            dummyHospitals
        )

        val dummyShelters = Dummies.makeDummyShelters(200)
        `when`(dummyDB.getShelter()).thenReturn(
            dummyShelters
        )

        val result = service.getSearch(keyword)

        result.let { (cats, distributionPoints, shelters, hospitals) ->
            Assert.assertEquals(
                dummyCats.filter {
                    it.name.contains(keyword)
                }.size,
                cats.filter {
                    it.name.contains(keyword)
                }.size
            )
            Assert.assertTrue(
                cats.filterNot {
                    it.name.contains(keyword)
                }.isEmpty()
            )

            Assert.assertEquals(
                dummyDistributionPoints.filter {
                    it.name.contains(keyword)
                }.size,
                distributionPoints.filter {
                    it.name.contains(keyword)
                }.size
            )
            Assert.assertTrue(
                distributionPoints.filterNot {
                    it.name.contains(keyword)
                }.isEmpty()
            )

            Assert.assertEquals(
                dummyHospitals.filter {
                    it.name.contains(keyword)
                }.size,
                hospitals.filter {
                    it.name.contains(keyword)
                }.size
            )
            Assert.assertTrue(
                hospitals.filterNot {
                    it.name.contains(keyword)
                }.isEmpty()
            )

            Assert.assertEquals(
                dummyShelters.filter {
                    it.name.contains(keyword)
                }.size,
                shelters.filter {
                    it.name.contains(keyword)
                }.size
            )
            Assert.assertTrue(
                shelters.filterNot {
                    it.name.contains(keyword)
                }.isEmpty()
            )
        }
    }

    @Test
    fun testUpdateCat() {
        val update = CatModel(
            id = 200,
            name = "나비",
            location = Location(20.0, 20.0),
            images = emptyArray(),
            feature = emptyArray()
        )
        val dummy = arrayOf(
            Cat(
                id = update.id!!,
                name = update.name!!,
                location = update.location!!,
                images = update.images!!,
                feature = update.feature!!,
                createdAt = ZonedDateTime.now()
            )
        )
        `when`(inMemoryDB.updateCats(arrayOf(update))).thenReturn(
            dummy
        )
        val result = service.updateCat(update)

        Assert.assertEquals(
            update.name,
            result.name
        )
        Assert.assertEquals(
            update.location,
            result.location
        )
        Assert.assertArrayEquals(
            update.images,
            result.images
        )
        Assert.assertArrayEquals(
            update.feature,
            result.feature
        )
        Assert.assertEquals(
            dummy.find { it.id == update.id },
            result
        )
    }

    @Test
    fun testInsertCat() {
        val insert = CatModel(
            id = null,
            name = "나비",
            location = Location(20.0, 20.0),
            images = emptyArray(),
            feature = emptyArray()
        )
        val dummy = arrayOf(
            Cat(
                id = 1,
                name = insert.name!!,
                location = insert.location!!,
                images = insert.images!!,
                feature = insert.feature!!,
                createdAt = ZonedDateTime.now()
            )
        )
        `when`(inMemoryDB.insertCats(arrayOf(insert))).thenReturn(
            dummy
        )

        val result = service.insertCat(insert)

        Assert.assertEquals(
            insert.name,
            result.name
        )
        Assert.assertEquals(
            insert.location,
            result.location
        )
        Assert.assertArrayEquals(
            insert.images,
            result.images
        )
        Assert.assertArrayEquals(
            insert.feature,
            result.feature
        )
    }

    @Test
    fun testUpdateDistributionPoint() {
        val update = DistributionPointModel(
            id = 200,
            name = "공덕역 5번 출구",
            location = Location(20.0, 20.0),
            images = emptyArray(),
            feature = emptyArray()
        )
        val dummy = arrayOf(
            DistributionPoint(
                id = update.id!!,
                name = update.name!!,
                location = update.location!!,
                images = update.images!!,
                feature = update.feature!!,
                createdAt = ZonedDateTime.now()
            )
        )
        `when`(inMemoryDB.updateDistributionPoints(arrayOf(update))).thenReturn(
            dummy
        )
        val result = service.updateDistributionPoint(update)

        Assert.assertEquals(
            update.name,
            result.name
        )
        Assert.assertEquals(
            update.location,
            result.location
        )
        Assert.assertArrayEquals(
            update.images,
            result.images
        )
        Assert.assertArrayEquals(
            update.feature,
            result.feature
        )
        Assert.assertEquals(
            dummy.find { it.id == update.id },
            result
        )
    }

    @Test
    fun testInsertDistributionPoint() {
        val insert = DistributionPointModel(
            id = null,
            name = "공덕역 5번 출구",
            location = Location(20.0, 20.0),
            images = emptyArray(),
            feature = emptyArray()
        )
        val dummy = arrayOf(
            DistributionPoint(
                id = 1,
                name = insert.name!!,
                location = insert.location!!,
                images = insert.images!!,
                feature = insert.feature!!,
                createdAt = ZonedDateTime.now()
            )
        )
        `when`(inMemoryDB.insertDistributionPoints(arrayOf(insert))).thenReturn(
            dummy
        )

        val result = service.insertDistributionPoint(insert)

        Assert.assertEquals(
            insert.name,
            result.name
        )
        Assert.assertEquals(
            insert.location,
            result.location
        )
        Assert.assertArrayEquals(
            insert.images,
            result.images
        )
        Assert.assertArrayEquals(
            insert.feature,
            result.feature
        )
    }
}