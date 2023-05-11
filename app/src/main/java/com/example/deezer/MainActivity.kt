package com.example.deezer

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.widget.SearchView
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
        myAdapter = MyAdapter(this, searchItems)
        rvmain.layoutManager = LinearLayoutManager(this)
        rvmain.adapter = myAdapter

        performSearch("arijit")

    val searchView = findViewById<SearchView>(R.id.searchView)
        val editText = searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
        editText.setTextColor(Color.WHITE)
    searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String): Boolean {
            searchView.clearFocus()
            performSearch(query)
            return false
        }

        override fun onQueryTextChange(newText: String): Boolean {
            return false
        }

    })
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

        val call = apiInterface.search(query)
        call.enqueue(object : Callback<SearchResult> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<SearchResult>, response: Response<SearchResult>) {
                if (response.isSuccessful && response.body() != null) {
                    searchItems.clear()
                    response.body()?.data?.let {
                        searchItems.addAll(it)
                        myAdapter.notifyDataSetChanged()
                    }
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
