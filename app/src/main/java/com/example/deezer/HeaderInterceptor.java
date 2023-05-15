package com.example.deezer;
import android.util.Log;

import androidx.annotation.NonNull;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeaderInterceptor implements Interceptor {

    @NonNull @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
        request = request.newBuilder()
                .addHeader("X-RapidAPI-Key", "1f41d2579cmsh5210b552b30453cp152c39jsn786c125f9af9")
                .addHeader("X-RapidAPI-Host", "deezerdevs-deezer.p.rapidapi.com")
                .build();

        Response response = chain.proceed(request);
        Log.e("TAG", "intercept: " + request.url() + "    " + request.headers() + "" + response.peekBody(1000).string());
        return response;
    }
}