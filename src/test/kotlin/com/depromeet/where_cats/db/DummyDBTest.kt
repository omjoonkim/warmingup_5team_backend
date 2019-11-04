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
class DummyDBTest {

    private val db = DummyDb()

    private val dummyHospitals = Dummies.makeDummyHospitals(500)
    private val dummyShelters = Dummies.makeDummyShelters(500)
    @Before
    fun before() {
        db.insertHospitals(dummyHospitals)
        db.insertShelters(dummyShelters)
    }

    @Test
    fun testGetHospitals() {
        Assert.assertEquals(
            dummyHospitals.size, db.getHospital().size
        )
    }

    @Test
    fun testGetShelters() {
        Assert.assertEquals(
            dummyShelters.size, db.getShelter().size
        )
    }

    @Test
    fun testInsertHospital() {
        val inserts = arrayOf(
            Hospital(
                name = "성모병원",
                location = Location(20.0, 20.0),
                openTime = "08:00",
                closeTime = "20:00"
            )
        )
        val results = db.insertHospitals(inserts)

        Assert.assertEquals(
            inserts.size, results.size
        )
        inserts.forEachIndexed { index, insert ->
            val result = results[index]

            Assert.assertEquals(
                insert, result
            )
        }
    }

    @Test
    fun testInsertShelter() {
        val inserts = arrayOf(
            Shelter(
                name = "우리동네 쉼터",
                location = Location(20.0, 20.0),
                openTime = "08:00",
                closeTime = "20:00"
            )
        )
        val results = db.insertShelters(inserts)

        Assert.assertEquals(
            inserts.size, results.size
        )
        inserts.forEachIndexed { index, insert ->
            val result = results[index]

            Assert.assertEquals(
                insert, result
            )
        }
    }
}