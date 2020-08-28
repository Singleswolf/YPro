package com.zy.commonlib.http

class ApiException(var code: Int, override var message: String) : RuntimeException()