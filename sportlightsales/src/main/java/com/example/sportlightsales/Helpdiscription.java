package com.example.sportlightsales;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Helpdiscription extends AppCompatActivity {

    TextView head1TextView,sub1TextView,head2TextView,sub2TextView,helpTextView;
    ExpandableListView expandableListView;
    List<String> listGroup;
    HashMap<String,List<String>> listItem;
    FaqAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helpdiscription);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        helpTextView = findViewById(R.id.helpText);
        head1TextView = findViewById(R.id.head1Text);
        head2TextView = findViewById(R.id.head2Text);
        sub1TextView = findViewById(R.id.sub1Text);
        sub2TextView = findViewById(R.id.sub2Text);
        expandableListView = findViewById(R.id.expandableListView);
        listGroup = new ArrayList<>();
        listItem = new HashMap<>();
        adapter = new FaqAdapter(this,listGroup,listItem);
        expandableListView.setAdapter(adapter);

        String value = getIntent().getStringExtra("value");
        String name = getIntent().getStringExtra("name");
        String property = getIntent().getStringExtra("property");
        String faqName = getIntent().getStringExtra("name");
        String faqType = getIntent().getStringExtra("type");
        String faqParentId = getIntent().getStringExtra("parent_id");
        String faqValue = getIntent().getStringExtra("value");
        if(property.equals("do")) {
//            expandableListView.setVisibility(View.GONE);
            String head1 = value.split("!@")[1];
            String row1 = value.split("!@")[2];
            String[] arr_row1 = row1.split("!~");
            String txt1 = "";
            for (int i = 0; i < arr_row1.length; i++) {
//            Toast.makeText(this, head1+"-"+arr_row1[i], Toast.LENGTH_SHORT).show();
                txt1 += "\u2022 " + arr_row1[i] + "\n";
                head1TextView.setText(head1);
                sub1TextView.setText(txt1);
            }
            String head2 = value.split("!@")[3];
            String row2 = value.split("!@")[4];
            String[] arr_row2 = row2.split("!~");
            String txt2 = "";
            for (int i = 0; i < arr_row2.length; i++) {
//            Toast.makeText(this, head2+"-"+arr_row2[i], Toast.LENGTH_SHORT).show();
                txt2 += "\u2022 " + arr_row2[i] + "\n";
                head2TextView.setText(head2);
                sub2TextView.setText(txt2);
            }
        }else if(property.equals("faq")){
//            Toast.makeText(this, "faq", Toast.LENGTH_SHORT).show();
            head1TextView.setVisibility(View.GONE);
            sub1TextView.setVisibility(View.GONE);
            head2TextView.setVisibility(View.GONE);
            sub2TextView.setVisibility(View.GONE);
            helpTextView.setVisibility(View.GONE);

            loadView();
        }
        else{
//            Toast.makeText(this, "else", Toast.LENGTH_SHORT).show();
            expandableListView.setVisibility(View.GONE);
            head1TextView.setVisibility(View.GONE);
            sub1TextView.setVisibility(View.GONE);
            head2TextView.setVisibility(View.GONE);
            sub2TextView.setVisibility(View.GONE);
            helpTextView.setText(value);
        }

        getSupportActionBar().setTitle(name);
//        helpTextView.setText(value);

    }

    private void loadView() {

        Intent in=getIntent();
        List<FaqModel> faqModelList= (List<FaqModel>) in.getSerializableExtra("faq_values");

        for (FaqModel faqModel : faqModelList) {
            if (faqModel.getFaqType()==8){
                int head_id=faqModel.getFaqId();
                listGroup.add(faqModel.getFaqValue());
                List<String> childlist = new ArrayList<>();
                for (FaqModel faqModel2 : faqModelList) {
                    if ((faqModel2.getFaqType()==2)&&(faqModel2.getFaqParentId()==head_id)){
                        childlist.add(faqModel2.getFaqValue());
                        int quest_id=faqModel2.getFaqId();
                        for (FaqModel faqModel3 : faqModelList){
                            if ((faqModel3.getFaqType()==5)&&(faqModel3.getFaqParentId()==quest_id)){
                                childlist.add(faqModel3.getFaqValue());
                            }
                        }
                    }
                }
                listItem.put(faqModel.getFaqValue(),childlist);
            }
        }
        adapter.notifyDataSetChanged();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            onBackPressed();
            return true;
        }
        else
            return super.onOptionsItemSelected(item);
    }
}
