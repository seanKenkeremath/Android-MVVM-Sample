package com.seank.kotlinflowplayground.domain

class GenericException(override val message: String? = "Generic Error"): Exception()

class NetworkException(): Exception("Network Error")