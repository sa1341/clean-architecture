package com.jean.cleanarchitecture.account.adapter.out.persistence

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Table(name = "account")
@Entity
class AccountJpaEntity {

    @Id
    @GeneratedValue
    var id: Long? = null
}
