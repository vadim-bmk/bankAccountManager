package com.dvo.bankAccountManager.web.model.response

import java.time.LocalDate

data class Rdk2pfResponse(
    val id: Long,
    val pinEq: String,
    val ser: String?,
    val num: String?,
    val otd: String?,
    val open: LocalDate?,
    val close: LocalDate?
)
