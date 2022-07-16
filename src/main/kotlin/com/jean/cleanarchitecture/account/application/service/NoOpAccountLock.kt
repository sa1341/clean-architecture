package com.jean.cleanarchitecture.account.application.service

import com.jean.cleanarchitecture.account.application.port.out.AccountLock
import com.jean.cleanarchitecture.account.domain.Account
import org.springframework.stereotype.Component

@Component
class NoOpAccountLock: AccountLock {

    override fun lockAccount(accountId: Account.AccountId) {
        TODO("Not yet implemented")
    }

    override fun releaseAccount(accountId: Account.AccountId) {
        TODO("Not yet implemented")
    }
}
