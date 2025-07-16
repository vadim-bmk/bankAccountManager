package com.dvo.bankAccountManager.web.model.response

data class PinsResponse(
    val id: Long,
    val pinEq: String,
    val inn: String?,
    val firstName: String?,
    val lastName: String?,
    val patronymic: String?
)
