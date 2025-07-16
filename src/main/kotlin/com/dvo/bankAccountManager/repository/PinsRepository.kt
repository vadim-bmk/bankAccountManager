package com.dvo.bankAccountManager.repository

import com.dvo.bankAccountManager.entity.Pins
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import java.util.Optional

interface PinsRepository: JpaRepository<Pins, Long>, JpaSpecificationExecutor<Pins> {
    fun existsByPinEq(pinEq: String): Boolean
    fun findByPinEq(pinEq: String): Optional<Pins>
    fun deleteByPinEq(pinEq: String)
}