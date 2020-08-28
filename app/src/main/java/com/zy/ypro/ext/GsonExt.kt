package com.zy.ypro.ext

import com.google.gson.Gson

inline fun <reified T> Gson.fromJson(json: String): T = fromJson(json, T::class.java)