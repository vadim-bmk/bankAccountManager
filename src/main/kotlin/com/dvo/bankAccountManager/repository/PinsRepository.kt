package com.dvo.bankAccountManager.repository

import com.dvo.bankAccountManager.entity.Pins
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface PinsRepository: JpaRepository<Pins, Long> {
    fun existsByPinEq(pinEq: String): Boolean
    fun findByPinEq(pinEq: String): Optional<Pins>
    fun deleteByPinEq(pinEq: String)
}