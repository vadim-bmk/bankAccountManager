package com.dvo.bankAccountManager.service.Impl

import com.dvo.bankAccountManager.entity.Pins
import com.dvo.bankAccountManager.exception.EntityExistsException
import com.dvo.bankAccountManager.exception.EntityNotFoundException
import com.dvo.bankAccountManager.mapper.PinsMapper
import com.dvo.bankAccountManager.repository.PinsRepository
import com.dvo.bankAccountManager.service.PinsService
import com.dvo.bankAccountManager.web.model.request.UpdatePinsRequest
import jakarta.transaction.Transactional
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class PinsServiceImpl(
    private val pinsRepository: PinsRepository,
    private val pinsMapper: PinsMapper
) : PinsService {
    private val log = LoggerFactory.getLogger(PinsServiceImpl::class.java)

    override fun findAll(): List<Pins> {
        log.info("Call findAll in PinsServiceImpl")

        return pinsRepository.findAll()
    }

    override fun findById(id: Long): Pins {
        log.info("Call findById in PinsServiceImpl with ID: {}", id)

        return pinsRepository.findById(id)
            .orElseThrow {
                EntityNotFoundException("Pins with ID: $id not found")
            }
    }

    @Transactional
    override fun save(pins: Pins): Pins {
        log.info("Call save in PinsServiceImpl with pins: {}", pins)

        if (pinsRepository.existsByPinEq(pins.pinEq)) {
            throw EntityExistsException("Pins with pinEq: ${pins.pinEq} is already exists")
        }

        return pinsRepository.save(pins)
    }

    @Transactional
    override fun update(id: Long, request: UpdatePinsRequest): Pins {
        log.info("Call update in PinsServiceImpl for ID: {} with pins: {}", id, request)

        val existingPins = pinsRepository.findById(id)
            .orElseThrow {
                EntityNotFoundException("Pins with ID: $id not found")
            }

        pinsMapper.updateRequestToPins(request, existingPins)

        return pinsRepository.save(existingPins)
    }

    @Transactional
    override fun deleteById(id: Long) {
        log.info("Call deleteById in PinsServiceImpl with ID: {}", id)

        pinsRepository.deleteById(id)
    }

    override fun findByPinEq(pinEq: String): Pins {
        log.info("Call findByPinEq in PinsServiceImpl with pinEq: {}", pinEq)

        return pinsRepository.findByPinEq(pinEq)
            .orElseThrow {
                EntityNotFoundException("Pins with pinEq: $pinEq not found")
            }
    }

    @Transactional
    override fun deleteByPinEq(pinEq: String) {
        log.info("Call deleteByPinEq in PinsServiceImpl with pinEq: {}", pinEq)

        return pinsRepository.deleteByPinEq(pinEq)
    }
}