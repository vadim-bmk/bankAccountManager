package com.dvo.bankAccountManager.service.impl

import com.dvo.bankAccountManager.entity.Pins
import com.dvo.bankAccountManager.entity.Rdk2pf
import com.dvo.bankAccountManager.exception.EntityNotFoundException
import com.dvo.bankAccountManager.mapper.Rdk2pfMapper
import com.dvo.bankAccountManager.repository.PinsRepository
import com.dvo.bankAccountManager.repository.Rdk2pfRepository
import com.dvo.bankAccountManager.service.Impl.Rdk2pfServiceImpl
import com.dvo.bankAccountManager.service.Rdk2pfService
import com.dvo.bankAccountManager.web.filter.Rdk2pfFilter
import com.dvo.bankAccountManager.web.model.request.UpdateRdk2pfRequest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.*
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.domain.Specification
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals

class Rdk2pfServiceImplTest {
    private lateinit var rdk2pfService: Rdk2pfService
    private val rdk2pfRepository: Rdk2pfRepository = mock()
    private val rdk2pfMapper: Rdk2pfMapper = mock()
    private val pinRepository: PinsRepository = mock()

    private lateinit var pin: Pins
    private lateinit var rdk2pf: Rdk2pf

    @BeforeEach
    fun setUp() {
        rdk2pfService = Rdk2pfServiceImpl(rdk2pfRepository, rdk2pfMapper, pinRepository)

        pin = Pins(
            id = 1L,
            pinEq = "U00001",
            firstName = "firstname"
        )

        rdk2pf = Rdk2pf(
            id = 1L,
            pinEq = "U00001"
        )
    }

    @Test
    fun `test findAll`() {
        whenever(rdk2pfRepository.findAll()).thenReturn(listOf(rdk2pf))
        val result = rdk2pfService.findAll()

        assertEquals(1, result.size)
        verify(rdk2pfRepository, times(1)).findAll()
    }

    @Test
    fun `test findAllByFilter`() {
        val filter = Rdk2pfFilter(
            pageNumber = 0,
            pageSize = 10
        )
        val rdk2pfList = listOf(rdk2pf)
        val page =
            PageImpl(rdk2pfList, PageRequest.of(filter.pageNumber!!, filter.pageSize!!), rdk2pfList.size.toLong())

        whenever(
            rdk2pfRepository.findAll(
                any<Specification<Rdk2pf>>(),
                eq(PageRequest.of(filter.pageNumber!!, filter.pageSize!!))
            )
        ).thenReturn(page)

        val result = rdk2pfService.findAllByFilter(filter)

        assertEquals(1, result.size)
    }

    @Test
    fun `test findById`() {
        whenever(rdk2pfRepository.findById(1L)).thenReturn(Optional.of(rdk2pf))
        val result = rdk2pfService.findById(1L)

        assertEquals(rdk2pf, result)
        verify(rdk2pfRepository, times(1)).findById(1L)
    }

    @Test
    fun `test findById when rdk2pf not found`() {
        whenever(rdk2pfRepository.findById(1L)).thenReturn(Optional.empty())
        assertThrows<EntityNotFoundException> {
            rdk2pfService.findById(1L)
        }
    }

    @Test
    fun `test findByPinEq`() {
        whenever(rdk2pfRepository.findByPinEq("U00001")).thenReturn(Optional.of(listOf(rdk2pf)))
        val result = rdk2pfService.findByPinEq("U00001")

        assertEquals(rdk2pf, result.get(0))
        verify(rdk2pfRepository, times(1)).findByPinEq("U00001")
    }

    @Test
    fun `test findByPinEq when rdk2pf not found with pinEq`() {
        whenever(rdk2pfRepository.findByPinEq("U00001")).thenReturn(Optional.empty())
        assertThrows<EntityNotFoundException> {
            rdk2pfService.findByPinEq("U00001")
        }
    }

    @Test
    fun `test save`() {
        whenever(pinRepository.existsByPinEq("U00001")).thenReturn(true)
        whenever(rdk2pfRepository.save(rdk2pf)).thenReturn(rdk2pf)

        val result = rdk2pfService.save(rdk2pf)

        assertEquals(rdk2pf, result)
        verify(pinRepository, times(1)).existsByPinEq("U00001")
        verify(rdk2pfRepository, times(1)).save(rdk2pf)
    }

    @Test
    fun `test save when Pin not founb`() {
        whenever(pinRepository.existsByPinEq("U00001")).thenReturn(false)
        assertThrows<EntityNotFoundException> {
            rdk2pfService.save(rdk2pf)
        }
    }

    @Test
    fun `test update`() {
        val request = UpdateRdk2pfRequest(
            ser = "111"
        )
        val existedRdk2pf = rdk2pf.copy()

        whenever(rdk2pfRepository.findById(1L)).thenReturn(Optional.of(existedRdk2pf))
        whenever(rdk2pfMapper.updateRequestToRdk2pf(request, existedRdk2pf)).thenReturn(existedRdk2pf)
        whenever(rdk2pfRepository.save(existedRdk2pf)).thenReturn(existedRdk2pf)

        val result = rdk2pfService.update(1L, request)

        assertEquals(existedRdk2pf, result)
        verify(rdk2pfRepository, times(1)).findById(1L)
        verify(rdk2pfMapper, times(1)).updateRequestToRdk2pf(request, existedRdk2pf)
        verify(rdk2pfRepository, times(1)).save(existedRdk2pf)
    }

    @Test
    fun `test update when rdk2pf not found`() {
        whenever(rdk2pfRepository.findById(1L)).thenReturn(Optional.empty())
        assertThrows<EntityNotFoundException> {
            rdk2pfService.update(1L, UpdateRdk2pfRequest())
        }
    }

    @Test
    fun `test deleteById`() {
        rdk2pfService.deleteById(1L)
        verify(rdk2pfRepository, times(1)).deleteById(1L)
    }

}