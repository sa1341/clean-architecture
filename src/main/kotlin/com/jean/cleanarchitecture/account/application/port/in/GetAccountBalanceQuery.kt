package com.jean.cleanarchitecture.account.application.port.`in`

import com.jean.cleanarchitecture.account.domain.Account.AccountId
import com.jean.cleanarchitecture.account.domain.Money

interface GetAccountBalanceQuery {
    fun getAccountBalance(accountId: AccountId): Money
}
