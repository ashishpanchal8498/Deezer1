package com.example.deezer

import android.content.ContentValues.TAG
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.deezer.reopository.DeezerRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var rvmain: RecyclerView
    private lateinit var myAdapter: MyAdapter
    private lateinit var deezerViewModel: DeezerViewModel
    private var viewModelFactory: DeezerViewModelFactory? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvmain = findViewById(R.id.recycler_view)
        myAdapter = MyAdapter(object : DiffUtil.ItemCallback<SearchItem>() {
            override fun areItemsTheSame(oldItem: SearchItem, newItem: SearchItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: SearchItem, newItem: SearchItem): Boolean {
                return oldItem == newItem
            }
        })
        rvmain.layoutManager = LinearLayoutManager(this)
        rvmain.adapter = myAdapter

        viewModelFactory = DeezerViewModelFactory(DeezerRepository(getAPIInterFace()))

        deezerViewModel = ViewModelProvider(
            this, viewModelFactory ?: DeezerViewModelFactory(DeezerRepository(getAPIInterFace()))
        )[DeezerViewModel::class.java]

        search("A")
        val searchView = findViewById<SearchView>(R.id.searchView)
        val editText = searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
        editText.setTextColor(Color.WHITE)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchView.clearFocus()
                search(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }

    private fun search(msg: String) {
        lifecycleScope.launch {
            try {

                deezerViewModel.returnList(msg).collectLatest {
                    Log.e(TAG, "loadList: $myAdapter")
                    myAdapter.submitData(it)
                }
            } catch (e: Exception) {
                Log.e("TAG", "setupActivity: ${e.message}")
            }
        }
    }

    private fun createService(): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(HeaderInterceptor()).build()
    }

    private fun getAPIInterFace(): ApiInterface {
        val retrofit = Retrofit.Builder().baseUrl("https://deezerdevs-deezer.p.rapidapi.com/")
            .addConverterFactory(GsonConverterFactory.create()).client(createService()).build()
        return retrofit.create(ApiInterface::class.java)
    }
}

