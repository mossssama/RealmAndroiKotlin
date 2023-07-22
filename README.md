# RealmAndroidKotlin
Simple Documentation for Realm in AndroidKotlin along with simple demo

# **Usage**
***
[1] **Add the following to the top of build.gradle (Project)**


    buildscript {
        repositories {
            mavenCentral()
        }
        dependencies {
            classpath "io.realm:realm-gradle-plugin:10.11.1"
        }
    }
***

[2] **Add the following in plugins in build.gradle (Module)**

        id 'org.jetbrains.kotlin.kapt'
***

[3] **Add the following after plugins in build.gradle (Module)**

    apply plugin: "realm-android" 
***

[4] **Add the following to the android{ } in build.gradle (Module)**

    realm {
        syncEnabled = true 
    }
***

[5] **Put [RealmManager class](https://github.com/mossssama/RealmAndroidKotlin/blob/main/app/src/main/java/com/example/realmdemo/realm/RealmManager.kt)**
 & **[RealmObjectCopyable interface](https://github.com/mossssama/RealmAndroidKotlin/blob/main/app/src/main/java/com/example/realmdemo/realm/RealmObjectCopyable.kt) in your app folder**

***

[6] **Create Pojo class to represent an object in Realm like the following [Person class](https://github.com/mossssama/RealmAndroidKotlin/blob/main/app/src/main/java/com/example/realmdemo/realmPojo/Person.kt) in your app folder**

***
[7] **Use the following in the place you want to use Realm**; substitute the i/p parameters by their equivalent in your project

       /* Create an instance for every Object*/
       val realmPerson: RealmManager<Person> = RealmManager.getInstance(this, Person::class.java)
       // val realmBook: RealmManager<Book> = RealmManager.getInstance(this,Book::class.java)        & so on

        /* [C] Creating object in Realm */
        realmPerson.write(Person(name="LeoMe", age=90))

        /* [U] Updating object in Realm */
        realmPerson.update("name", "LeoMes", Person(name="LeoMes", age=123))

        /* [R] Reading object from Realm */
        val person = realmPerson.read("name", "LeoMe")
        person?.let { Log.i("TAG", it.age.toString()) }

        /* [D] Deleting object from Realm */
        realmPerson.delete("name", "LeoMe")
        if (person == null) Log.i("TAG", "Deletion")
- - - - 
