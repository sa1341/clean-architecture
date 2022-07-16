package com.jean.cleanarchitecture.account.application.service

import com.jean.cleanarchitecture.account.domain.Money

class MoneyTransferProperties {

    companion object {
        val maximumTransferThreshold = Money.of(1_000_000L)
    }
}
