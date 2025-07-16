package com.dvo.bankAccountManager.web.model.request

import jakarta.validation.constraints.Size

data class UpdatePinsRequest(
    @field:Size(max = 12, message = "Поле ИНН (inn) должно быть до {max} символов")
    val inn: String? = null,

    @field:Size(max = 255, message = "Поле фамилия (firstName) должно быть до {max} символов")
    val firstName: String? = null,

    @field:Size(max = 255, message = "Поле имя (lastName) должно быть до {max} символов")
    val lastName: String? = null,

    @field:Size(max = 255, message = "Поле отчество (patronymic) должно быть до {max} символов")
    val patronymic: String? = null
)
