package com.dvo.bankAccountManager.service.impl

import com.dvo.bankAccountManager.entity.Pins
import com.dvo.bankAccountManager.exception.EntityExistsException
import com.dvo.bankAccountManager.exception.EntityNotFoundException
import com.dvo.bankAccountManager.mapper.PinsMapper
import com.dvo.bankAccountManager.repository.PinsRepository
import com.dvo.bankAccountManager.service.Impl.PinsServiceImpl
import com.dvo.bankAccountManager.service.PinsService
import com.dvo.bankAccountManager.web.filter.PinsFilter
import com.dvo.bankAccountManager.web.model.request.UpdatePinsRequest
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

class PinsServiceImplTest {
    private lateinit var pinsService: PinsService
    private val pinsRepository: PinsRepository = mock()
    private val pinsMapper: PinsMapper = mock()

    private lateinit var pin: Pins

    @BeforeEach
    fun setUp() {
        pinsService = PinsServiceImpl(pinsRepository, pinsMapper)

        pin = Pins(
            id = 1L,
            pinEq = "U00001",
            firstName = "firstname"
        )
    }

    @Test
    fun `test findAll`() {
        whenever(pinsRepository.findAll()).thenReturn(listOf(pin))
        val result = pinsService.findAll()

        assertNotNull(result)
        assertEquals(1, result.size)
        verify(pinsRepository, times(1)).findAll()
    }

    @Test
    fun `test findAllByFilter`() {
        val filter = PinsFilter(
            pageNumber = 0,
            pageSize = 10
        )

        val pinsList = listOf(pin)
        val page = PageImpl(pinsList, PageRequest.of(filter.pageNumber!!, filter.pageSize!!), pinsList.size.toLong())

        whenever(
            pinsRepository.findAll(
                any<Specification<Pins>>(),
                eq(PageRequest.of(filter.pageNumber!!, filter.pageSize!!))
            )
        ).thenReturn(page)

        val result = pinsService.findAllByFilter(filter)

        assertNotNull(result)
        assertEquals(1, result.size)
    }

    @Test
    fun `test findById`() {
        whenever(pinsRepository.findById(1L)).thenReturn(Optional.of(pin))

        val result = pinsService.findById(1L)

        assertEquals(1L, result.id)
        assertEquals("U00001", result.pinEq)
        verify(pinsRepository).findById(1L)
    }

    @Test
    fun `test findById when not found`() {
        whenever(pinsRepository.findById(1L)).thenReturn(Optional.empty())

        assertThrows<EntityNotFoundException> {
            pinsService.findById(1L)
        }
    }

    @Test
    fun `test create pin`() {
        whenever(pinsRepository.existsByPinEq("U00001")).thenReturn(false)
        whenever(pinsRepository.save(any<Pins>())).thenReturn(pin)

        val result = pinsService.save(pin)

        assertEquals(1L, result.id)
        assertEquals("U00001", result.pinEq)
        verify(pinsRepository, times(1)).save(pin)
        verify(pinsRepository, times(1)).existsByPinEq("U00001")
    }

    @Test
    fun `test create pin when already exists`() {
        whenever(pinsRepository.existsByPinEq("U00001")).thenReturn(true)

        assertThrows<EntityExistsException> {
            pinsService.save(pin)
        }
    }

    @Test
    fun `test update`() {
        val request = UpdatePinsRequest()
        val existedPin = pin.copy()

        whenever(pinsRepository.findById(1L)).thenReturn(Optional.of(pin))
        whenever(pinsMapper.updateRequestToPins(request, existedPin)).thenReturn(existedPin)
        whenever(pinsRepository.save(existedPin)).thenReturn(existedPin)

        val result = pinsService.update(1L, request)

        assertEquals(1L, result.id)
        verify(pinsRepository, times(1)).findById(1L)
        verify(pinsMapper, times(1)).updateRequestToPins(request, existedPin)
        verify(pinsRepository, times(1)).save(existedPin)
    }

    @Test
    fun `test update when pin not found`() {
        whenever(pinsRepository.findById(1L)).thenReturn(Optional.empty())
        assertThrows<EntityNotFoundException> {
            pinsService.update(1L, UpdatePinsRequest())
        }
    }

    @Test
    fun `test deleteById`() {
        pinsService.deleteById(1L)
        verify(pinsRepository).deleteById(1L)
    }

    @Test
    fun `test findByPinEq`() {
        whenever(pinsRepository.findByPinEq("U00001")).thenReturn(Optional.of(pin))
        val result = pinsService.findByPinEq("U00001")

        assertEquals(1L, result.id)
        verify(pinsRepository, times(1)).findByPinEq("U00001")
    }

    @Test
    fun `test findByPinEq when not found`() {
        whenever(pinsRepository.findByPinEq("U00001")).thenReturn(Optional.empty())
        assertThrows<EntityNotFoundException> {
            pinsService.findByPinEq("U00001")
        }
    }

    @Test
    fun `test deleteByPinEq`() {
        pinsService.deleteByPinEq("U00001")
        verify(pinsRepository, times(1)).deleteByPinEq("U00001")
    }
}
