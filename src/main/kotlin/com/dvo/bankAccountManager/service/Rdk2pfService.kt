package com.dvo.bankAccountManager.service

import com.dvo.bankAccountManager.entity.Rdk2pf
import com.dvo.bankAccountManager.web.filter.Rdk2pfFilter
import com.dvo.bankAccountManager.web.model.request.UpdateRdk2pfRequest

interface Rdk2pfService {
    fun findAll(): List<Rdk2pf>
    fun findAllByFilter(filter: Rdk2pfFilter): List<Rdk2pf>
    fun findById(id: Long): Rdk2pf
    fun findByPinEq(pinEq: String): List<Rdk2pf>
    fun save(rdk2pf: Rdk2pf): Rdk2pf
    fun update(id: Long, rdk2pf: UpdateRdk2pfRequest): Rdk2pf
    fun deleteById(id: Long)
}