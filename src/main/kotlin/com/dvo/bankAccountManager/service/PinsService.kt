package com.dvo.bankAccountManager.service

import com.dvo.bankAccountManager.entity.Pins
import com.dvo.bankAccountManager.web.model.request.UpdatePinsRequest

interface PinsService {
    fun findAll(): List<Pins>
    fun findById(id: Long): Pins
    fun save(pins: Pins): Pins
    fun update(id: Long, request: UpdatePinsRequest): Pins
    fun deleteById(id: Long)
    fun findByPinEq(pinEq: String): Pins
    fun deleteByPinEq(pinEq: String)
}