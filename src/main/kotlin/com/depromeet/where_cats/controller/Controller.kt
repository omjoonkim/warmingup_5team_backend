package com.depromeet.where_cats.controller

import com.depromeet.where_cats.controller.response.*
import com.depromeet.where_cats.model.entity.CatModel
import com.depromeet.where_cats.model.entity.DistributionPointModel
import com.depromeet.where_cats.service.ServiceSpec
import org.springframework.web.bind.annotation.*

@RestController
class Controller(
    private val service: ServiceSpec
) {

    companion object {
        val RANGE_OF_NEAR_BY = 200.0
    }

    @GetMapping("/nearby", consumes = ["application/json"])
    fun getNearBy(
        @RequestParam("x") x: Double,
        @RequestParam("y") y: Double
    ): GetNearbyResponse = service.getNearBy(x, y, RANGE_OF_NEAR_BY).let { (cats, distributionPoints, shelters, hospitals) ->
        GetNearbyResponse(
            GetNearbyResponse.Data(
                cats = cats,
                distributionPoints = distributionPoints,
                shelters = shelters,
                hospital = hospitals
            )
        )
    }

    @GetMapping("/search-word/recently", consumes = ["application/json"])
    fun getRecentlySearchWords(
        @RequestParam("count") count: Int?
    ): GetRecentlySearchWordsResponse = GetRecentlySearchWordsResponse(
        data = count?.let {
            service.getRecentlySearchWords(count)
        } ?: service.getAllSearchWords()
    )

    @GetMapping("/search-word/related", consumes = ["application/json"])
    fun getRelatedSearchWords(
        @RequestParam("keyword") keyword: String
    ): GetRelatedSearchWordsResponse = GetRelatedSearchWordsResponse(
        data = service.getRelatedSearchWords(keyword)
    )

    @GetMapping("/search", consumes = ["application/json"])
    fun getSearch(
        @RequestParam("keyword") keyword: String
    ): GetSearchResponse = service.getSearch(keyword).let { (cats, distributionPoints, shelters, hospitals) ->
        GetSearchResponse(
            data = GetSearchResponse.Data(
                cats = cats,
                distributionPoints = distributionPoints,
                shelters = shelters,
                hospital = hospitals
            )
        )
    }

    @GetMapping("/cat", consumes = ["application/json"])
    fun postCat(
        @RequestBody model: CatModel
    ): PostCatResponse = PostCatResponse(
        service.insertCat(model)
    )

    @PatchMapping("/cat", consumes = ["application/json"])
    fun patchCat(
        @RequestBody model: CatModel
    ): PatchCatResponse = PatchCatResponse(
        service.updateCat(model)
    )

    @GetMapping("/distribution-point", consumes = ["application/json"])
    fun postDistributionPoint(
        @RequestBody model: DistributionPointModel
    ): PostDistributionPointResponse = PostDistributionPointResponse(
        service.insertDistributionPoint(model)
    )

    @PatchMapping("/distribution-point/patch", consumes = ["application/json"])
    fun patchDistributionPoint(
        @RequestBody model: DistributionPointModel
    ): PatchDistributionPointResponse = PatchDistributionPointResponse(
        service.updateDistributionPoint(model)
    )
}