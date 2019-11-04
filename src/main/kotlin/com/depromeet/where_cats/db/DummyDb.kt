package com.depromeet.where_cats.db

import com.depromeet.where_cats.model.entity.Hospital
import com.depromeet.where_cats.model.entity.Shelter

interface DummyDbSpec {
    fun insertHospitals(item: Array<Hospital>): Array<Hospital>
    fun insertShelters(item: Array<Shelter>): Array<Shelter>
    fun getHospital(): Array<Hospital>
    fun getShelter(): Array<Shelter>
}

class DummyDb : DummyDbSpec {

    private val hospitals: HashSet<Hospital> = hashSetOf()
    private val shelters: HashSet<Shelter> = hashSetOf()

    private var lastHospitalId = 0
    private var lastShelterId = 0

    override fun insertHospitals(item: Array<Hospital>): Array<Hospital> = item.apply {
        hospitals.addAll(this)
        lastHospitalId += item.size
    }

    override fun insertShelters(item: Array<Shelter>): Array<Shelter> = item.apply {
        shelters.addAll(this)
        lastShelterId += item.size
    }

    override fun getHospital(): Array<Hospital> = hospitals.toTypedArray()

    override fun getShelter(): Array<Shelter> = shelters.toTypedArray()
}