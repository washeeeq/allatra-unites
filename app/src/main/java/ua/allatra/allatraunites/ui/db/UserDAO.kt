package ua.allatra.allatraunites.ui.db

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class UserDAO: RealmObject() {
    @PrimaryKey
    private var id: Long = 1
    private lateinit var name: String
    private lateinit var country: String
    private lateinit var email: String
    private lateinit var language: String

    fun getId(): Long = id
    fun getName(): String = name
    fun getLanguage(): String = language
    fun getCountry(): String = country
    fun getEmail(): String = email

    fun setId(id: Long){
        this.id = id
    }

    fun setName(name: String){
        this.name = name
    }

    fun email(email: String){
        this.email = email
    }

    fun setLanguage(language: String){
        this.language = language
    }

    fun setCountry(country: String){
        this.country = country
    }
}