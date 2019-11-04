package com.depromeet.where_cats.db

import com.depromeet.where_cats.model.entity.*
import java.time.ZonedDateTime

interface InMemoryDbSpec {

    fun getCats(): Array<Cat>
    fun getDistributionPoints(): Array<DistributionPoint>
    fun getSearchWords(): Array<SearchWord>
    fun insertCats(item: Array<CatModel>): Array<Cat>
    fun updateCats(item: Array<CatModel>): Array<Cat>
    fun insertDistributionPoints(item: Array<DistributionPointModel>): Array<DistributionPoint>
    fun updateDistributionPoints(item: Array<DistributionPointModel>): Array<DistributionPoint>
    fun insertSearchWords(item: Array<SearchWord>): Array<SearchWord>
}

class InMemoryDb : InMemoryDbSpec {

    private val cats: HashSet<Cat> = hashSetOf()
    private val distributionPoints: HashSet<DistributionPoint> = hashSetOf()
    private val searchWords: HashSet<SearchWord> = hashSetOf()

    private var lastCatId = 0
    private var lastDistributionPointId = 0


    override fun getCats(): Array<Cat> = cats.toTypedArray()
    override fun getDistributionPoints(): Array<DistributionPoint> = distributionPoints.toTypedArray()
    override fun getSearchWords(): Array<SearchWord> = searchWords.toTypedArray()

    override fun insertCats(item: Array<CatModel>): Array<Cat> = item
        .map { model ->
            if (model.id != null) throw IllegalArgumentException("id is must be null")
            if (model.name == null) throw IllegalArgumentException("name is must be not null")
            if (model.location == null) throw IllegalArgumentException("location is must be not null")
            if (model.images == null) throw IllegalArgumentException("images is must be not null")
            if (model.feature == null) throw IllegalArgumentException("feature is must be not null")


            Cat(
                id = lastCatId++,
                name = model.name!!,
                location = model.location!!,
                images = model.images!!,
                feature = model.feature!!,
                createdAt = ZonedDateTime.now()
            )
        }.toTypedArray().apply {
            cats.addAll(this)
        }

    override fun updateCats(item: Array<CatModel>): Array<Cat> = item.map { model ->
        model.id ?: throw IllegalArgumentException("id is must be not null")

        val original = cats.find {
            it.id == model.id
        } ?: throw IllegalArgumentException("can not find Cat. id is ${model.id}")

        original.copy(
            name = model.name ?: original.name,
            location = model.location ?: original.location,
            images = model.images ?: original.images,
            feature = model.feature ?: original.feature
        )
    }.toTypedArray().apply {
        cats.addAll(this)
    }

    override fun insertDistributionPoints(item: Array<DistributionPointModel>): Array<DistributionPoint> =
        item.map { model ->
            if (model.id != null) throw IllegalArgumentException("id is must be null")
            if (model.name == null) throw IllegalArgumentException("name is must be not null")
            if (model.location == null) throw IllegalArgumentException("location is must be not null")
            if (model.images == null) throw IllegalArgumentException("images is must be not null")
            if (model.feature == null) throw IllegalArgumentException("feature is must be not null")


            DistributionPoint(
                id = lastDistributionPointId++,
                name = model.name!!,
                location = model.location!!,
                images = model.images!!,
                feature = model.feature!!,
                createdAt = ZonedDateTime.now()
            )
        }.toTypedArray().apply {
            distributionPoints.addAll(this)
        }


    override fun updateDistributionPoints(item: Array<DistributionPointModel>): Array<DistributionPoint> = item.map { model ->
        model.id ?: throw IllegalArgumentException("id is must be not null")

        val original = distributionPoints.find {
            it.id == model.id
        } ?: throw IllegalArgumentException("can not find Cat. id is ${model.id}")

        original.copy(
            name = model.name ?: original.name,
            location = model.location ?: original.location,
            images = model.images ?: original.images,
            feature = model.feature ?: original.feature
        )
    }.toTypedArray().apply {
        distributionPoints.addAll(this)
    }

    override fun insertSearchWords(item: Array<SearchWord>): Array<SearchWord> = searchWords.run {
        addAll(item)
        toTypedArray()
    }
}