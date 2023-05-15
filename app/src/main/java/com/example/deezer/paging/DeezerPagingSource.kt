package com.example.deezer.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.deezer.ApiInterface
import com.example.deezer.SearchItem

class DeezerPagingSource(private val apiInterface: ApiInterface,val string: String) : PagingSource<Int, SearchItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchItem> {
        return try {
            val position = params.key ?: 0
            val response = apiInterface.search(string, position)

            val next = if (response.next==null){null}else{position+25}
            return LoadResult.Page(
                data = response.data,
                prevKey = null,
                nextKey = next,
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
    override fun getRefreshKey(state: PagingState<Int, SearchItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}