package com.dvo.bankAccountManager.web.view

import com.dvo.bankAccountManager.service.ReportService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/report")
class ReportViewController(
    private val reportService: ReportService
) {
    @GetMapping("/pins")
    fun showReportAllPin(model: Model): String{
        val report = reportService.getSummaryReportPinAll()
        model.addAttribute("report", report)

        return "report"
    }

    @GetMapping("/select")
    fun selectPinForm(model: Model): String{
        val allPins = reportService.getSummaryReportPinAll()
        model.addAttribute("pins", allPins)

        return "select-pin"
    }

    @PostMapping("/select")
    fun showPinReport(
        @RequestParam pinEq: String,
        model: Model
    ): String{
        val report = reportService.getSummaryReportPinByPinEq(pinEq)
        model.addAttribute("report", report)

        return "report-single"
    }
}