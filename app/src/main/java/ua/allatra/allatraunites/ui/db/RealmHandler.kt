package ua.allatra.allatraunites.ui.db

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmObject
import java.io.ByteArrayOutputStream

class RealmHandler() {
    private lateinit var realm: Realm
    private lateinit var context: Context

    companion object {
        private const val TAG = "DB"
        private const val APP_REAL_NAME = "realm.allatra.unites"
        const val DEFAULT_ID = 1L
    }

    constructor(context: Context) : this() {
        this.context = context
        initRealm()
    }

    // Initialize Realm
    private fun initRealm(){
        Realm.init(context)
        val config = RealmConfiguration.Builder()
            .name(APP_REAL_NAME)
            .schemaVersion(3)
            .deleteRealmIfMigrationNeeded()
            .build()
        realm = Realm.getInstance(config)
    }

    private fun dropRealm(realmConfiguration: RealmConfiguration){
        Realm.deleteRealm(realmConfiguration)
    }

    private fun deleteAllRecords(){
        realm.beginTransaction()
        realm.deleteAll()
        realm.commitTransaction()
    }

    fun getUserDAO(id: Long): UserDAO?{
        return realm.where<UserDAO>(UserDAO::class.java).equalTo("id", id).findFirst()
    }

    fun createUserDAO(language: String){
        var userDAO = UserDAO()
        var lastId = realm.where<UserDAO>(UserDAO::class.java).max("id")

        lastId?.let {
            val newId = lastId.toInt() + 1
            userDAO.setId(newId.toLong())
        }?: kotlin.run {
            userDAO.setId(DEFAULT_ID)
        }

        userDAO.setLanguage(language)

        // Commit
        realm.beginTransaction()
        realm.copyToRealmOrUpdate(userDAO)
        realm.commitTransaction()
    }

    private fun convertBitmapToByteArray(drawable: BitmapDrawable): ByteArray? {
        val stream = ByteArrayOutputStream()
        drawable.bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        return stream.toByteArray()
    }

    private fun commitRecord(realmObj: RealmObject){
        // Commit
        realm.beginTransaction()
        realm.copyToRealmOrUpdate(realmObj)
        realm.commitTransaction()
    }
}