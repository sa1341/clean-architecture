package com.jean.cleanarchitecture.account.application.port.out

import com.jean.cleanarchitecture.account.domain.Account

interface UpdateAccountStatePort {
    fun updateActivities(account: Account)
}
