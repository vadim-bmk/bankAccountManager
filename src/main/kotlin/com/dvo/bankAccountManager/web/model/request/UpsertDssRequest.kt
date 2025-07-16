package com.dvo.bankAccountManager.web.model.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.math.BigDecimal
import java.time.LocalDate

data class UpsertDssRequest(
    @field:NotNull(message = "Поле дата (repDate) должно быть заполнено")
    val repDate: LocalDate,

    @field:NotBlank(message = "Поле счет (sbalKod) должно быть заполнено")
    @field:Size(max = 5, min = 5, message = "Поле счет (sbalKod) должно быть 5 символов")
    val sbalKod: String,

    @field:NotBlank(message = "Поле лицевой счет (ssKod) должно быть заполнено")
    @field:Size(max = 15, min = 15, message = "Поле лицевой счет (ssKod) должно быть 15 символов")
    val ssKod: String,

    @field:NotNull(message = "Поле остаток (ost) должно быть заполнено")
    val ost: BigDecimal
)
