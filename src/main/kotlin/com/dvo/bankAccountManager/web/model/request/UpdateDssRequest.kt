package com.dvo.bankAccountManager.web.model.request

import jakarta.validation.constraints.Size
import java.math.BigDecimal
import java.time.LocalDate

data class UpdateDssRequest(
    val ost: BigDecimal? = null
)
