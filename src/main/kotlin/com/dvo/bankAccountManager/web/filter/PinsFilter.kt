package com.dvo.bankAccountManager.web.filter

import com.dvo.bankAccountManager.validation.PinsFilterValid

@PinsFilterValid
data class PinsFilter(
    var pageNumber: Int? = null,
    var pageSize: Int? = null,
    var id: Long? = null,
    var pinEq: String? = null,
    var inn: String? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var patronymic: String? = null
)
