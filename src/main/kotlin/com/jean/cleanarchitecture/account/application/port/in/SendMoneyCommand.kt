package com.jean.cleanarchitecture.account.application.port.`in`

import com.jean.cleanarchitecture.account.domain.Account.AccountId
import com.jean.cleanarchitecture.account.domain.Money
import com.jean.cleanarchitecture.common.SelfValidating
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

class SendMoneyCommand(
    @field:NotNull
    val sourceAccountId: AccountId,
    @field:NotNull
    val targetAccountId: AccountId,
    @field:Min(1)
    val money: Money
): SelfValidating<SendMoneyCommand>() {

    init {
        this.validateSelf()
    }
}
