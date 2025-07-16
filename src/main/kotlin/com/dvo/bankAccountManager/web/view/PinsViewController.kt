package com.dvo.bankAccountManager.web.view

import com.dvo.bankAccountManager.exception.EntityExistsException
import com.dvo.bankAccountManager.mapper.PinsMapper
import com.dvo.bankAccountManager.service.PinsService
import com.dvo.bankAccountManager.web.model.request.UpdatePinsRequest
import com.dvo.bankAccountManager.web.model.request.UpsertPinsRequest
import jakarta.validation.Valid
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/pins")
class PinsViewController(
    private val pinsService: PinsService,
    private val pinsMapper: PinsMapper
) {
    @GetMapping
    fun getAll(model: Model): String {
        val allPins = pinsService.findAll().map(pinsMapper::pinsToResponse)
        model.addAttribute("pinsList", allPins)
        model.addAttribute("newPin", UpsertPinsRequest())

        return "pins"
    }

    @PostMapping("/create")
    fun create(
        @ModelAttribute("newPin") @Valid request: UpsertPinsRequest,
        bindingResult: BindingResult,
        model: Model
    ): String {
        if (bindingResult.hasErrors()) {
            model.addAttribute("pinsList", pinsService.findAll().map(pinsMapper::pinsToResponse))
            model.addAttribute("newSh", request)
            return "pins"
        }

        return try {
            pinsService.save(pinsMapper.requestToPins(request))
            return "redirect:/pins"
        } catch (ex: EntityExistsException) {
            model.addAttribute("errorMessage", ex.message ?: "Ошибка при создании пина")
            model.addAttribute("pinsList", pinsService.findAll().map(pinsMapper::pinsToResponse))
            model.addAttribute("newPin", request)
            "pins"
        }

    }

    @PostMapping("/update/{id}")
    fun update(
        @PathVariable id: Long,
        @ModelAttribute("updatePin") @Valid request: UpdatePinsRequest,
        bindingResult: BindingResult,
        model: Model
    ): String {
        if (bindingResult.hasErrors()) {
            val pinsList = pinsService.findAll().map(pinsMapper::pinsToResponse)
            model.addAttribute("pinsList", pinsList)
            model.addAttribute("newPin", UpsertPinsRequest("", null, null, null, null))

            val errorsMap = mutableMapOf<Long, List<String>>()
            errorsMap[id] = bindingResult.fieldErrors.map { it.defaultMessage ?: "Ошибка в поле ${it.field}" }
            model.addAttribute("errorsMap", errorsMap)

            return "pins"
        }

        return try {
            pinsService.update(id, request)
            "redirect:/pins"
        } catch (ex: Exception) {
            model.addAttribute("errorMessage", ex.message ?: "Ошибка при обновлении пина")
            model.addAttribute("pinsList", pinsService.findAll().map(pinsMapper::pinsToResponse))
            model.addAttribute("newPin", UpsertPinsRequest("", null, null, null, null))
            return "pins"
        }
    }

    @PostMapping("/delete/{id}")
    fun delete(@PathVariable id: Long): String {
        pinsService.deleteById(id)
        return "redirect:/pins"
    }
}