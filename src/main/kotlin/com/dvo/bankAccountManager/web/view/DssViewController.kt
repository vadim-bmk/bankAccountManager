package com.dvo.bankAccountManager.web.view

import com.dvo.bankAccountManager.exception.EntityExistsException
import com.dvo.bankAccountManager.exception.EntityNotFoundException
import com.dvo.bankAccountManager.mapper.DssMapper
import com.dvo.bankAccountManager.service.DssService
import com.dvo.bankAccountManager.web.model.request.UpdateDssRequest
import com.dvo.bankAccountManager.web.model.request.UpsertDssRequest
import jakarta.validation.Valid
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/dss")
class DssViewController(
    private val dssService: DssService,
    private val dssMapper: DssMapper
) {
    @GetMapping
    fun getAll(model: Model): String {
        val allDss = dssService.findAll().map(dssMapper::dssToResponse)
        model.addAttribute("dssList", allDss)
        model.addAttribute("newDss", UpsertDssRequest())

        return "dss"
    }

    @PostMapping("/create")
    fun create(
        @ModelAttribute("newDss") @Valid request: UpsertDssRequest,
        bindingResult: BindingResult,
        model: Model
    ): String {
        if (bindingResult.hasErrors()) {
            model.addAttribute("dssList", dssService.findAll().map(dssMapper::dssToResponse))
            model.addAttribute("newDss", request)

            return "dss"
        }

        return try {
            dssService.save(dssMapper.requestToDss(request))
            return "redirect:/dss"
        } catch (ex: EntityNotFoundException) {
            bindingResult.rejectValue("sbalKod", "error.sbalKod.notfound", ex.message ?: "sbalKod and ssKod not found")
            model.addAttribute("dssList", dssService.findAll().map(dssMapper::dssToResponse))

            return "dss"
        } catch (ex: EntityExistsException) {

            bindingResult.rejectValue(
                "sbalKod",
                "error.sbalKod.found",
                ex.message ?: "sbalKod and ssKod is already exists"
            )
            model.addAttribute("dssList", dssService.findAll().map(dssMapper::dssToResponse))

            return "dss"
        } catch (ex: Exception) {
            bindingResult.reject("error.global", ex.message ?: "Ошибка при сохранении")
            model.addAttribute("dssList", dssService.findAll().map(dssMapper::dssToResponse))

            return "dss"
        }
    }

    @PostMapping("/update/{id}")
    fun update(
        @PathVariable id: Long,
        @ModelAttribute("updateDss") @Valid request: UpdateDssRequest,
        bindingResult: BindingResult,
        model: Model
    ): String {
        if (bindingResult.hasErrors()) {
            model.addAttribute("dssList", dssService.findAll().map(dssMapper::dssToResponse))
            model.addAttribute("newDss", UpsertDssRequest())
            model.addAttribute("updateDss", request)

            val errorsMap = mutableMapOf<Long, List<String>>()
            errorsMap[id] = bindingResult.fieldErrors.map { it.defaultMessage ?: "Ошибка в поле ${it.field}" }
            model.addAttribute("errorsMap", errorsMap)

            return "dss"
        }

        return try {
            dssService.update(id, request)
            "redirect:/dss"
        } catch (ex: EntityNotFoundException) {

            bindingResult.rejectValue("pinEq", "error.sbalKod.notfound", ex.message ?: "sbalKod and ssKod not found")

            model.addAttribute("dssList", dssService.findAll().map(dssMapper::dssToResponse))
            return "dss"
        } catch (ex: Exception) {
            bindingResult.reject("error.global", ex.message ?: "Ошибка при сохранении")
            model.addAttribute("errorMessage", ex.message ?: "Ошибка при обновлении")
            model.addAttribute("dssList", dssService.findAll().map(dssMapper::dssToResponse))
            model.addAttribute("newDss", UpsertDssRequest())
            model.addAttribute("updateDss", request)

            return "dss"
        }
    }

    @PostMapping("/delete/{id}")
    fun delete(@PathVariable id: Long): String {
        dssService.deleteById(id)
        return "redirect:/dss"
    }
}