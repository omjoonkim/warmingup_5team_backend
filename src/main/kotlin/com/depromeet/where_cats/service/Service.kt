package com.depromeet.where_cats.service

import com.depromeet.where_cats.controller.Controller
import com.depromeet.where_cats.db.DummyDbSpec
import com.depromeet.where_cats.db.InMemoryDbSpec
import com.depromeet.where_cats.model.entity.*
import com.depromeet.where_cats.util.Quadruple
import org.springframework.stereotype.Service

interface ServiceSpec {
    fun getNearBy(
        x: Double,
        y: Double,
        range: Double
    ): Quadruple<
        Array<Cat>,
        Array<DistributionPoint>,
        Array<Shelter>,
        Array<Hospital>
        >

    fun getAllSearchWords(): Array<SearchWord>

    fun getRecentlySearchWords(
        count: Int
    ): Array<SearchWord>

    fun getRelatedSearchWords(
        keyword: String
    ): Array<SearchWord>

    fun getSearch(
        keyword: String
    ): Quadruple<
        Array<Cat>,
        Array<DistributionPoint>,
        Array<Shelter>,
        Array<Hospital>
        >

    fun insertCat(
        model: CatModel
    ): Cat

    fun updateCat(
        model: CatModel
    ): Cat

    fun insertDistributionPoint(
        model: DistributionPointModel
    ): DistributionPoint

    fun updateDistributionPoint(
        model: DistributionPointModel
    ): DistributionPoint
}

@Service
class Service(
    private val inMemoryDB: InMemoryDbSpec,
    private val dummyDB: DummyDbSpec
) : ServiceSpec {

    override fun getNearBy(
        x: Double,
        y: Double,
        range: Double
    ): Quadruple<Array<Cat>, Array<DistributionPoint>, Array<Shelter>, Array<Hospital>> = Quadruple(
        inMemoryDB.getCats().filter {
            it.location.isNearBy(
                Controller.RANGE_OF_NEAR_BY, Location(x, y)
            )
        }.toTypedArray(),
        inMemoryDB.getDistributionPoints().filter {
            it.location.isNearBy(
                Controller.RANGE_OF_NEAR_BY, Location(x, y)
            )
        }.toTypedArray(),
        dummyDB.getShelter().filter {
            it.location.isNearBy(
                Controller.RANGE_OF_NEAR_BY, Location(x, y)
            )
        }.toTypedArray(),
        dummyDB.getHospital().filter {
            it.location.isNearBy(
                Controller.RANGE_OF_NEAR_BY, Location(x, y)
            )
        }.toTypedArray()
    )

    override fun getAllSearchWords(): Array<SearchWord> = inMemoryDB.getSearchWords()

    override fun getRecentlySearchWords(
        count: Int
    ): Array<SearchWord> = inMemoryDB.getSearchWords()
        .takeLast(count)
        .toTypedArray()

    override fun getRelatedSearchWords(
        keyword: String
    ): Array<SearchWord> = inMemoryDB.getSearchWords()
        .filter { searchWord ->
            keyword.split(" ")
                .find {
                    searchWord.text.contains(it)
                } != null
        }.toTypedArray()

    override fun getSearch(
        keyword: String
    ): Quadruple<Array<Cat>, Array<DistributionPoint>, Array<Shelter>, Array<Hospital>> = Quadruple(
        inMemoryDB.getCats()
            .filter {
                it.name.contains(
                    keyword
                )
            }.toTypedArray(),
        inMemoryDB.getDistributionPoints()
            .filter {
                it.name.contains(
                    keyword
                )
            }.toTypedArray(),
        dummyDB.getShelter()
            .filter {
                it.name.contains(
                    keyword
                )
            }.toTypedArray(),
        dummyDB.getHospital()
            .filter {
                it.name.contains(
                    keyword
                )
            }.toTypedArray()
    )

    override fun insertCat(
        model: CatModel
    ): Cat = inMemoryDB.insertCats(
        arrayOf(model)
    ).first()

    override fun updateCat(
        model: CatModel
    ): Cat = inMemoryDB.updateCats(
        arrayOf(model)
    ).first()


    override fun insertDistributionPoint(
        model: DistributionPointModel
    ): DistributionPoint = inMemoryDB.insertDistributionPoints(
        arrayOf(model)
    ).first()

    override fun updateDistributionPoint(
        model: DistributionPointModel
    ): DistributionPoint = inMemoryDB.updateDistributionPoints(
        arrayOf(model)
    ).first()
}