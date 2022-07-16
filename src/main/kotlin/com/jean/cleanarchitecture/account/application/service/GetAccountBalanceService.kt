package com.jean.cleanarchitecture.account.application.service

import com.jean.cleanarchitecture.account.application.port.`in`.GetAccountBalanceQuery
import com.jean.cleanarchitecture.account.application.port.out.LoadAccountPort
import com.jean.cleanarchitecture.account.domain.Account.AccountId
import com.jean.cleanarchitecture.account.domain.Money
import java.time.LocalDateTime

class GetAccountBalanceService(
    private val loadAccountPort: LoadAccountPort
): GetAccountBalanceQuery {

    override fun getAccountBalance(accountId: AccountId): Money {
        return loadAccountPort.loadAccount(accountId, LocalDateTime.now())
            .calculateBalance()
    }
}
