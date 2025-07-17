package com.dvo.bankAccountManager.web.model.response

import java.math.BigDecimal
import java.time.LocalDate

data class AccountSummaryResponse(
    val repDate: LocalDate = LocalDate.now(),
    val acc: String? = null,
    val balance: BigDecimal = BigDecimal.valueOf(0)
)
