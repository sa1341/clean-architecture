package com.jean.cleanarchitecture.account.domain

import com.jean.cleanarchitecture.account.domain.Account.AccountId
import java.time.LocalDateTime
import javax.validation.constraints.NotNull

class Activity(
    @NotNull val ownerAccountId: AccountId,
    @NotNull val sourceAccountId: AccountId,
    @NotNull val targetAccountId: AccountId,
    @NotNull val timestamp: LocalDateTime,
    @NotNull val money: Money
) {

    var id: ActivityId? = null

    class ActivityId(val value: Long)
}
