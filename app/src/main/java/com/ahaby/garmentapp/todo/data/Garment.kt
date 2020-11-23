package com.ahaby.garmentapp.todo.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "garments")
data class Garment (
    @PrimaryKey @ColumnInfo(name = "_id") val _id: String,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "material") var material: String,
    @ColumnInfo(name = "inaltime") var inaltime: String,
    @ColumnInfo(name = "latime") var latime: String,
    @ColumnInfo(name = "descriere") var descriere: String
//    val _id: String,
//    var name: String,
//    var ingredients: String,
//    var cookTime: String,
//    var noServings: String,
//    var method: String

) {
//    override fun toString(): String = name + "\n\n" + ingredients + "\n\n" + cookTime + "\n\n" + noServings + "\n\n" + method
    override fun toString(): String = name + ", " + material + " " + inaltime + " " + latime + " " + descriere
}