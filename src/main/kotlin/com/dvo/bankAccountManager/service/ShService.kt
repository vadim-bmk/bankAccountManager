package com.dvo.bankAccountManager.service

import com.dvo.bankAccountManager.entity.Sh
import com.dvo.bankAccountManager.web.filter.ShFilter
import com.dvo.bankAccountManager.web.model.request.UpdateShRequest

interface ShService {
    fun findAll(): List<Sh>
    fun findAllByFilter(filter: ShFilter): List<Sh>
    fun findById(id: Long): Sh
    fun save(sh: Sh): Sh
    fun update(id: Long, request: UpdateShRequest): Sh
    fun deleteById(id: Long)
}