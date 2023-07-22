package com.example.realmkotlindemo.realm

import android.content.Context
import io.realm.Realm
import io.realm.RealmObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RealmManager<T>(private val realmClass: Class<T>) where T : RealmObject, T : RealmObjectCopyable<T> {

    private val realm: Realm = Realm.getDefaultInstance()

    companion object {
        private var instance: RealmManager<*>? = null
        private val LOCK = Any()

        @JvmStatic
        fun <T> getInstance(context: Context, realmClass: Class<T>): RealmManager<T> where T : RealmObject, T : RealmObjectCopyable<T> {
            synchronized(LOCK) {
                if (instance == null) {
                    Realm.init(context.applicationContext) // Initialize Realm
                    instance = RealmManager(realmClass) // Create the RealmManager instance
                } else if (instance!!.realmClass != realmClass) {
                    throw IllegalStateException("RealmManager instance is already used with a different RealmObject class.")
                }
            }
            return instance as RealmManager<T>
        }
    }

    fun getRealm(): Realm = realm

    private suspend fun <T> withBackgroundRealm(block: suspend (Realm) -> T): T {
        return withContext(Dispatchers.IO) {
            val backgroundRealm = Realm.getDefaultInstance()
            val result = block(backgroundRealm)
            backgroundRealm.close()
            result
        }
    }
fun write(objectToWrite: T) { realm.executeTransactionAsync { realm -> realm.copyToRealm(objectToWrite) } }

fun read(fieldName: String, fieldValue: String): T? = realm.where(realmClass).equalTo(fieldName, fieldValue).findFirst()

fun update(fieldName: String, fieldValue: String, objectToUpdate: T) {
    realm.executeTransactionAsync { realm ->
        val obj = realm.where(realmClass).equalTo(fieldName, fieldValue).findFirst()
        obj?.let { objectToUpdate.copyToRealmObject(it) }
    }
}

fun delete(fieldName: String, fieldValue: String) {
    realm.executeTransactionAsync { realm ->
        val results = realm.where(realmClass).equalTo(fieldName, fieldValue).findAll()
        results.deleteAllFromRealm()
    }
}

fun clearRealm() { realm.executeTransactionAsync { realm -> realm.delete(realmClass) } }
}
