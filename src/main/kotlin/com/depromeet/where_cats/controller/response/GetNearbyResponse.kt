package com.depromeet.where_cats.controller.response

import com.depromeet.where_cats.model.entity.Cat
import com.depromeet.where_cats.model.entity.DistributionPoint
import com.depromeet.where_cats.model.entity.Hospital
import com.depromeet.where_cats.model.entity.Shelter

data class GetNearbyResponse(
    val data: Data
) {

    data class Data(
        val cats: Array<Cat>,
        val distributionPoints: Array<DistributionPoint>,
        val shelters: Array<Shelter>,
        val hospital: Array<Hospital>
    ) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Data

            if (!cats.contentEquals(other.cats)) return false
            if (!distributionPoints.contentEquals(other.distributionPoints)) return false
            if (!shelters.contentEquals(other.shelters)) return false
            if (!hospital.contentEquals(other.hospital)) return false

            return true
        }

        override fun hashCode(): Int {
            var result = cats.contentHashCode()
            result = 31 * result + distributionPoints.contentHashCode()
            result = 31 * result + shelters.contentHashCode()
            result = 31 * result + hospital.contentHashCode()
            return result
        }
    }
}