package com.jean.cleanarchitecture.account.application.port.out

import com.jean.cleanarchitecture.account.domain.Account
import com.jean.cleanarchitecture.account.domain.Account.AccountId
import java.time.LocalDateTime

interface LoadAccountPort {
    fun loadAccount(accountId: AccountId, baselineDate: LocalDateTime): Account
}
