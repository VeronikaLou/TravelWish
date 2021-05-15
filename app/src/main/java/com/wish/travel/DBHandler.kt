package com.wish.travel

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.wish.travel.data.Country

val DATABASE_NAME = "DB"
val TABLE_NAME = "Countries"
val COL_NAME = "name"
val COL_CODE = "code"
val COL_REGION = "region"
var COL_WISHLIST_ORDER = "wishlist_order"

class DBHandler(var context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE $TABLE_NAME (" +
                "$COL_NAME VARCHAR(256), " +
                "$COL_CODE VARCHAR(256) PRIMARY KEY," +
                "$COL_REGION VARCHAR(256)," +
                "$COL_WISHLIST_ORDER INTEGER)"

        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun insertData(country: Country) {
        val db = this.writableDatabase
        var cv = ContentValues()
        cv.put(COL_NAME, country.name)
        cv.put(COL_CODE, country.code)
        cv.put(COL_REGION, country.region)
        cv.put(COL_WISHLIST_ORDER, country.wishlistOrder as Int)

        db.insert(TABLE_NAME, null, cv)
        db.close()
    }

    val allWishlistCountries: ArrayList<Country>
        get() {
            val wishlistCountries = ArrayList<Country>()
            val selectQuery = "SELECT * FROM $TABLE_NAME"
            val db = this.writableDatabase
            val cursor = db.rawQuery(selectQuery, null)
            if (cursor.moveToFirst()) {
                do {
                    val name = cursor.getString(cursor.getColumnIndex(COL_NAME))
                    val code = cursor.getString(cursor.getColumnIndex(COL_CODE))
                    val region = cursor.getString(cursor.getColumnIndex(COL_REGION))
                    val wishlistOrder = cursor.getInt(cursor.getColumnIndex(COL_WISHLIST_ORDER))
                    val country = Country(name, code, region, wishlistOrder)

                    wishlistCountries.add(country)
                } while (cursor.moveToNext())
            }
            db.close()
            return wishlistCountries
        }

    fun updateWishlistCountry(country: Country): Int {
        val db = this.writableDatabase
        var cv = ContentValues()
        cv.put(COL_NAME, country.name)
        cv.put(COL_CODE, country.code)
        cv.put(COL_REGION, country.region)
        cv.put(COL_WISHLIST_ORDER, country.wishlistOrder as Int)

        return db.update(TABLE_NAME, cv, "$COL_CODE=?", arrayOf(country.code))
    }

    fun deleteWishlistCountry(country: Country) {
        val db = this.writableDatabase

        db.delete(TABLE_NAME, "$COL_CODE=?", arrayOf(country.code))
        db.close()
    }
}