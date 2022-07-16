package com.jean.cleanarchitecture.account.domain

import com.jean.cleanarchitecture.account.domain.Account.AccountId
import org.springframework.lang.NonNull
import java.time.LocalDateTime
import java.util.Collections

class ActivityWindow {

    private val activities: MutableList<Activity>

    fun getStartTimeStamp(): LocalDateTime {
        return activities.stream()
            .min(Comparator.comparing(Activity::timestamp))
            .orElseThrow{IllegalStateException()}
            .timestamp
    }

    fun getEndTimeStamp(): LocalDateTime {
        return activities.stream()
            .max(Comparator.comparing(Activity::timestamp))
            .orElseThrow{IllegalStateException()}
            .timestamp
    }

    fun calculateBalance(accountId: AccountId): Money {

        val depositBalance = activities.filter {
            it.targetAccountId == accountId
        }
            .map(Activity::money)
            .fold(Money.ZERO, Money::add)

        val withdrawalBalance = activities.filter {
            it.sourceAccountId == accountId
        }
            .map(Activity::money)
            .fold(Money.ZERO, Money::add)

        return Money.add(depositBalance, withdrawalBalance.negate())
    }

    constructor(@NonNull _activities: MutableList<Activity>) {
        this.activities = _activities
    }

    constructor(@NonNull _activities: Array<Activity>) {
        this.activities = _activities.asList().toMutableList()
    }

    fun getActivities(): List<Activity> {
        return Collections.unmodifiableList(this.activities)
    }

    fun addActivity(activity: Activity) {
        this.activities.add(activity)
    }
}
