package com.dvo.bankAccountManager.service.Impl

import com.dvo.bankAccountManager.entity.Rdk2pf
import com.dvo.bankAccountManager.exception.EntityNotFoundException
import com.dvo.bankAccountManager.mapper.Rdk2pfMapper
import com.dvo.bankAccountManager.repository.PinsRepository
import com.dvo.bankAccountManager.repository.Rdk2pfRepository
import com.dvo.bankAccountManager.service.Rdk2pfService
import com.dvo.bankAccountManager.web.model.request.UpdateRdk2pfRequest
import jakarta.transaction.Transactional
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class Rdk2pfServiceImpl(
    private val rdk2pfRepository: Rdk2pfRepository,
    private val rdk2pfMapper: Rdk2pfMapper,
    private val pinsRepository: PinsRepository
): Rdk2pfService {
    private val log = LoggerFactory.getLogger(Rdk2pfServiceImpl::class.java)

    override fun findAll(): List<Rdk2pf> {
        log.info("Call findAll in Rdk2pfServiceImpl")

        return rdk2pfRepository.findAll()
    }

    override fun findById(id: Long): Rdk2pf {
        log.info("Call findById in Rdk2pfServiceImpl with ID: {}", id)

        return rdk2pfRepository.findById(id)
            .orElseThrow {
                EntityNotFoundException("Rdk2pf not found with ID: $id")
            }
    }

    override fun findByPinEq(pinEq: String): List<Rdk2pf> {
        log.info("Call findByPinEq in Rdk2pfServiceImpl with pinEq: {}", pinEq)

        return rdk2pfRepository.findByPinEq(pinEq)
            .orElseThrow {
                EntityNotFoundException("Rdk2pf not found with pinEq: $pinEq")
            }
    }

    @Transactional
    override fun save(rdk2pf: Rdk2pf): Rdk2pf {
        log.info("Call save in Rdk2pfServiceImpl with rdk2pf: {}", rdk2pf)

        if (!pinsRepository.existsByPinEq(rdk2pf.pinEq)){
            throw EntityNotFoundException("Pins not found with pinEq: ${rdk2pf.pinEq}")
        }

        return rdk2pfRepository.save(rdk2pf)
    }

    @Transactional
    override fun update(id: Long, rdk2pf: UpdateRdk2pfRequest): Rdk2pf {
        log.info("Call update in Rdk2pfServiceImpl for ID: {} with rdk2pf: {}", id, rdk2pf)

        val existingRdk2pf = rdk2pfRepository.findById(id)
            .orElseThrow {
                EntityNotFoundException("Rdk2pf not found with ID: $id")
            }

        rdk2pfMapper.updateRequestToRdk2pf(rdk2pf, existingRdk2pf)

        return rdk2pfRepository.save(existingRdk2pf)
    }

    @Transactional
    override fun deleteById(id: Long) {
        log.info("Call deleteById in Rdk2pfServiceImpl with ID: {}", id)

        rdk2pfRepository.deleteById(id)
    }
}