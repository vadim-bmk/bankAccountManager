package com.dvo.bankAccountManager.web.view

import com.dvo.bankAccountManager.exception.EntityNotFoundException
import com.dvo.bankAccountManager.mapper.ShMapper
import com.dvo.bankAccountManager.service.ShService
import com.dvo.bankAccountManager.web.filter.ShFilter
import com.dvo.bankAccountManager.web.model.request.UpdateShRequest
import com.dvo.bankAccountManager.web.model.request.UpsertShRequest
import jakarta.validation.Valid
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/sh")
class ShViewController(
    private val shService: ShService,
    private val shMapper: ShMapper
) {
    @GetMapping
    fun getAll(
        @ModelAttribute("filter") filter: ShFilter,
        model: Model
    ): String {
        val allSh = shService.findAllByFilter(filter).map(shMapper::shToResponse)
        model.addAttribute("shList", allSh)
        model.addAttribute("newSh", UpsertShRequest())
        model.addAttribute("filter", filter)

        return "sh"
    }

    @PostMapping("/create")
    fun create(
        @ModelAttribute("filter") filter: ShFilter,
        @ModelAttribute("newSh") @Valid request: UpsertShRequest,
        bindingResult: BindingResult,
        model: Model
    ): String {
        val allSh = shService.findAllByFilter(filter).map(shMapper::shToResponse)

        if (bindingResult.hasErrors()){
            model.addAttribute("shList", allSh)
            model.addAttribute("filter", filter)
            return "sh"
        }

        return try {
            shService.save(shMapper.requestToSh(request))
            "redirect:/sh"
        } catch (ex: EntityNotFoundException) {
            bindingResult.rejectValue("pinEq", "error.pinEq.notfound", ex.message ?: "Pin not found")

            model.addAttribute("shList", allSh)
            model.addAttribute("filter", filter)
            return "sh"
        } catch (ex: Exception) {
            bindingResult.reject("error.global", ex.message ?: "Ошибка при сохранении")

            model.addAttribute("shList", allSh)
            model.addAttribute("filter", filter)
            return "sh"
        }
    }

    @PostMapping("/update/{id}")
    fun update(
        @PathVariable id: Long,
        @ModelAttribute("updateSh") @Valid request: UpdateShRequest,
        bindingResult: BindingResult,
        model: Model,
        @ModelAttribute("filter") filter: ShFilter
    ): String {
        val allSh = shService.findAllByFilter(filter).map(shMapper::shToResponse)
        if (bindingResult.hasErrors()) {
            model.addAttribute("shList", allSh)
            model.addAttribute("newSh", UpsertShRequest())
            model.addAttribute("updateSh", request)
            model.addAttribute("filter", filter)

            val errorsMap = mutableMapOf<Long, List<String>>()
            errorsMap[id] = bindingResult.fieldErrors.map { it.defaultMessage ?: "Ошибка в поле ${it.field}" }
            model.addAttribute("errorsMap", errorsMap)

            return "sh"
        }

        return try {
            shService.update(id, request)
            "redirect:/sh"
        } catch (ex: EntityNotFoundException) {
            bindingResult.rejectValue("pinEq", "error.pinEq.notfound", ex.message ?: "Pin not found")

            model.addAttribute("shList", allSh)
            model.addAttribute("filter", filter)
            return "sh"
        } catch (ex: Exception) {
            bindingResult.reject("error.global", ex.message ?: "Ошибка при сохранении")
            model.addAttribute("errorMessage", ex.message ?: "Ошибка при обновлении")
            model.addAttribute("shList", allSh)
            model.addAttribute("newSh", UpsertShRequest())
            model.addAttribute("updateSh", request)
            model.addAttribute("filter", filter)
            return "sh"
        }
    }

    @PostMapping("/delete/{id}")
    fun delete(@PathVariable id: Long): String {
        shService.deleteById(id)
        return "redirect:/sh"
    }
}