package com.dvo.bankAccountManager.service.Impl

import com.dvo.bankAccountManager.entity.Dss
import com.dvo.bankAccountManager.exception.EntityExistsException
import com.dvo.bankAccountManager.exception.EntityNotFoundException
import com.dvo.bankAccountManager.mapper.DssMapper
import com.dvo.bankAccountManager.repository.DssRepository
import com.dvo.bankAccountManager.repository.ShRepository
import com.dvo.bankAccountManager.service.DssService
import com.dvo.bankAccountManager.web.model.request.UpdateDssRequest
import jakarta.transaction.Transactional
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class DssServiceImpl(
    private val dssRepository: DssRepository,
    private val dssMapper: DssMapper,
    private val shRepository: ShRepository
) : DssService {
    private val log = LoggerFactory.getLogger(DssServiceImpl::class.java)

    override fun findAll(): List<Dss> {
        log.info("Call findAll in DssServiceImpl")

        return dssRepository.findAll()
    }

    override fun findById(id: Long): Dss {
        log.info("Call findById in DssServiceImpl with ID: {}", id)

        return dssRepository.findById(id)
            .orElseThrow {
                EntityNotFoundException("Dss not found with ID: $id")
            }
    }

    @Transactional
    override fun save(dss: Dss): Dss {
        log.info("Call save in DssServiceImpl with dss: {}", dss)

        if (dssRepository.existsByRepDateAndSbalKodAndSsKod(dss.repDate, dss.sbalKod, dss.ssKod)) {
            throw EntityExistsException("Dss with repDate: ${dss.repDate} and sbalKod: ${dss.sbalKod} and ssKod: ${dss.ssKod} is already exists")
        }

        if (!shRepository.existsBySbalKodAndSsKod(dss.sbalKod, dss.ssKod)) {
            throw EntityNotFoundException("Sh with sbalKod: ${dss.sbalKod} and ssKod: ${dss.ssKod} not found")
        }

        return dssRepository.save(dss)
    }

    @Transactional
    override fun update(id: Long, dss: UpdateDssRequest): Dss {
        log.info("Call update in DssServiceImpl for ID: {} with dss: {}", id, dss)

        val existingDss = dssRepository.findById(id)
            .orElseThrow {
                EntityNotFoundException("Dss not found with ID: $id")
            }

        dssMapper.updateRequestToDss(dss, existingDss)

        return dssRepository.save(existingDss)
    }

    @Transactional
    override fun deleteById(id: Long) {
        log.info("Call deleteById in DssService with ID: {}", id)

        dssRepository.deleteById(id)
    }
}