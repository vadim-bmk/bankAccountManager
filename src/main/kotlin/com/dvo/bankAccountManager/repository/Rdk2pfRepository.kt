package com.dvo.bankAccountManager.repository

import com.dvo.bankAccountManager.entity.Rdk2pf
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface Rdk2pfRepository : JpaRepository<Rdk2pf, Long> {
    fun findByPinEq(pinEq: String): Optional<List<Rdk2pf>>
}