package com.example.tmobilechallenge;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String url = "https://private-8ce77c-tmobiletest.apiary-mock.com/test/home";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private List<ListItem> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listItems = new ArrayList();

        loadRecyclerViewData();
    }

    private void loadRecyclerViewData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Downloading data from Server...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray jsonArray = object.getJSONObject("page").getJSONArray("cards");

                            for (int i = 0; i < jsonArray.length(); ++i) {
                                JSONObject outerObject = jsonArray.getJSONObject(i);
                                String card_type = outerObject.getString("card_type");
                                ListItem listItem;

                                // One of three constructors will be called based on card_type
                                if (card_type.equals("text")) { // Title only
                                    String value = outerObject.getJSONObject("card")
                                            .getString("value");
                                    String color = outerObject.getJSONObject("card")
                                            .getJSONObject("attributes")
                                            .getString("text_color");
                                    float size = (float) outerObject.getJSONObject("card")
                                            .getJSONObject("attributes")
                                            .getJSONObject("font")
                                            .getInt("size");

                                    // text constructor
                                    listItem = new ListItem(value, color, size);
                                } else if (card_type.equals("title_description")) { // Title and description*/
                                    String tValue = outerObject.getJSONObject("card")
                                            .getJSONObject("title")
                                            .getString("value");
                                    String tColor = outerObject.getJSONObject("card")
                                            .getJSONObject("title")
                                            .getJSONObject("attributes")
                                            .getString("text_color");
                                    float tSize = outerObject.getJSONObject("card")
                                            .getJSONObject("title")
                                            .getJSONObject("attributes")
                                            .getJSONObject("font")
                                            .getInt("size");

                                    String dValue = outerObject.getJSONObject("card")
                                            .getJSONObject("description")
                                            .getString("value");
                                    String dColor = outerObject.getJSONObject("card")
                                            .getJSONObject("description")
                                            .getJSONObject("attributes")
                                            .getString("text_color");
                                    float dSize = (float) outerObject.getJSONObject("card")
                                            .getJSONObject("description")
                                            .getJSONObject("attributes")
                                            .getJSONObject("font")
                                            .getInt("size");

                                    // title_description constructor
                                    listItem = new ListItem(tValue, tColor, tSize,
                                            dValue, dColor, dSize);
                                } else { // Title, description, and image
                                    String tValue = outerObject.getJSONObject("card")
                                            .getJSONObject("title")
                                            .getString("value");
                                    String tColor = outerObject.getJSONObject("card")
                                            .getJSONObject("title")
                                            .getJSONObject("attributes")
                                            .getString("text_color");
                                    float tSize = (float) outerObject.getJSONObject("card")
                                            .getJSONObject("title")
                                            .getJSONObject("attributes")
                                            .getJSONObject("font")
                                            .getInt("size");

                                    String dValue = outerObject.getJSONObject("card")
                                            .getJSONObject("description")
                                            .getString("value");
                                    String dColor = outerObject.getJSONObject("card")
                                            .getJSONObject("description")
                                            .getJSONObject("attributes")
                                            .getString("text_color");
                                    float dSize = (float) outerObject.getJSONObject("card")
                                            .getJSONObject("description")
                                            .getJSONObject("attributes")
                                            .getJSONObject("font")
                                            .getInt("size");

                                    String url = outerObject.getJSONObject("card")
                                            .getJSONObject("image")
                                            .getString("url");
                                    int width = outerObject.getJSONObject("card")
                                            .getJSONObject("image")
                                            .getJSONObject("size")
                                            .getInt("width");
                                    int height = outerObject.getJSONObject("card")
                                            .getJSONObject("image")
                                            .getJSONObject("size")
                                            .getInt("height");

                                    // image_title_description constructor
                                    listItem = new ListItem(tValue, tColor, tSize,
                                            dValue, dColor, dSize,
                                            url, width, height);
                                }
                                listItems.add(listItem);
                            }
                            adapter = new MyAdapter(listItems, getApplicationContext()); // Initialize ViewHolders
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}