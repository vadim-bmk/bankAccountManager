package com.dvo.bankAccountManager.web.model.response

data class SummaryReportPinResponse(
    val pinEq: String = "",
    val fullName: String? = null,
    val passport: String? = null,
    val accounts: List<AccountSummaryResponse> = emptyList(),
)
