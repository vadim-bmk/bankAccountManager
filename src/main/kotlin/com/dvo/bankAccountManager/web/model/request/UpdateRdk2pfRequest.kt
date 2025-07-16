package com.dvo.bankAccountManager.web.model.request

import jakarta.validation.constraints.Size
import java.time.LocalDate

data class UpdateRdk2pfRequest(
    @field:Size(max = 4, message = "Поле серия (ser) должно быть не более 4 символов")
    val ser: String? = null,

    @field:Size(max = 4, message = "Поле номер (num) должно быть не более 10 символов")
    val num: String? = null,

    @field:Size(max = 255, message = "Поле отделение (otd) должно быть не 255 символов")
    val otd: String? = null,

    val open: LocalDate? = null,
    val close: LocalDate? = null
)
