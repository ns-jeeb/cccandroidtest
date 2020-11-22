package com.example.cccandroidtest

import androidx.room.*
import io.reactivex.rxjava3.core.Flowable


@Entity(tableName = "estimate_tbl")
data class Estimate(
    @PrimaryKey var id: String,
    @ColumnInfo(name = "company") val company: String?,
    @ColumnInfo(name = "address") val address: String?,
    @ColumnInfo(name = "number") val number: Int?,
    @ColumnInfo(name = "revision_number") val revision_number: Int?,
    @ColumnInfo(name = "created_date") val created_date: String?,
    @ColumnInfo(name = "created_by") val created_by: String?,
    @ColumnInfo(name = "requested_by") val requested_by: String?,
    @ColumnInfo(name = "contacted") val contact: String?
)
@Entity(tableName = "person_tbl")
data class Person(
    @PrimaryKey var id: String,
    @ColumnInfo(name = "first_name") var first_name: String?,
    @ColumnInfo(name = "last_name") var last_name: String?,
    @ColumnInfo(name = "email") var email: String?,
    @ColumnInfo(name = "phone_number") var phone_number: String?

)
@Database(entities = arrayOf( Estimate::class, Person::class), version = 2)
@TypeConverters()
abstract class AppDatabase : RoomDatabase() {

    abstract fun estimateDao(): EstimateDao
    abstract fun personDao(): PersonDao
}

@Dao
interface EstimateDao{
    @Insert
    fun insertAll(vararg estimate: Estimate)
    @Query("SELECT * FROM estimate_tbl")
    fun loadEstimates(): Estimate

    @Query("SELECT * FROM estimate_tbl WHERE created_by LIKE :created AND " +
            "requested_by LIKE :requested" )
    fun findByName(created: String, requested: String): Estimate

}
@Dao
interface PersonDao{
    @Insert
    fun insertAllPersons(vararg person: Person)
    @Query("SELECT * FROM person_tbl")
    fun loadPersons(): Person
}



