package com.ahaby.garmentapp.core

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

class Utils {
    var context: Context? = null
    var sharedPref: SharedPreferences? = null
    var sharedPrefEditor: SharedPreferences.Editor? = null

    private val PREF_NAME = "com.ahaby.garmentapp"
    val KEY_ID = "id"
    val KEY_ACCESS_TOKEN = "access_token"

    fun configSessionUtils(context: Context) {
        this.context = context
        sharedPref = context.getSharedPreferences("loginDetails", Activity.MODE_PRIVATE)
        sharedPrefEditor = sharedPref?.edit()
    }

    fun storeValueString(key: String?, value: String?) {
        sharedPrefEditor?.putString(key, value)
//        sharedPrefEditor!!.commit()
        sharedPrefEditor!!.apply()
    }

    fun fetchValueString(key: String?): String? {
        return sharedPref!!.getString(key, null)
    }

    fun deleteValueString(key: String?) {
        sharedPref!!.edit().remove(key).apply()
    }

    companion object {
        var _instance: Utils? = null
        fun instance(context: Context): Utils? {
            if (_instance == null) {
                _instance = Utils()
                _instance!!.configSessionUtils(context)
            }
            return _instance
        }

        fun instance(): Utils? {
            return _instance
        }
    }
}