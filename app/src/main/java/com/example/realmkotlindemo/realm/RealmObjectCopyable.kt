package com.example.realmkotlindemo.realm

import io.realm.RealmObject

interface RealmObjectCopyable<T : RealmObject> {
    fun copyToRealmObject(destination: T)
}
