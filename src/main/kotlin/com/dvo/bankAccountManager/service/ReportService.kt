package com.dvo.bankAccountManager.service

import com.dvo.bankAccountManager.web.model.response.SummaryReportPinResponse

interface ReportService {
    fun getSummaryReportPinAll(): List<SummaryReportPinResponse>
    fun getSummaryReportPinByPinEq(pinEq: String): SummaryReportPinResponse
}