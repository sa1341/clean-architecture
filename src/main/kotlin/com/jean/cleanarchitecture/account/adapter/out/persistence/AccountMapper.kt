package com.jean.cleanarchitecture.account.adapter.out.persistence

import com.jean.cleanarchitecture.account.domain.Account
import com.jean.cleanarchitecture.account.domain.Account.AccountId
import com.jean.cleanarchitecture.account.domain.Activity
import com.jean.cleanarchitecture.account.domain.ActivityWindow
import com.jean.cleanarchitecture.account.domain.Money
import org.springframework.stereotype.Component

@Component
class AccountMapper {

    fun mapToDomainEntity(
        account: AccountJpaEntity,
        activities: List<ActivityJpaEntity>,
        withdrawalBalance: Long?,
        depositBalance: Long?
    ): Account {
        val baselineBalance = Money.subtract(
            Money.of(depositBalance!!),
            Money.of(withdrawalBalance!!)
        )
        return Account.withId(
            AccountId(account.id!!),
            baselineBalance,
            mapToActivityWindow(activities)!!
        )
    }

    fun mapToActivityWindow(activities: List<ActivityJpaEntity>): ActivityWindow? {

        val mappedActivities: MutableList<Activity> = ArrayList<Activity>()

        for (activity in activities) {
            mappedActivities.add(
                Activity(
                    AccountId(activity.ownerAccountId),
                    AccountId(activity.sourceAccountId),
                    AccountId(activity.targetAccountId),
                    activity.timestamp,
                    Money.of(activity.amount)
                )
            )
        }

        return ActivityWindow(mappedActivities)
    }

    fun mapToJpaEntity(activity: Activity): ActivityJpaEntity? {
        return ActivityJpaEntity(
            activity.timestamp,
            activity.ownerAccountId.value,
            activity.sourceAccountId.value,
            activity.targetAccountId.value,
            activity.money.amount.longValueExact()
        )
    }
}
