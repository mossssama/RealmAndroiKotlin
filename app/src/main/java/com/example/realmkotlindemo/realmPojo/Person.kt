package com.example.realmkotlindemo.realmPojo

import com.example.realmkotlindemo.realm.RealmObjectCopyable
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class Person(@PrimaryKey var id: String = UUID.randomUUID().toString(), var name: String = "", var age: Int = 0) : RealmObject(),
    RealmObjectCopyable<Person> {
    constructor() : this("", "", 0)
    override fun copyToRealmObject(destination: Person) {
        destination.name = this.name
        destination.age = this.age
    }
}
