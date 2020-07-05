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
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONObject("page").getJSONArray("cards");
                            System.out.print(response);

                            for (int i = 0; i < jsonArray.length(); ++i) {
                                JSONObject outerObject = jsonArray.getJSONObject(i);
                                String card_type = outerObject.getString("card_type");
                                JSONObject cardObject = outerObject.getJSONObject("card");

                                ListItem listItem;

                                // One of three constructors below will be called per array listItem
                                if (card_type.equals("text")) { // Title only
                                    String value = cardObject.getString("value");
                                    JSONObject attributesObject = cardObject.getJSONObject("attributes");
                                    String color = attributesObject.getString("text_color");
                                    JSONObject fontObject = attributesObject.getJSONObject("font");
                                    float size = (float) fontObject.getInt("size");

                                    listItem = new ListItem(value, color, size);
                                } else if (card_type.equals("title_description")) { // Title and description
                                    JSONObject titleObject = cardObject.getJSONObject("title");
                                    String tValue = titleObject.getString("value");
                                    JSONObject attributesObject = titleObject.getJSONObject("attributes");
                                    String tColor = attributesObject.getString("text_color");
                                    JSONObject fontObject = attributesObject.getJSONObject("font");
                                    float tSize = fontObject.getInt("size");

                                    JSONObject descriptionObject = cardObject.getJSONObject("description");
                                    String dValue = descriptionObject.getString("value");
                                    JSONObject dAttributesObject = descriptionObject.getJSONObject("attributes");
                                    String dColor = dAttributesObject.getString("text_color");
                                    JSONObject dFontObject = dAttributesObject.getJSONObject("font");
                                    float dSize = (float) dFontObject.getInt("size");

                                    listItem = new ListItem(tValue, tColor, tSize,
                                            dValue, dColor, dSize);
                                } else { // Title, description, and image
                                    JSONObject titleObject = cardObject.getJSONObject("title");
                                    String tValue = titleObject.getString("value");
                                    JSONObject attributesObject = titleObject.getJSONObject("attributes");
                                    String tColor = attributesObject.getString("text_color");
                                    JSONObject fontObject = attributesObject.getJSONObject("font");
                                    float tSize = (float) fontObject.getInt("size");

                                    JSONObject descriptionObject = cardObject.getJSONObject("description");
                                    String dValue = descriptionObject.getString("value");
                                    JSONObject dAttributesObject = descriptionObject.getJSONObject("attributes");
                                    String dColor = dAttributesObject.getString("text_color");
                                    JSONObject dFontObject = dAttributesObject.getJSONObject("font");
                                    float dSize = (float) dFontObject.getInt("size");

                                    JSONObject imageObject = cardObject.getJSONObject("image");
                                    String url = imageObject.getString("url");
                                    JSONObject sizeObject = imageObject.getJSONObject("size");
                                    int width = sizeObject.getInt("width");
                                    int height = sizeObject.getInt("height");

                                    listItem = new ListItem(tValue, tColor, tSize,
                                            dValue, dColor, dSize,
                                            url, width, height);
                                }
                                listItems.add(listItem);
                            }
                            adapter = new MyAdapter(listItems, getApplicationContext());
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