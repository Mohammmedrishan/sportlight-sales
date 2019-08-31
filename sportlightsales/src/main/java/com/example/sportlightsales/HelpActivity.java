package com.example.sportlightsales;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class HelpActivity extends AppCompatActivity implements SqlDelegate {

    ListView helpListView;
    String[] headList;
    ArrayAdapter<String> helpArrayAdapter;
    List<HelpModel> listHelp;
    List<FaqModel> listFaq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        getSupportActionBar().setTitle("Help");

        listHelp=new ArrayList<>();
        listFaq=new ArrayList<FaqModel>();
        helpListView = findViewById(R.id.listHelp);

        getUserDetails();
        helpListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), Helpdiscription.class);
                if(listHelp.get(position).getProperty().equals("faq")) {
                    intent.putExtra("faq_values", (Serializable) listFaq);
                    intent.putExtra("value", listHelp.get(position).getValue());
                    intent.putExtra("name", listHelp.get(position).getName());
                    intent.putExtra("property", listHelp.get(position).getProperty());
                }else {
                    intent.putExtra("value", listHelp.get(position).getValue());
                    intent.putExtra("name", listHelp.get(position).getName());
                    intent.putExtra("property", listHelp.get(position).getProperty());
                }
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResponse(SqlHelper sqlHelper) {
//        Toast.makeText(this, "Works", Toast.LENGTH_SHORT).show();
        try {
            JSONArray jsonArray = sqlHelper.getJSONResponse().getJSONArray("response");
            if (sqlHelper.getActionString().equals("fetchData")) {
//                JSONArray jsonArray = sqlHelper.getJSONResponse().getJSONArray("response");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    HelpModel helpModel = new HelpModel();
                    helpModel.setId(jsonObject.getInt("id"));
                    helpModel.setName(jsonObject.getString("name"));
                    helpModel.setProperty(jsonObject.getString("property"));
                    helpModel.setValue(jsonObject.getString("value"));
                    listHelp.add(helpModel);
                }
                if (listHelp.size() > 0) {
                    headList = new String[listHelp.size()];
                    for (int i = 0; i < listHelp.size(); i++) {
                        headList[i] = listHelp.get(i).getName();
                    }
                    helpArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, headList);
                    helpListView.setAdapter(helpArrayAdapter);
                    getFaqDetails();
                }
            } else if (sqlHelper.getActionString().equals("fetchFaq")) {
//                JSONArray jsonArray = sqlHelper.getJSONResponse().getJSONArray("response");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    FaqModel faqModel = new FaqModel();
                    faqModel.setFaqId(jsonObject.getInt("id"));
                    faqModel.setFaqName(jsonObject.getString("name"));
                    faqModel.setFaqType(jsonObject.getInt("type"));
                    faqModel.setFaqParentId(jsonObject.getInt("parent_id"));
                    faqModel.setFaqValue(jsonObject.getString("value"));
                    listFaq.add(faqModel);
                }
            }
        }
         catch (JSONException e) {
             Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
         }
    }
    public void getUserDetails() {
        SqlHelper sqlHelper = new SqlHelper(HelpActivity.this, HelpActivity.this);
        sqlHelper.setExecutePath("fetchData.php");
        sqlHelper.setActionString("fetchData");
        ContentValues params = new ContentValues();
        sqlHelper.setMethod(getString(R.string.method_get));
        sqlHelper.setParams(params);
        sqlHelper.executeUrl(true);
    }
    public void getFaqDetails(){
        SqlHelper sqlHelper = new SqlHelper(HelpActivity.this, HelpActivity.this);
        sqlHelper.setExecutePath("fetchFaq.php");
        sqlHelper.setActionString("fetchFaq");
        ContentValues params = new ContentValues();
        sqlHelper.setMethod(getString(R.string.method_get));
        sqlHelper.setParams(params);
        sqlHelper.executeUrl(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.help_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.preferences) {
//          Intent intent = new Intent(this,Helpdiscription.class);
//          startActivity(intent);
            Toast.makeText(this, "Add Sales", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}