package com.dvo.bankAccountManager.web.view

import com.dvo.bankAccountManager.exception.EntityNotFoundException
import com.dvo.bankAccountManager.mapper.Rdk2pfMapper
import com.dvo.bankAccountManager.service.Rdk2pfService
import com.dvo.bankAccountManager.web.model.request.UpdateRdk2pfRequest
import com.dvo.bankAccountManager.web.model.request.UpsertRdk2pfRequest
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
@RequestMapping("/rdk2pf")
class Rdk2pfViewController(
    private val rdk2pfService: Rdk2pfService,
    private val rdk2pfMapper: Rdk2pfMapper
) {
    @GetMapping
    fun getAll(model: Model): String {
        model.addAttribute("rdk2pfList", rdk2pfService.findAll().map(rdk2pfMapper::rdk2pfToResponse))
        model.addAttribute("newRdk2pf", UpsertRdk2pfRequest())

        return "rdk2pf"
    }

    @PostMapping("/create")
    fun create(
        @ModelAttribute("newRdk2pf") @Valid request: UpsertRdk2pfRequest,
        bindingResult: BindingResult,
        model: Model
    ): String {
        if (bindingResult.hasErrors()) {
            model.addAttribute("rdk2pfList", rdk2pfService.findAll().map(rdk2pfMapper::rdk2pfToResponse))
            model.addAttribute("newRdk2pf", request)

            return "rdk2pf"
        }

        return try {
            rdk2pfService.save(rdk2pfMapper.requestToRdk2pf(request))
            return "redirect:/rdk2pf"
        } catch (ex: EntityNotFoundException) {
            bindingResult.rejectValue("pinEq", "error.pinEq.notFound", ex.message ?: "pinEq not found")
            model.addAttribute("rdk2pfList", rdk2pfService.findAll().map(rdk2pfMapper::rdk2pfToResponse))

            return "rdk2pf"
        } catch (ex: Exception) {
            bindingResult.reject("error.global", ex.message ?: "Ошибка при сохранении")
            model.addAttribute("rdk2pfList", rdk2pfService.findAll().map(rdk2pfMapper::rdk2pfToResponse))

            return "rdk2pf"
        }
    }

    @PostMapping("/update/{id}")
    fun update(
        @PathVariable id: Long,
        @ModelAttribute("updateRdk2pf") @Valid request: UpdateRdk2pfRequest,
        bindingResult: BindingResult,
        model: Model
    ): String {
        if (bindingResult.hasErrors()) {
            model.addAttribute("rdk2pfList", rdk2pfService.findAll().map(rdk2pfMapper::rdk2pfToResponse))
            model.addAttribute("newRdk2pf", UpsertRdk2pfRequest())
            model.addAttribute("updateRdk2pf", request)

            val errorsMap = mutableMapOf<Long, List<String>>()
            errorsMap[id] = bindingResult.fieldErrors.map { it.defaultMessage ?: "Ошибка в поле ${it.field}" }
            model.addAttribute("errorsMap", errorsMap)

            return "rdk2pf"
        }

        return try {
            rdk2pfService.update(id, request)
            "redirect:/rdk2pf"
        } catch (ex: EntityNotFoundException) {
            bindingResult.rejectValue("pinEq", "error.pinEq.notFound", ex.message ?: "pinEq not found")
            model.addAttribute("rdk2pfList", rdk2pfService.findAll().map(rdk2pfMapper::rdk2pfToResponse))

            return "rdk2pf"
        } catch (ex: Exception) {
            bindingResult.reject("error.global", ex.message ?: "Ошибка при сохранении")
            model.addAttribute("errorMessage", ex.message ?: "Ошибка при обновлении")
            model.addAttribute("rdk2pfList", rdk2pfService.findAll().map(rdk2pfMapper::rdk2pfToResponse))
            model.addAttribute("newRdk2pf", UpsertRdk2pfRequest())
            model.addAttribute("updateRdk2pf", request)

            return "rdk2pf"
        }
    }

    @PostMapping("/delete/{id}")
    fun delete(@PathVariable id: Long): String {
        rdk2pfService.deleteById(id)
        return "redirect:/rdk2pf"
    }
}