package com.dvo.bankAccountManager.repository

import com.dvo.bankAccountManager.entity.Sh
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface ShRepository : JpaRepository<Sh, Long>, JpaSpecificationExecutor<Sh> {
    fun existsBySbalKodAndSsKod(sbalKod: String, ssKod: String): Boolean
    fun findByPinEq(pinEq: String): List<Sh>
}