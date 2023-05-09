package com.example.deezer

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import okhttp3.OkHttpClient

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    private lateinit var rvmain: RecyclerView
    private lateinit var myAdapter: MyAdapter
    private var searchItems: MutableList<SearchItem> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvmain = findViewById(R.id.recycler_view)
        myAdapter = MyAdapter(searchItems)
        rvmain.layoutManager = LinearLayoutManager(this)
        rvmain.adapter = myAdapter

        performSearch("alesso")
    }

    private fun createService(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HeaderInterceptor())
            .build()
    }

    private fun performSearch(query: String) {
        val apiInterface = Retrofit.Builder()
            .baseUrl("https://deezerdevs-deezer.p.rapidapi.com/")
            .addConverterFactory(GsonConverterFactory.create()).client(createService())
            .build()
            .create(ApiInterface::class.java)

        val token="1f41d2579cmsh5210b552b30453cp152c39jsn786c125f9af9"

        val call = apiInterface.search(query)
        call.enqueue(object : Callback<SearchResult> {
            override fun onResponse(call: Call<SearchResult>, response: Response<SearchResult>) {
                if (response.isSuccessful && response.body() != null) {
                    searchItems.clear()
                    searchItems.addAll(response.body()!!.data)
                    myAdapter.notifyDataSetChanged()
                } else {
                    // Handle error response
                }
            }

            override fun onFailure(call: Call<SearchResult>, t: Throwable) {
                Log.e("TAG", "onFailure: "+t.message.toString())
                // handle network or API error
            }
        })
    }

}
