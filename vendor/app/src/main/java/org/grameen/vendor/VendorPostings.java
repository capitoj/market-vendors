package org.grameen.vendor;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.grameen.vendor.adapter.VendorItemAdapter;
import org.grameen.vendor.data.ItemInfo;
import org.grameen.vendor.xml.XmlParser;

import java.util.ArrayList;
import java.util.List;


public class VendorPostings extends Activity {


    protected ArrayAdapter adapter;
    protected List<ItemInfo> itemList;
    protected ListView listViewItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_postings);
        this.init();
    }

    protected void init(){
        this.itemList = new ArrayList<>();
        this.itemList = XmlParser.getVendorItems(VendorPostings.this);
        this.adapter = new VendorItemAdapter(VendorPostings.this, this.itemList);
        this.listViewItems = (ListView)findViewById(R.id.listViewItems);
        this.listViewItems.setAdapter(this.adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_vendor_postings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
