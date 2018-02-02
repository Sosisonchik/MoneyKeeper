package com.sosiisonchik.moneykeeper;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.Query;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    static class Item {
        public String name;
        public int price;


        public Item(String name, int price) {
            this.name = name;
            this.price = price;
        }
    }
    //For git
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Typeface font = Typeface.createFromAsset(getAssets(),"fonts/fontawesome-webfont.ttf");
        final Button del = (Button)findViewById(R.id.del);

        del.setTypeface(font);
        del.setText("\uf014");
        final Button adds = (Button)findViewById(R.id.add);
        adds.setTypeface(font);
        adds.setText("\uf055");
        SharedPreferences sPreferences;
        final EditText name = (EditText)findViewById(R.id.name);
        final EditText price = (EditText)findViewById(R.id.price);
        final ListView items = (ListView)findViewById(R.id.items);
        final Button sum = (Button)findViewById(R.id.sum);
        final ItemsAdapter adapter = new ItemsAdapter();
        items.setAdapter(adapter);

        adds.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try{
                            Item item = new Item(name.getText().toString(),Integer.valueOf(price.getText().toString()));
                            adapter.insert(item,0);

                        }catch (Exception e){System.out.print(e.getMessage());}
                        name.setText(null);
                        price.setText(null);
                    }
                }
        );

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.remove(adapter.getItem(adapter.getCount()-1));

            }
        });
        sum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sum.getText().toString().equals("Всього")){

                    try {
                    int result = 0;
                    for(int i=0;i<adapter.getCount();i++){
                        Item item1 = adapter.getItem(i);
                        String a = String.valueOf(item1.price);
                        result +=Integer.valueOf(a);
                    }
                    sum.setText(String.valueOf(result));
                }catch (Exception e){}}else{sum.setText("Всього");}
            }
        });
    }

    private int Own(int a) {
        return a++;
    }


    private class ItemsAdapter extends ArrayAdapter<Item> {
        public ItemsAdapter() {
            super(MainActivity.this, R.layout.item);
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.item,null);
            ((TextView)view.findViewById(R.id.name)) .setText(getItem(position).name);
            ((TextView)view.findViewById(R.id.price)).setText(String.valueOf(getItem(position).price));
            return view;
        }
    }

}
