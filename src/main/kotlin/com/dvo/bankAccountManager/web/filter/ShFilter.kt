package com.dvo.bankAccountManager.web.filter

import com.dvo.bankAccountManager.validation.ShFilterValid
import java.time.LocalDate

@ShFilterValid
data class ShFilter(
    var pageNumber: Int? = null,
    var pageSize: Int? = null,
    var id: Long? = null,
    var sbalKod: String? = null,
    var ssKod: String? = null,
    var ssNaim: String? = null,
    var actP: Boolean? = null,
    var ssOpen: LocalDate? = null,
    var ssClose: LocalDate? = null,
    var kv: String? = null,
    var pinEq: String? = null
)
