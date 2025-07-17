package com.dvo.bankAccountManager.repository

import com.dvo.bankAccountManager.entity.Dss
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate

interface DssRepository: JpaRepository<Dss, Long> {
    fun existsByRepDateAndSbalKodAndSsKod(repDate: LocalDate, sbalKod: String, ssKod: String): Boolean
    fun findBySbalKodAndSsKod(sbalKod: String, ssKod: String): List<Dss>
}