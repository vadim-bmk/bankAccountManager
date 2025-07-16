package com.dvo.bankAccountManager.web.model.response

data class ErrorResponse(
    val message: String,
    val details: Map<String, String>? = null
)
