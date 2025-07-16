package com.dvo.bankAccountManager.web.model.response

import java.math.BigDecimal
import java.time.LocalDate

data class DssResponse(
    val id: Long,
    val repDate: LocalDate,
    val sbalKod: String,
    val ssKod: String,
    val ost: BigDecimal
)
