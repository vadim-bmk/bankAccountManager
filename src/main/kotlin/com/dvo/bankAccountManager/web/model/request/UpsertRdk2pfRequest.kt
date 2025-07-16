package com.dvo.bankAccountManager.web.model.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.time.LocalDate

data class UpsertRdk2pfRequest(
    @field:NotBlank(message = "Поле pinEq должно быть заполнено")
    @field:Size(max = 6, min = 6, message = "Поле pinEq должно быть 6 символов")
    val pinEq: String,

    @field:Size(max = 4, message = "Поле серия (ser) должно быть не более 4 символов")
    val ser: String?,

    @field:Size(max = 4, message = "Поле номер (num) должно быть не более 10 символов")
    val num: String?,

    @field:Size(max = 255, message = "Поле отделение (otd) должно быть не 255 символов")
    val otd: String?,

    val open: LocalDate?,
    val close: LocalDate?
)
