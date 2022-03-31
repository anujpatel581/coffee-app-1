package com.anujpatel.coffee_api;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    ImageView coffeeImg;
    Button nextButton , shareButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        coffeeImg = findViewById(R.id.coffeeImg);
        nextButton = findViewById(R.id.nextButton);
        shareButton = findViewById(R.id.shareButton);

        getSupportActionBar().hide();

        loadCoffee();


    }

    private void loadCoffee(){


        String url = "https://coffee.alexflipnote.dev/random.json";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                       // textView.setText("Response: " + response.toString());

                        try {
                            String url = response.getString("file");

                            Glide.with(MainActivity.this).load(url).into(coffeeImg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                        Toast.makeText(MainActivity.this,"Something is wrong ! ",Toast.LENGTH_SHORT).show();

                    }
                });

// Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);


    }

    public void nextButton(View view) {

        loadCoffee();

    }

    public void shareButton(View view) {

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "wow cool coffee !");

        startActivity(intent);
    }
}