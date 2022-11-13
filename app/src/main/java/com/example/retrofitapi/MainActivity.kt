package com.example.retrofitapi

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitapi.databinding.ActivityMainBinding
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import androidx.recyclerview.widget.LinearLayoutManager as LinearLayoutManager1

const val BASE_URL ="https://jsonplaceholder.typicode.com/"
class MainActivity : AppCompatActivity() {

     lateinit var adapter: MyAdapter
     lateinit var linearLayoutManager: LinearLayoutManager1



    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rcView.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager1(/* context = */ this)
        binding.rcView.layoutManager=linearLayoutManager
        getMyData()
    }



    private fun getMyData() {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retrofitBuilder.getData()

        retrofitData.enqueue(object : Callback<List<MyDataItem>?> {
            override fun onResponse(
                call: Call<List<MyDataItem>?>,
                response: Response<List<MyDataItem>?>,
            ) {

            val responseBody = response.body()!!

                var myAdapter = MyAdapter(baseContext, responseBody)
                myAdapter.notifyDataSetChanged()
            binding.rcView.adapter=myAdapter
            }

            override fun onFailure(call: Call<List<MyDataItem>?>, t: Throwable) {
              Log.d("MainActivity", "onFailure: " + t.message)
            }
        })
    }
}