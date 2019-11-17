package com.example.class1
import com.example.class1.model.Crypto


import io.reactivex.Observable
import retrofit2.http.GET

interface GetData {
    // Describe the request type and the relative URL
    @GET("prices?key=6adbe2fe339d7c069093209abf0faa81")
    fun getData() : Observable<List<Crypto>>
}