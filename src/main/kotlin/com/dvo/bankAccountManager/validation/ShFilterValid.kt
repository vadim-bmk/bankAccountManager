package com.dvo.bankAccountManager.validation

import jakarta.validation.Constraint
import kotlin.reflect.KClass

@MustBeDocumented
@Constraint(validatedBy = [ShFilterValidValidator::class])
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class ShFilterValid (
    val message: String = "Поля для пагинации должны быть указаны!",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Any>> = []
)