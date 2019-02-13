package com.example.lucas_abreu.mainactivity;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PhraseClient {

    @GET("/frase")
    Call<Phrase> getRandomRhrase();
}
