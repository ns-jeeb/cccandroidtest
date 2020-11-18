package com.example.cccandroidtest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.room.Room
import com.example.cccandroidtest.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {
    var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        runBlocking {
            val job = launch (Dispatchers.Default){
                try {
                    val db = Room.databaseBuilder(
                            applicationContext,
                            AppDatabase::class.java, "database_name"
                    ).build()

                    if (db.estemateDao().loadEstemateByIds("c574b0b4-bdef-4b92-a8f0-608a86ccf5ab").id.isEmpty()) {
                        db.estemateDao().insertAll(Estimate(
                            "c574b0b4-bdef-4b92-a8f0-608a86ccf5ab",
                            "Placebo Secondary School",
                            "32 Commissioners Road East",
                            32,
                            3,
                            "2020-08-22 15:23:54",
                            "85a57f85-a52d-4137-a0d1-62e61362f716",
                            "85a57f85-a52d-4137-a0d1-62e61362f716",
                            "85a57f85-a52d-4137-a0d1-62e61362f716"
                        ))
                    }

                    var id = db.estemateDao().findByName("85a57f85-a52d-4137-a0d1-62e61362f716",
                            "85a57f85-a52d-4137-a0d1-62e61362f716" )
                    binding?.txtId?.text = id.company
                }catch (e:InterruptedException){
                }
            }

        }
    }
}
