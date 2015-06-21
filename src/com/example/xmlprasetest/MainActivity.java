package com.example.xmlprasetest;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.xmlprasetest.model.Book;
import com.example.xmlprasetest.parser.BookParser;
import com.example.xmlprasetest.parser.DomBookParser;
import com.example.xmlprasetest.parser.PullBookParser;
import com.example.xmlprasetest.parser.SaxBookParser;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

public class MainActivity extends Activity {

    private BookParser parser;  
    private List<Book> books;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        getActionBar().setDisplayHomeAsUpEnabled(true);
        
        Button readBtn = (Button) findViewById(R.id.button1);
        Button writeBtn = (Button) findViewById(R.id.button2);
        
        readBtn.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                try {  
                    InputStream is = getAssets().open("books.xml");  
//                    parser = new SaxBookParser();  //创建SaxBookParser实例  
//                    parser = new DomBookParser();
                    parser = new PullBookParser();
                    books = parser.parse(is);  //解析输入流  
                    for (Book book : books) {  
                        Log.i("", book.toString());  
                    }  
                } catch (Exception e) {  
                    Log.e("", e.getMessage());  
                } 
                
            }
        });
        
        writeBtn.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                try {  
                    String xml = parser.serialize(books);  //序列化  
                    FileOutputStream fos = openFileOutput("books.xml", Context.MODE_PRIVATE);  
                    fos.write(xml.getBytes("UTF-8"));  
                } catch (Exception e) {  
                    Log.e("", e.getMessage());
                }  
                
            }
        });
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
