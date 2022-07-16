package com.jean.cleanarchitecture.common

import javax.validation.ConstraintViolation
import javax.validation.ConstraintViolationException
import javax.validation.Validation
import javax.validation.Validator

abstract class SelfValidating<T> {

    private val validator: Validator

    init {
        val factory = Validation.buildDefaultValidatorFactory()
        this.validator = factory.validator
    }

    protected fun validateSelf() {
        val violations: Set<ConstraintViolation<T>> = validator.validate(this as T)

        if (violations.isNotEmpty())  {
            throw ConstraintViolationException(violations)
        }
    }
}
