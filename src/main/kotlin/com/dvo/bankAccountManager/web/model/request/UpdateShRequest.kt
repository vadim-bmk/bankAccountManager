package com.dvo.bankAccountManager.web.model.request

import jakarta.validation.constraints.Size
import java.time.LocalDate

data class UpdateShRequest(
    @field:Size(max = 5, min = 5, message = "Поле счет (sbalKod) должно быть 5 символов")
    val sbalKod: String? = null,

    @field:Size(max = 15, min = 15, message = "Поле лицевой счёт (ssKod) должно быть 15 символов")
    val ssKod: String? = null,

    @field:Size(max = 255, message = "Поле наименование (ssNaim) должно быть до {max} символов")
    val ssNaim: String? = null,

    val actP: Boolean? = null,
    val ssOpen: LocalDate? = null,
    val ssClose: LocalDate? = null,

    @field:Size(max = 3, min = 3, message = "Поле валюта (kv) должно быть 3 символа")
    val kv: String? = null,

    @field:Size(max = 6, min = 6, message = "Поле pinEq должно быть 6 символов")
    val pinEq: String? = null
)
