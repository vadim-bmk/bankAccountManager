package com.dvo.bankAccountManager.validation

import com.dvo.bankAccountManager.web.filter.Rdk2pfFilter
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import org.apache.commons.lang3.ObjectUtils

class Rdk2pfValidValidator : ConstraintValidator<Rdk2pfValid, Rdk2pfFilter> {
    override fun isValid(p0: Rdk2pfFilter, p1: ConstraintValidatorContext): Boolean {
        return !ObjectUtils.anyNull(p0.pageNumber, p0.pageSize)
    }
}