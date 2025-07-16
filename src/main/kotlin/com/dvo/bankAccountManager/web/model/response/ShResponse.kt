package com.dvo.bankAccountManager.web.model.response

import java.time.LocalDate

data class ShResponse(
    val id: Long,
    val sbalKod: String,
    val ssKod: String,
    val ssNaim: String?,
    val actP: Boolean?,
    val ssOpen: LocalDate,
    val ssClose: LocalDate?,
    val kv: String,
    val pinEq: String
)
