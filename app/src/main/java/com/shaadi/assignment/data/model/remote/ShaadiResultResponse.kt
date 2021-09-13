package com.shaadi.assignment.data.model.remote

import com.google.gson.annotations.Expose

data class ShaadiResultResponse(
    val results: ArrayList<Match>
){
    data class Match(
        val gender: String,
        val name: Name,
        val location: Location,
        val dob: Dob,
        val registered: Registered,
        val email: String,
        val phone: String,
        val cell: String,
        val id: ID,
        val picture: Picture,
        val nat: String,
        val login: Login,
        @Expose(serialize = false)
        var matchStatus: MatchStatus = MatchStatus.NONE
    ){
        data class Name(
            val title: String,
            val first: String,
            val last: String
        )
        data class Location(
            val city: String,
            val state: String,
            val country: String,
            val postCode: String,
            val street: Street,
            val coordinates: Coordinates,
            val timezone: TimeZone
        ){
            data class Street(
                val number: Int,
                val name: String
            )
            data class Coordinates(
                val latitude: String,
                val longitude: String
            )
            data class TimeZone(
                val offset: String,
                val description: String
            )
        }
        data class Dob(
            val date: String,
            val age: Int
        )
        data class Registered(
            val date: String,
            val age: Int
        )
        data class ID(
            val name: String,
            val value: String?
        )
        data class Picture(
            val large: String,
            val medium: String,
            val thumbnail: String
        )
        data class Login(
            val uuid: String,
            val username: String
        )
    }
}
