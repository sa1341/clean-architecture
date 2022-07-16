package com.jean.cleanarchitecture.account.adapter.out.persistence

import java.time.LocalDateTime
import javax.persistence.*

@Table(name = "activity")
@Entity
class ActivityJpaEntity(
    @Column val timestamp: LocalDateTime,
    @Column val ownerAccountId: Long,
    @Column val sourceAccountId: Long,
    @Column val targetAccountId: Long,
    @Column val amount: Long
) {

    @Id
    @GeneratedValue
    var id: Long? = null
}
