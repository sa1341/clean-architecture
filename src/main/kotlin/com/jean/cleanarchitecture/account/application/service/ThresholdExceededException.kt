package com.jean.cleanarchitecture.account.application.service

import com.jean.cleanarchitecture.account.domain.Money

class ThresholdExceededException(
    private val threshold: Money,
    private val actual: Money
): RuntimeException(
    String.format("Maximum threshold for transferring money exceeded: tried to transfer %s but threshold is %s!", actual, threshold)
)
