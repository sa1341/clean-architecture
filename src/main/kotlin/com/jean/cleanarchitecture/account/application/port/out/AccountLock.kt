package com.jean.cleanarchitecture.account.application.port.out

import com.jean.cleanarchitecture.account.domain.Account.AccountId

interface AccountLock {

    fun lockAccount(accountId: AccountId)

    fun releaseAccount(accountId: AccountId)
}
