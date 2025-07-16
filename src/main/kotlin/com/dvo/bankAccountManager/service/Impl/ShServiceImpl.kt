package com.dvo.bankAccountManager.service.Impl

import com.dvo.bankAccountManager.entity.Sh
import com.dvo.bankAccountManager.exception.EntityNotFoundException
import com.dvo.bankAccountManager.mapper.ShMapper
import com.dvo.bankAccountManager.repository.PinsRepository
import com.dvo.bankAccountManager.repository.ShRepository
import com.dvo.bankAccountManager.service.ShService
import com.dvo.bankAccountManager.web.model.request.UpdateShRequest
import jakarta.transaction.Transactional
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class ShServiceImpl(
    private val shRepository: ShRepository,
    private val pinsRepository: PinsRepository,
    private val shMapper: ShMapper
) : ShService {
    private val log = LoggerFactory.getLogger(ShServiceImpl::class.java)

    override fun findAll(): List<Sh> {
        log.info("Call findAll in ShServiceImpl")

        return shRepository.findAll()
    }

    override fun findById(id: Long): Sh {
        log.info("Call findById in ShServiceImpl with ID: {}", id)

        return shRepository.findById(id)
            .orElseThrow {
                EntityNotFoundException("Sh not found with ID: $id")
            }
    }

    @Transactional
    override fun save(sh: Sh): Sh {
        log.info("Call save in ShServiceImpl with sh: {}", sh)

        if (!pinsRepository.existsByPinEq(sh.pinEq)){
            throw EntityNotFoundException("Pin not found with pinEq: ${sh.pinEq}")
        }

        return shRepository.save(sh)
    }

    @Transactional
    override fun update(id: Long, request: UpdateShRequest): Sh {
        log.info("Call update in ShServiceImpl for ID: {} with sh: {}", id, request)

        val sh = shRepository.findById(id)
            .orElseThrow {
                EntityNotFoundException("Sh not found with ID: $id")
            }


        if (!pinsRepository.existsByPinEq(sh.pinEq)){
            throw EntityNotFoundException("Pin not found with pinEq: ${request.pinEq}")
        }

        shMapper.updateRequestToSh(request, sh)

        return shRepository.save(sh)
    }

    @Transactional
    override fun deleteById(id: Long) {
        log.info("Call deleteById in ShServiceImpl with ID: {}", id)

        shRepository.deleteById(id)
    }
}