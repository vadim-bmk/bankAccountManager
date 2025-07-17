package com.dvo.bankAccountManager.service.impl

import com.dvo.bankAccountManager.entity.Pins
import com.dvo.bankAccountManager.entity.Sh
import com.dvo.bankAccountManager.exception.EntityNotFoundException
import com.dvo.bankAccountManager.mapper.ShMapper
import com.dvo.bankAccountManager.repository.PinsRepository
import com.dvo.bankAccountManager.repository.ShRepository
import com.dvo.bankAccountManager.service.Impl.ShServiceImpl
import com.dvo.bankAccountManager.service.ShService
import com.dvo.bankAccountManager.web.filter.ShFilter
import com.dvo.bankAccountManager.web.model.request.UpdateShRequest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.*
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.domain.Specification
import java.util.*
import kotlin.test.assertEquals

class ShServiceImplTest {
    private lateinit var shService: ShService
    private val shRepository: ShRepository = mock()
    private val pinsRepository: PinsRepository = mock()
    private val shMapper: ShMapper = mock()

    private lateinit var pin: Pins
    private lateinit var sh: Sh

    @BeforeEach
    fun setUp() {
        shService = ShServiceImpl(shRepository, pinsRepository, shMapper)

        pin = Pins(
            id = 1L,
            pinEq = "U00001"
        )

        sh = Sh(
            id = 1L,
            sbalKod = "40820",
            pinEq = "U00001"
        )
    }

    @Test
    fun `test findAll`() {
        whenever(shRepository.findAll()).thenReturn(listOf((sh)))
        val result = shService.findAll()

        assertNotNull(result)
        assertEquals(1, result.size)

        verify(shRepository, times(1)).findAll()
    }

    @Test
    fun `test findAllByFilter`() {
        val filter = ShFilter(
            pageNumber = 0,
            pageSize = 10
        )
        val shList = listOf(sh)
        val page = PageImpl(shList, PageRequest.of(filter.pageNumber!!, filter.pageSize!!), shList.size.toLong())

        whenever(
            shRepository.findAll(
                any<Specification<Sh>>(),
                eq(PageRequest.of(filter.pageNumber!!, filter.pageSize!!))
            )
        ).thenReturn(page)

        val result = shService.findAllByFilter(filter)

        assertEquals(1, result.size)
    }

    @Test
    fun `test findById` () {
        whenever(shRepository.findById(1L)).thenReturn(Optional.of(sh))
        val result = shService.findById(1L)

        assertEquals(sh, result)
        verify(shRepository, times(1)).findById(1L)
    }

    @Test
    fun `test findById when not found` () {
        whenever(shRepository.findById(1L)).thenReturn(Optional.empty())
        assertThrows<EntityNotFoundException> {
            shService.findById(1L)
        }
    }

    @Test
    fun `test save sh`(){
        whenever(pinsRepository.existsByPinEq("U00001")).thenReturn(true)
        whenever(shRepository.save(sh)).thenReturn(sh)

        val result = shService.save(sh)

        assertEquals(sh, result)
        verify(pinsRepository, times(1)).existsByPinEq("U00001")
        verify(shRepository, times(1)).save(sh)
    }

    @Test
    fun `test save sh when pin not found`(){
        whenever(pinsRepository.existsByPinEq("U00001")).thenReturn(false)
        assertThrows<EntityNotFoundException> {
            shService.save(sh)
        }
    }

    @Test
    fun `test update sh`(){
        val request = UpdateShRequest(
            sbalKod = "40820"
        )
        val existedSh = sh.copy()

        whenever(shRepository.findById(1L)).thenReturn(Optional.of(existedSh))
        whenever(pinsRepository.existsByPinEq("U00001")).thenReturn(true)
        whenever(shMapper.updateRequestToSh(request, existedSh)).thenReturn(existedSh)
        whenever(shRepository.save(existedSh)).thenReturn(existedSh)

        val result= shService.update(1L, request)

        assertEquals(sh, existedSh)
        verify(shRepository, times(1)).findById(1L)
        verify(pinsRepository, times(1)).existsByPinEq("U00001")
        verify(shMapper, times(1)).updateRequestToSh(request, existedSh)
    }

    @Test
    fun `test update sh when Sh not found`(){
        whenever(shRepository.findById(1L)).thenReturn(Optional.empty())
        assertThrows<EntityNotFoundException> {
            shService.update(1L, UpdateShRequest())
        }
    }

    @Test
    fun `test update sh when Pin not found`(){
        whenever(pinsRepository.existsByPinEq("U00001")).thenReturn(false)
        assertThrows<EntityNotFoundException> {
            shService.update(1L, UpdateShRequest())
        }
    }

    @Test
    fun `test deleteById`(){
        shService.deleteById(1L)
        verify(shRepository, times(1)).deleteById(1L)
    }
}