package com.dvo.bankAccountManager.repository

import com.dvo.bankAccountManager.entity.Sh
import org.springframework.data.jpa.repository.JpaRepository

interface ShRepository: JpaRepository<Sh, Long> {
    fun existsBySbalKodAndSsKod(sbalKod: String, ssKod: String): Boolean
}