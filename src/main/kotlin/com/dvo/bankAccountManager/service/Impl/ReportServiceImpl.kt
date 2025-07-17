package com.dvo.bankAccountManager.service.Impl

import com.dvo.bankAccountManager.exception.EntityNotFoundException
import com.dvo.bankAccountManager.repository.DssRepository
import com.dvo.bankAccountManager.repository.PinsRepository
import com.dvo.bankAccountManager.repository.Rdk2pfRepository
import com.dvo.bankAccountManager.repository.ShRepository
import com.dvo.bankAccountManager.service.ReportService
import com.dvo.bankAccountManager.web.model.response.AccountSummaryResponse
import com.dvo.bankAccountManager.web.model.response.SummaryReportPinResponse
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class ReportServiceImpl(
    private val pinsRepository: PinsRepository,
    private val rdk2pfRepository: Rdk2pfRepository,
    private val shRepository: ShRepository,
    private val dssRepository: DssRepository
) : ReportService {
    private val log = LoggerFactory.getLogger(ReportServiceImpl::class.java)

    override fun getSummaryReportPinAll(): List<SummaryReportPinResponse> {
        log.info("Call getSummaryReportPinAll in ReportServiceImpl")

        val pinList = pinsRepository.findAll()

        return pinList.map { pin ->
            val passports = rdk2pfRepository.findByPinEq(pin.pinEq)
                .orElse(emptyList())
                .joinToString(", ") { "${it.ser} ${it.num}" }

            val accounts = shRepository.findByPinEq(pin.pinEq).flatMap { sh ->
                dssRepository.findBySbalKodAndSsKod(sh.sbalKod, sh.ssKod).map { dss ->
                    AccountSummaryResponse(
                        repDate = dss.repDate,
                        acc = "${dss.sbalKod}${dss.ssKod}",
                        balance = dss.ost
                    )
                }
            }

            SummaryReportPinResponse(
                pinEq = pin.pinEq,
                fullName = "${pin.firstName} ${pin.lastName} ${pin.patronymic}",
                passport = passports,
                accounts = accounts
            )
        }
    }

    override fun getSummaryReportPinByPinEq(pinEq: String): SummaryReportPinResponse {
        log.info("Call getSummaryReportPinByPinEq in ReportServiceImpl with pinEq: {}", pinEq)

        val pin = pinsRepository.findByPinEq(pinEq)
            .orElseThrow {
                EntityNotFoundException("Pin $pinEq not found")
            }

        return SummaryReportPinResponse(
            pinEq = pin.pinEq,
            fullName = "${pin.firstName} ${pin.lastName} ${pin.patronymic}",
            passport = rdk2pfRepository.findByPinEq(pin.pinEq)
                .orElse(emptyList())
                .joinToString(", ") { "${it.ser} ${it.num}" },
            accounts = shRepository.findByPinEq(pin.pinEq).flatMap { sh ->
                dssRepository.findBySbalKodAndSsKod(sh.sbalKod, sh.ssKod).map { dss ->
                    AccountSummaryResponse(
                        repDate = dss.repDate,
                        acc = "${dss.sbalKod}${dss.ssKod}",
                        balance = dss.ost
                    )
                }
            }
        )
    }
}