package com.depromeet.where_cats.controller

import com.depromeet.where_cats.controller.response.*
import com.depromeet.where_cats.model.entity.CatModel
import com.depromeet.where_cats.model.entity.DistributionPointModel
import com.depromeet.where_cats.service.ServiceSpec
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import org.springframework.web.bind.annotation.*

@Api(tags = ["API"])
@RestController
class Controller(
    private val service: ServiceSpec
) {

    companion object {
        val RANGE_OF_NEAR_BY = 200.0
    }

    @ApiOperation(value = "좌표 근처 찾기", notes = "좌표 근처의 고양이, 배급소, 고양이, 보호소를 반합니다.")
    @GetMapping("/nearby", consumes = ["application/json"])
    fun getNearBy(
        @ApiParam("x 좌표", defaultValue = "20.0", required = true) @RequestParam("x") x: Double,
        @ApiParam("y 좌표", defaultValue = "20.0", required = true) @RequestParam("y") y: Double
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

    @ApiOperation(value = "최근 검색어", notes = "count만큼 최근 검색어를 반환합니다. count가 null일 경우 모두 리턴합니다.")
    @GetMapping("/search-word/recently", consumes = ["application/json"])
    fun getRecentlySearchWords(
        @ApiParam("검색어 갯수", defaultValue = "20", required = false) @RequestParam("count") count: Int?
    ): GetRecentlySearchWordsResponse = GetRecentlySearchWordsResponse(
        data = count?.let {
            service.getRecentlySearchWords(count)
        } ?: service.getAllSearchWords()
    )

    @ApiOperation(value = "연관 검색어", notes = "keyword가 포함되어있는 검색어를 반환합니다.")
    @GetMapping("/search-word/related", consumes = ["application/json"])
    fun getRelatedSearchWords(
        @ApiParam("검색어", defaultValue = "병원", required = true) @RequestParam("keyword") keyword: String
    ): GetRelatedSearchWordsResponse = GetRelatedSearchWordsResponse(
        data = service.getRelatedSearchWords(keyword)
    )

    @ApiOperation(value = "검색", notes = "keyword가 포함되어있는 고양이, 배급소, 병원, 보호소를 반환합니다.")
    @GetMapping("/search", consumes = ["application/json"])
    fun getSearch(
        @ApiParam("검색어", defaultValue = "병원", required = true) @RequestParam("keyword") keyword: String
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

    @ApiOperation(value = "고양이 추가", notes = "서버에 고양이를 추가합니다.")
    @GetMapping("/cat", consumes = ["application/json"])
    fun postCat(
        @RequestBody model: CatModel
    ): PostCatResponse = PostCatResponse(
        service.insertCat(model)
    )

    @ApiOperation(value = "고양이 수정", notes = "서버에 존재하던 고양이를 수정합니다.")
    @PatchMapping("/cat", consumes = ["application/json"])
    fun patchCat(
        @RequestBody model: CatModel
    ): PatchCatResponse = PatchCatResponse(
        service.updateCat(model)
    )

    @ApiOperation(value = "배급소 추가", notes = "서버에 배급소를 추가합니다.")
    @GetMapping("/distribution-point", consumes = ["application/json"])
    fun postDistributionPoint(
        @RequestBody model: DistributionPointModel
    ): PostDistributionPointResponse = PostDistributionPointResponse(
        service.insertDistributionPoint(model)
    )

    @ApiOperation(value = "배급소 수정", notes = "서버에 존재하던 배급소를 수정합니다.")
    @PatchMapping("/distribution-point/patch", consumes = ["application/json"])
    fun patchDistributionPoint(
        @RequestBody model: DistributionPointModel
    ): PatchDistributionPointResponse = PatchDistributionPointResponse(
        service.updateDistributionPoint(model)
    )
}