package com.depromeet.where_cats

import com.depromeet.where_cats.model.entity.*
import java.time.ZonedDateTime
import java.util.*

object Dummies {

    private val random = Random()

    fun makeDummySearchWord(number: Int) = SearchWord(
        text = "Hi I'm ${random}th",
        createdAt = ZonedDateTime.now()
    )

    fun makeDummySearchWords(size: Int) = (0 until size).map(Dummies::makeDummySearchWord).toTypedArray()

    fun makeDummyCat(id: Int) = Cat(
        id = id,
        name = "Cat $id",
        location = makeDummyLocation(),
        images = emptyArray(),
        feature = emptyArray(),
        createdAt = ZonedDateTime.now()
    )

    fun makeDummyCats(size: Int) = (0 until size).map(Dummies::makeDummyCat).toTypedArray()

    fun makeDummyCatModel(id: Int) = CatModel(
        id = null,
        name = "Cat $id",
        location = makeDummyLocation(),
        images = emptyArray(),
        feature = emptyArray()
    )

    fun makeDummyCatModels(size: Int) = (0 until size).map(Dummies::makeDummyCatModel).toTypedArray()

    fun makeDummyDistributionPoint(id: Int) = DistributionPoint(
        id = id,
        name = "DistributionPoint $id",
        location = makeDummyLocation(),
        images = emptyArray(),
        feature = emptyArray(),
        createdAt = ZonedDateTime.now()
    )

    fun makeDummyDistributionPoints(size: Int) = (0 until size).map(Dummies::makeDummyDistributionPoint).toTypedArray()

    fun makeDummyDistributionPointModel(id: Int) = DistributionPointModel(
        id = null,
        name = "DistributionPoint $id",
        location = makeDummyLocation(),
        images = emptyArray(),
        feature = emptyArray()
    )

    fun makeDummyDistributionPointModels(size: Int) = (0 until size).map(Dummies::makeDummyDistributionPointModel).toTypedArray()

    fun makeDummyHospital(id: Int) = Hospital(
        name = "Hospital $id",
        location = makeDummyLocation(),
        openTime = "${String.format("%02d", random.nextInt(12))}:${random.nextInt(60)}",
        closeTime = "${String.format("%02d", random.nextInt() + 12)}:${random.nextInt(60)}"
    )

    fun makeDummyHospitals(size: Int) = (0 until size).map(Dummies::makeDummyHospital).toTypedArray()

    fun makeDummyShelter(id: Int) = Shelter(
        name = "Shelter $id",
        location = makeDummyLocation(),
        openTime = "${String.format("%02d", random.nextInt(12))}:${random.nextInt(60)}",
        closeTime = "${String.format("%02d", random.nextInt() + 12)}:${random.nextInt(60)}"
    )

    fun makeDummyShelters(size: Int) = (0 until size).map(Dummies::makeDummyShelter).toTypedArray()

    fun makeDummyLocation(
        x: Double = random.nextDouble(),
        y: Double = random.nextDouble()
    ) = Location(x, y)
}