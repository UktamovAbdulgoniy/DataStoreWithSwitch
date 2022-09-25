package com.example.datastorepref

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.datastorepref.databinding.ActivityMainBinding
import com.example.datastorepref.manager.DataStorePref
import com.example.datastorepref.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var dataStorePref: DataStorePref
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        dataStorePref = DataStorePref(this)

        binding.btnSave.setOnClickListener {
            val name = binding.editName.text.toString().trim()
            val age = binding.editAge.text.toString().trim()
            val gender = binding.editGender.text.toString().trim()

            GlobalScope.launch(Dispatchers.IO) {
                dataStorePref.saveUser(
                    user = User(
                        name = name,
                        age = age,
                        gender = gender.toBoolean()
                    )
                )
            }
        }
        binding.btnGet.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO){
                dataStorePref.getUser().catch {
                    it.printStackTrace()
                }.collect{
                    withContext(Dispatchers.Main){
                        binding.textName.text = it.name
                        binding.textAge.text = it.age
                        if (!it.gender){
                            binding.textGender.text = "Male"
                        }else{
                            binding.textGender.text = "Female"
                        }
                    }
                }
            }
        }

    }
}