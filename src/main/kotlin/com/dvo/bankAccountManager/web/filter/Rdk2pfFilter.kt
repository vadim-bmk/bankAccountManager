package com.dvo.bankAccountManager.web.filter

import com.dvo.bankAccountManager.validation.Rdk2pfValid
import java.time.LocalDate

@Rdk2pfValid
data class Rdk2pfFilter(
    var pageNumber: Int? = null,
    var pageSize: Int? = null,
    var id: Long? = null,
    var pinEq: String? = null,
    var ser: String? = null,
    var num: String? = null,
    var otd: String? = null,
    var open: LocalDate? = null,
    var close: LocalDate? = null

)
