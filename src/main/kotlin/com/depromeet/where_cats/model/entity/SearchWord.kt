package com.depromeet.where_cats.model.entity

import java.time.ZonedDateTime

data class SearchWord(
    val text: String,
    val createdAt: ZonedDateTime
)