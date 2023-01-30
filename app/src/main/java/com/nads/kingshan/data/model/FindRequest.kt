package com.nads.kingshan.data.model

import com.google.gson.*
import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.JsonAdapter
import java.lang.reflect.Type


data class FindRequest(
    @SerializedName("token")
    val token: String?,
    @JsonAdapter(VehicleListTypeAdapter::class)
    @SerializedName("vehicle_names")
    val vehicleNames: List<String>,
    @JsonAdapter(PlanetListTypeAdapter::class)
    @SerializedName("planet_names")
    val planetNames: List<String>
)
class VehicleListTypeAdapter: JsonSerializer<List<String>>, JsonDeserializer<List<String>> {


    fun toJson(vehicleNames: List<String>): JsonArray {
        val jsonArray = JsonArray()
        vehicleNames.forEach { jsonArray.add(it) }
        return jsonArray
    }


    fun fromJson(jsonArray: JsonArray): List<String> {
        val vehicleNames = mutableListOf<String>()
        for (i in 0 until jsonArray.size()) {
            vehicleNames.add(jsonArray.get(i).asString)
        }
        return vehicleNames
    }

    override fun serialize(
        src: List<String>?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        val jsonArray = JsonArray()
        src?.forEach { jsonArray.add(it) }
        return jsonArray
    }

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): List<String> {
        val vehicleNames = mutableListOf<String>()
        val jsonArray = json?.asJsonArray
        jsonArray?.forEach { vehicleNames.add(it.asString) }
        return vehicleNames

    }
}
class PlanetListTypeAdapter : JsonSerializer<List<String>>, JsonDeserializer<List<String>> {

    fun toJson(planetNames: List<String>): JsonArray {
        val jsonArray = JsonArray()
        planetNames.forEach { jsonArray.add(it) }
        return jsonArray
    }

    fun fromJson(jsonArray: JsonArray): List<String> {
        val planetNames = mutableListOf<String>()
        for (i in 0 until jsonArray.size()) {
            planetNames.add(jsonArray.get(i).asString)
        }
        return planetNames
    }

    override fun serialize(
        src: List<String>?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        val jsonArray = JsonArray()
        src?.forEach { jsonArray.add(it) }
        return jsonArray
    }

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): List<String> {
        val planetNames = mutableListOf<String>()
        val jsonArray = json?.asJsonArray
        jsonArray?.forEach { planetNames.add(it.asString) }
        return planetNames
    }
}