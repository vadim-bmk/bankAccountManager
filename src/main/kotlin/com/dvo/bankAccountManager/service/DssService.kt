package com.dvo.bankAccountManager.service

import com.dvo.bankAccountManager.entity.Dss
import com.dvo.bankAccountManager.web.model.request.UpdateDssRequest
import java.time.LocalDate

interface DssService {
    fun findAll(): List<Dss>
    fun findById(id: Long): Dss

    //    fun findByRepDate(repDate: LocalDate): List<Dss>
//    fun findByRepDateAndSbalKodAndSsKod(repDate: LocalDate, sbalKod: String, ssKod: String): Dss
//    fun findBySbalKodAndSSKod(sbalKod: String, ssKod: String): List<Dss>
    fun save(dss: Dss): Dss
    fun update(id: Long, dss: UpdateDssRequest): Dss
    fun deleteById(id: Long)
//    fun deleteBySbalKodAndSSKod(sbalKod: String, ssKod: String)
//    fun deleteByRepDateAndSbalKodAndSsKod(repDate: LocalDate, sbalKod: String, ssKod: String)
}