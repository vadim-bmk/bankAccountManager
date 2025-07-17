package com.dvo.bankAccountManager.service.impl

import com.dvo.bankAccountManager.entity.Dss
import com.dvo.bankAccountManager.entity.Sh
import com.dvo.bankAccountManager.exception.EntityExistsException
import com.dvo.bankAccountManager.exception.EntityNotFoundException
import com.dvo.bankAccountManager.mapper.DssMapper
import com.dvo.bankAccountManager.repository.DssRepository
import com.dvo.bankAccountManager.repository.ShRepository
import com.dvo.bankAccountManager.service.DssService
import com.dvo.bankAccountManager.service.Impl.DssServiceImpl
import com.dvo.bankAccountManager.web.model.request.UpdateDssRequest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*
import kotlin.test.assertEquals

class DssServiceImplTest {
    private lateinit var dssService: DssService
    private val dssRepository: DssRepository = mock()
    private val dssMapper: DssMapper = mock()
    private val shRepository: ShRepository = mock()

    private lateinit var sh: Sh
    private lateinit var dss: Dss

    @BeforeEach
    fun setUp() {
        dssService = DssServiceImpl(dssRepository, dssMapper, shRepository)

        sh = Sh(
            id = 1L,
            sbalKod = "40820",
            pinEq = "U00001"
        )

        dss = Dss(
            id = 1L,
            sbalKod = "40520",
            ssKod = "",
            repDate = LocalDate.of(2025, 5, 25),
        )
    }

    @Test
    fun `test findAll`() {
        whenever(dssRepository.findAll()).thenReturn(listOf(dss))
        val result = dssService.findAll()

        assertEquals(1, result.size)
        verify(dssRepository, times(1)).findAll()
    }

    @Test
    fun `test findById`() {
        whenever(dssRepository.findById(1L)).thenReturn(Optional.of(dss))
        val result = dssService.findById(1L)

        assertEquals(dss, result)
        verify(dssRepository, times(1)).findById(1L)
    }

    @Test
    fun `test findById when dss not found`() {
        whenever(dssRepository.findById(1L)).thenReturn(Optional.empty())
        assertThrows<EntityNotFoundException> {
            dssService.findById(1L)
        }
    }

    @Test
    fun `test save`() {
        whenever(dssRepository.existsByRepDateAndSbalKodAndSsKod(LocalDate.of(2025, 5, 25), "40520", "")).thenReturn(
            false
        )
        whenever(shRepository.existsBySbalKodAndSsKod("40520", "")).thenReturn(true)
        whenever(dssRepository.save(dss)).thenReturn(dss)

        val result = dssService.save(dss)

        assertEquals(dss, result)
        verify(dssRepository, times(1)).existsByRepDateAndSbalKodAndSsKod(LocalDate.of(2025, 5, 25), "40520", "")
        verify(shRepository, times(1)).existsBySbalKodAndSsKod("40520", "")
        verify(dssRepository, times(1)).save(dss)
    }

    @Test
    fun `test save when dss exists`() {
        whenever(dssRepository.existsByRepDateAndSbalKodAndSsKod(LocalDate.of(2025, 5, 25), "40520", "")).thenReturn(
            true
        )
        whenever(shRepository.existsBySbalKodAndSsKod("40520", "")).thenReturn(true)
        assertThrows<EntityExistsException> {
            dssService.save(dss)
        }
    }

    @Test
    fun `test save when sh not found`() {
        whenever(dssRepository.existsByRepDateAndSbalKodAndSsKod(LocalDate.of(2025, 5, 25), "40520", "")).thenReturn(
            false
        )
        whenever(shRepository.existsBySbalKodAndSsKod("40520", "")).thenReturn(false)

        assertThrows<EntityNotFoundException> {
            dssService.save(dss)
        }
    }

    @Test
    fun `test update`() {
        val request = UpdateDssRequest(
            ost = BigDecimal.valueOf(100)
        )
        val existedDss = dss.copy()
        whenever(dssRepository.findById(1L)).thenReturn(Optional.of(existedDss))
        whenever(dssMapper.updateRequestToDss(request, existedDss)).thenReturn(existedDss)
        whenever(dssRepository.save(existedDss)).thenReturn(existedDss)

        val result = dssService.update(1L, request)

        assertEquals(existedDss, result)
    }

    @Test
    fun `test update when dss not found`() {
        whenever(dssRepository.findById(1L)).thenReturn(Optional.empty())

        assertThrows<EntityNotFoundException> {
            dssService.update(1L, UpdateDssRequest())
        }
    }

    @Test
    fun `test deleteById`() {
        dssService.deleteById(1L)
        verify(dssRepository, times(1)).deleteById(1L)
    }
}