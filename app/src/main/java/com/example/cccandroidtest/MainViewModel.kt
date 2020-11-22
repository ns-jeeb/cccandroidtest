package com.example.cccandroidtest

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.Room
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.concurrent.TimeUnit


class MainViewModel() : ViewModel() {

    private var estimateLiveData = MutableLiveData<Estimate>()
    private var personLive = MutableLiveData<Person>()
    lateinit var db: AppDatabase

    fun getEstimateData(context: Context): MutableLiveData<Estimate> {
        context.deleteDatabase("database_name")
        db = Room.databaseBuilder(context, AppDatabase::class.java, "database_name").build()

        var observable = PublishSubject.create<Int>()
        observable.toFlowable(BackpressureStrategy.MISSING).onBackpressureBuffer()
            .observeOn(Schedulers.computation())
            .subscribeBy (
                onNext ={
                    db.clearAllTables()
                    db.estimateDao().insertAll(
                        Estimate(
                            "c574b0b4-bdef-4b92-a8f0-608a86ccf5ab",
                            "Placebo Secondary School",
                            "32 Commissioners Road East",
                            32,
                            3,
                            "2020-08-22 15:23:54",
                            "85a57f85-a52d-4137-a0d1-62e61362f716",
                            "85a57f85-a52d-4137-a0d1-62e61362f716",
                            "85a57f85-a52d-4137-a0d1-62e61362f716"))


                    estimateLiveData.postValue(db.estimateDao().loadEstimates())

                },onError = {t->
                    print(t.message)
                }
            )
        observable.onNext(1)

       return estimateLiveData
    }
    fun getPersonData(context: Context):  MutableLiveData<Person> {
        db = Room.databaseBuilder(context, AppDatabase::class.java, "database_name").build()
        var observable = PublishSubject.create<Int>()
        observable.toFlowable(BackpressureStrategy.MISSING).onBackpressureBuffer()
            .observeOn(Schedulers.computation())
            .subscribeBy (
                onNext ={
                    db.clearAllTables()
                    db.personDao().insertAllPersons(
                        Person(
                            "85a57f85-a52d-4137-a0d1-62e61362f716",
                            "Joseph",
                            "Sham",
                            "joseph.sham@fake.fake",
                            "123-456-7890"
                        )
                    )
                    personLive.postValue(db.personDao().loadPersons())

                },onError = {t->
                    print(t.message)
                }
            )
        observable.onNext(1)
        return personLive
    }
}
