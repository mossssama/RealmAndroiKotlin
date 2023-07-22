package com.example.realmkotlindemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.realmkotlindemo.realm.RealmManager
import com.example.realmkotlindemo.realmPojo.Person

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val realmPerson: RealmManager<Person> = RealmManager.getInstance(this, Person::class.java)

        /* [C] Creating object in Realm */
        val oldPerson = Person(name="LeoMe", age=90)
        realmPerson.write(oldPerson)

        /* [U] Updating object in Realm */
        realmPerson.update("name", "LeoMes", Person(name="LeoMes", age=123))

        /* [R] Reading object from Realm */
        val person = realmPerson.read("name", "LeoMe")
        person?.let { Log.i("TAG", it.age.toString()) }

        /* [D] Deleting object from Realm */
        realmPerson.delete("name", "LeoMe")
        if (person == null) Log.i("TAG", "Deletion")


    }
}