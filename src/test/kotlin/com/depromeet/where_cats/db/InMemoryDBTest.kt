package com.depromeet.where_cats.db

import com.depromeet.where_cats.Dummies
import com.depromeet.where_cats.model.entity.*
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class InMemoryDBTest {

    private val db = InMemoryDb()

    private val dummyCatModels = Dummies.makeDummyCatModels(500)
    private val dummyDistributionPointModels =
        Dummies.makeDummyDistributionPointModels(500)
    private val dummySearchWord = Dummies.makeDummySearchWords(200)
    @Before
    fun before() {
        db.insertCats(dummyCatModels)
        db.insertDistributionPoints(dummyDistributionPointModels)
        db.insertSearchWords(dummySearchWord)
    }

    @Test
    fun testGetCats() {
        Assert.assertEquals(
            dummyCatModels.size, db.getCats().size
        )
    }

    @Test
    fun testGetDistributionPoints() {
        Assert.assertEquals(
            dummyDistributionPointModels.size, db.getDistributionPoints().size
        )
    }

    @Test
    fun testGetSearchWords() {
        Assert.assertEquals(
            dummySearchWord.size, db.getSearchWords().size
        )
    }

    @Test
    fun testInsertCatBasic() {
        val inserts = arrayOf(
            CatModel(
                id = null,
                name = "나비",
                location = Location(20.0, 20.0),
                images = emptyArray(),
                feature = emptyArray()
            )
        )
        val results = db.insertCats(inserts)

        Assert.assertEquals(
            inserts.size, results.size
        )
        inserts.forEachIndexed { index, insert ->
            val result = results[index]

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

    @Test(expected = IllegalArgumentException::class)
    fun testInsetCatIdIsNotNull() {
        val inserts = arrayOf(
            CatModel(
                id = 2,
                name = "나비",
                location = Location(20.0, 20.0),
                images = emptyArray(),
                feature = emptyArray()
            )
        )
        db.insertCats(inserts)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testInsertCatNameIsNull() {
        val inserts = arrayOf(
            CatModel(
                id = null,
                name = null,
                location = Location(20.0, 20.0),
                images = emptyArray(),
                feature = emptyArray()
            )
        )
        db.insertCats(inserts)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testInsertCatLocationIsNotNull() {
        val inserts = arrayOf(
            CatModel(
                id = null,
                name = "나비",
                location = null,
                images = emptyArray(),
                feature = emptyArray()
            )
        )
        db.insertCats(inserts)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testInsertCatImagesIsNotNull() {
        val inserts = arrayOf(
            CatModel(
                id = 2,
                name = "나비",
                location = Location(20.0, 20.0),
                images = null,
                feature = emptyArray()
            )
        )
        db.insertCats(inserts)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testInsertCatFeatureIsNotNull() {
        val inserts = arrayOf(
            CatModel(
                id = 2,
                name = "나비",
                location = Location(20.0, 20.0),
                images = emptyArray(),
                feature = null
            )
        )
        db.insertCats(inserts)
    }

    @Test
    fun testUpdateCatBasic() {
        val updates = arrayOf(
            CatModel(
                id = 200,
                name = "나비",
                location = Location(20.0, 20.0),
                images = emptyArray(),
                feature = emptyArray()
            )
        )
        val results = db.updateCats(
            updates
        )

        Assert.assertEquals(
            updates.size, results.size
        )
        updates.forEachIndexed { index, update ->
            val result = results[index]

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
        }
    }

    @Test(expected = IllegalArgumentException::class)
    fun testUpdateCatIdIsNull() {
        val updates = arrayOf(
            CatModel(
                id = null,
                name = "나비",
                location = Location(20.0, 20.0),
                images = emptyArray(),
                feature = emptyArray()
            )
        )
        db.updateCats(updates)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testUpdateCatNotFound() {
        val updates = arrayOf(
            CatModel(
                id = 600,
                name = "나비",
                location = Location(20.0, 20.0),
                images = emptyArray(),
                feature = emptyArray()
            )
        )
        db.updateCats(updates)
    }

    @Test
    fun testInsertDistributionPointBasic() {
        val inserts = arrayOf(
            DistributionPointModel(
                id = null,
                name = "공덕역 5번 출구",
                location = Location(20.0, 20.0),
                images = emptyArray(),
                feature = emptyArray()
            )
        )
        val results = db.insertDistributionPoints(inserts)

        Assert.assertEquals(
            inserts.size, results.size
        )
        inserts.forEachIndexed { index, insert ->
            val result = results[index]

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

    @Test(expected = IllegalArgumentException::class)
    fun testInsetDistributionPointIdIsNotNull() {
        val inserts = arrayOf(
            DistributionPointModel(
                id = 2,
                name = "나비",
                location = Location(20.0, 20.0),
                images = emptyArray(),
                feature = emptyArray()
            )
        )
        db.insertDistributionPoints(inserts)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testInsertDistributionPointNameIsNull() {
        val inserts = arrayOf(
            DistributionPointModel(
                id = null,
                name = null,
                location = Location(20.0, 20.0),
                images = emptyArray(),
                feature = emptyArray()
            )
        )
        db.insertDistributionPoints(inserts)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testInsertDistributionPointLocationIsNotNull() {
        val inserts = arrayOf(
            DistributionPointModel(
                id = null,
                name = "나비",
                location = null,
                images = emptyArray(),
                feature = emptyArray()
            )
        )
        db.insertDistributionPoints(inserts)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testInsertDistributionPointImagesIsNotNull() {
        val inserts = arrayOf(
            DistributionPointModel(
                id = 2,
                name = "나비",
                location = Location(20.0, 20.0),
                images = null,
                feature = emptyArray()
            )
        )
        db.insertDistributionPoints(inserts)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testInsertDistributionPointFeatureIsNotNull() {
        val inserts = arrayOf(
            DistributionPointModel(
                id = 2,
                name = "나비",
                location = Location(20.0, 20.0),
                images = emptyArray(),
                feature = null
            )
        )
        db.insertDistributionPoints(inserts)
    }

    @Test
    fun testUpdateDistributionPointBasic() {
        val updates = arrayOf(
            DistributionPointModel(
                id = 200,
                name = "나비",
                location = Location(20.0, 20.0),
                images = emptyArray(),
                feature = emptyArray()
            )
        )
        val results = db.updateDistributionPoints(
            updates
        )

        Assert.assertEquals(
            updates.size, results.size
        )
        updates.forEachIndexed { index, update ->
            val result = results[index]

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
        }
    }

    @Test(expected = IllegalArgumentException::class)
    fun testUpdateDistributionPointIdIsNull() {
        val updates = arrayOf(
            DistributionPointModel(
                id = null,
                name = "나비",
                location = Location(20.0, 20.0),
                images = emptyArray(),
                feature = emptyArray()
            )
        )
        db.updateDistributionPoints(updates)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testUpdateDistributionPointNotFound() {
        val updates = arrayOf(
            DistributionPointModel(
                id = 600,
                name = "나비",
                location = Location(20.0, 20.0),
                images = emptyArray(),
                feature = emptyArray()
            )
        )
        db.updateDistributionPoints(updates)
    }
}