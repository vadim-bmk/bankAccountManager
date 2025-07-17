package com.dvo.bankAccountManager.web.controller

import com.dvo.bankAccountManager.service.ReportService
import com.dvo.bankAccountManager.web.model.response.SummaryReportPinResponse
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/report")
class ReportController(
    private val reportService: ReportService
) {
    @GetMapping("/pins")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get summary report PIN for all", description = "Get summary report PIN EQ for all", tags = ["report"])
    fun getSummaryReportPinAll(): ResponseEntity<List<SummaryReportPinResponse>> {
        return ResponseEntity.ok(reportService.getSummaryReportPinAll())
    }

    @GetMapping("/pins/{pinEq}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get summary report PIN EQ", description = "Get summary report PIN EQ", tags = ["report"])
    fun getSummaryReportPinByPinEq(@PathVariable pinEq: String): ResponseEntity<SummaryReportPinResponse>{
        return ResponseEntity.ok(reportService.getSummaryReportPinByPinEq(pinEq))
    }
}