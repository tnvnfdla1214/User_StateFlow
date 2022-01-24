package com.example.user_stateflow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var userAdapter:UserAdapter
    private val userViewModel:UserViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        observe()
        userViewModel.getAllUser()
        initRecyclerView()

        save.setOnClickListener{
            setUserData()
        }
    }
    fun observe(){
        lifecycleScope.launch {
            userViewModel.expense.collect { event->
                when(event) {
                    is State.Loading -> {

                    }
                    is State.Success -> {
                        userAdapter.setUser(event.List as ArrayList<User>)
                    }
                    is State.Failure -> {

                    }
                    else -> {

                    }
                }
            }
        }
    }

    private fun setUserData() {
        val getName = name.text.toString().trim()
        val getAge = age.text.toString().trim()
        if(!TextUtils.isEmpty(getName) && !TextUtils.isEmpty(getAge))
        {
            val user=User(getName,getAge.toInt())
            userViewModel.insertUser(user)
        }
        else{
            Toast.makeText(applicationContext,"Please fill all the fields", Toast.LENGTH_SHORT).show()
        }

    }

    private fun initRecyclerView() {
        recyclerView=findViewById(R.id.recyclerView)
        userAdapter= UserAdapter(this, ArrayList())
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager= LinearLayoutManager(this@MainActivity)
            adapter=userAdapter
        }
    }
}