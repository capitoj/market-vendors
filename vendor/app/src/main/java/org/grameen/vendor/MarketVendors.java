package org.grameen.vendor;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.grameen.vendor.adapter.MarketVendorItemAdapter;
import org.grameen.vendor.data.ItemInfo;
import org.grameen.vendor.data.MarketInfo;
import org.grameen.vendor.remote.MarketVendorItem;
import org.grameen.vendor.xml.XmlParser;

import java.util.List;


public class MarketVendors extends Activity {


    protected List<ItemInfo> itemList;

    protected ListView listViewItems;
    protected int marketIndex;
    protected ArrayAdapter<ItemInfo> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_vendors);
        this.marketIndex = this.getIntent().getIntExtra("marketIndex", 0);

        this.init();
    }


    protected void init(){
        this.listViewItems = (ListView)findViewById(R.id.listViewItems);
        List<MarketInfo> marketList = XmlParser.getMarkets(MarketVendors.this);
        MarketInfo marketInfo = marketList.get(marketIndex);
        this.itemList = XmlParser.getMarketVendorItems(MarketVendors.this, marketInfo.getID());

        this.adapter = new MarketVendorItemAdapter(MarketVendors.this, this.itemList);
        this.listViewItems.setAdapter(this.adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_market_vendors, menu);
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
