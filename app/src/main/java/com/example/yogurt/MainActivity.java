package com.example.yogurt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    // 路徑宣告-------------------------------------------------------------------------------------
    String post_data = GlobalVariable.urlstart+"/yogurt";
    String get_response = GlobalVariable.urlstart+"/response";
    String pay;

    EditText name, phone,address,
            bonus,
            yogurt1,yogurt2,yogurt3,yogurt4,
            jam1,jam2,jam3,
            oatmeal,scone,
            message;
    //spinner 宣告套件 ------------------------------------------------------
    Spinner spin1;
    private String[] list = {"ATM轉帳","貨到付款(酌收手續費)"}; //喧告字串陣列
    private ArrayAdapter<String> listAdapter; //喧告listAdapter物件

    Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //基本資料----------------------------------------------
        name = findViewById(R.id.editTextTextPersonName);
        phone = findViewById(R.id.editTextNumber);
        address = findViewById(R.id.editTextTextPersonName4);

        spin1 = (Spinner) findViewById(R.id.spinner);
        listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        spin1.setAdapter(listAdapter);
        //優格類別----------------------------------------------
        bonus = findViewById(R.id.editTextNumber2);
        yogurt1 = findViewById(R.id.editTextNumber3);
        yogurt2 = findViewById(R.id.editTextNumber4);
        yogurt3 = findViewById(R.id.editTextNumber5);
        yogurt4 = findViewById(R.id.editTextNumber6);

        //果醬類別----------------------------------------------
        jam1 = findViewById(R.id.editTextNumber7);
        jam2 = findViewById(R.id.editTextNumber8);
        jam3 = findViewById(R.id.editTextNumber9);

        //其他類別----------------------------------------------
        oatmeal = findViewById(R.id.editTextNumber10);
        scone = findViewById(R.id.editTextNumber11);
        message = findViewById(R.id.editTextTextPersonName5);
        send = findViewById(R.id.button);

        spin1.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onNothingSelected(AdapterView<?> parent) { }
            public void onItemSelected(AdapterView adapterView, View view, int position, long id){
                pay  = adapterView.getSelectedItem().toString();
                Log.d("Choice", "" + pay);
            }});

    }

    public void send1(View view) {
        Log.d("POST Data~~",post_data);
        //Node.js 連結---------------------------------------------------------------------->>
        HashMap data = new HashMap();
        // 要轉為String ~~
        data.put("name", name.getText().toString());
        data.put("phone", phone.getText().toString());
        data.put("address", address.getText().toString());
        // 付款方式------------------------------>>
        data.put("pay", pay);

        data.put("bonus", bonus.getText().toString());
        data.put("yogurt9n", yogurt1.getText().toString());
        data.put("yogurt9y", yogurt2.getText().toString());
        data.put("yogurt5n", yogurt3.getText().toString());
        data.put("yogurt5y", yogurt4.getText().toString());

        data.put("blueberry", jam1.getText().toString());
        data.put("pineapple_mango", jam2.getText().toString());
        data.put("pineapple", jam3.getText().toString());

        data.put("oatmeal", oatmeal.getText().toString());
        data.put("scon", scone.getText().toString());
        data.put("comment", message.getText().toString());

        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.POST, post_data, new JSONObject(data), new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) { }}, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) { error.printStackTrace(); }
                });
        Volley.newRequestQueue(MainActivity.this).add(jsonRequest);

        // delay 1 second------------------------------------------------------------------------->>
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //GET manhole_tube_get data ---------------------------------------------------------->>
        Log.d("GET Data~~",get_response);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, get_response,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Result handling
                        Log.d("Right !!",response);
                        Toast.makeText(MainActivity.this, response,  Toast.LENGTH_LONG).show();

                    }}, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Error handling
                Log.d("Error !!","compares went wrong!");
                Toast.makeText(MainActivity.this, "上傳失敗!!",  Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }});
        Volley.newRequestQueue(MainActivity.this).add(stringRequest);

    }
}