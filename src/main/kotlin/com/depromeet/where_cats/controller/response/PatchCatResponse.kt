package com.depromeet.where_cats.controller.response

import com.depromeet.where_cats.model.entity.Cat

data class PatchCatResponse(
    val data: Cat
)