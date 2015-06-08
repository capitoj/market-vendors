package org.grameen.vendor;

import android.app.ActionBar;
import android.app.ActivityGroup;
import android.app.Activity;
import android.app.LocalActivityManager;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.Toast;

import org.grameen.vendor.data.MarketInfo;
import org.grameen.vendor.data.VendorInfo;
import org.grameen.vendor.database.Sqlite;
import org.grameen.vendor.models.Vendor;
import org.grameen.vendor.remote.VendorRegistration;
import org.grameen.vendor.url.Connection;
import org.grameen.vendor.xml.XmlParser;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class Home extends ActivityGroup{

    protected Vendor vendor;
    protected int clickIndex;
    protected VendorInfo vendorInfo;
    protected List<MarketInfo> markets;

    protected TabHost tabHost;
    protected TabHost.TabSpec tabSpec;
    protected Intent intent;
    protected int marketIndex;
    protected int dropDownClicks;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Sqlite.initDataTables(Home.this);
        Connection.showStatus(Home.this);
        this.init();
        this.loadContentView();
    }

    protected void init(){
        this.vendorInfo = new VendorInfo();
        this.vendor = new Vendor(Home.this);
        this.clickIndex = 0;
        this.markets = XmlParser.getMarkets(Home.this);
        this.dropDownClicks = 0;
        this.marketIndex = 0;
    }

    protected void loadContentView(){
        if(this.vendor.isRegistered()){
            setContentView(R.layout.activity_home);
            this.initDropDownMenu();
            this.initTabLayout();
        }else{
            setContentView(R.layout.activity_registration);
        }
    }

    protected void initDropDownMenu(){
        ActionBar actionBar = this.getActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        final List<MarketInfo> markets = XmlParser.getMarkets(Home.this);
        List<String> marketList = new ArrayList<String>();
        for(int i = 0; i< markets.size(); i++){
            MarketInfo marketInfo = markets.get(i);

            marketList.add(marketInfo.getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, marketList);
        actionBar.setListNavigationCallbacks(adapter, new ActionBar.OnNavigationListener() {
            @Override
            public boolean onNavigationItemSelected(int itemPosition, long itemId) {
                marketIndex = itemPosition;
                dropDownClicks++;
                if(dropDownClicks > 1){
                    tabHost.clearAllTabs();
                    initTabs();
                    tabHost.setCurrentTab(1);
                }
                return true;
            }
        });
    }

    protected void initTabLayout(){

        this.tabHost = (TabHost)findViewById(android.R.id.tabhost);
        this.tabHost.setup(this.getLocalActivityManager());
        this.initTabs();
        this.tabHost.setCurrentTab(0);
    }

    protected void initTabs(){
        this.intent = new Intent(Home.this, VendorPostings.class);
        this.tabSpec = this.tabHost.newTabSpec("myItems");
        this.tabSpec.setIndicator("My Items");
        this.tabSpec.setContent(this.intent);
        this.tabHost.addTab(this.tabSpec);

        this.intent = new Intent(Home.this, MarketVendors.class);
        this.intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        this.tabSpec = this.tabHost.newTabSpec("search");
        this.tabSpec.setIndicator("Item Search");
        this.intent.putExtra("marketIndex", this.marketIndex);
        this.tabSpec.setContent(this.intent);
        this.tabHost.addTab(this.tabSpec);
    }

    protected void loadMenu(Menu menu){
        if(this.vendor.isRegistered()){
            getMenuInflater().inflate(R.menu.menu_home, menu);
            this.menuAction(menu);
        }else{
            getMenuInflater().inflate(R.menu.menu_registration, menu);
            this.registrationMenuAction(menu);
        }
    }

    protected void menuAction(Menu menu){
        MenuItem menuItem = menu.findItem(R.id.action_add_items);
        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_add_items : showAddItems(); break;
                }
                return true;
            }
        });
    }

    protected void showAddItems(){
        Intent intent1 = new Intent(Home.this, VendorItem.class);
        startActivity(intent1);
    }

    protected void registrationMenuAction(Menu menu){
        MenuItem menuItem = menu.findItem(R.id.registration_accept);
        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                clickIndex = captureRegInput(clickIndex);

                return true;
            }
        });
    }

    protected boolean registerVendor(){
        boolean isRegistered = false;
        try {
            AsyncTask<String, Integer, String> registration = new VendorRegistration(this.vendorInfo.getName(), this.vendorInfo.getStallNumber(), this.vendorInfo.getPhoneNumber(), this.vendorInfo.getMarketID()).execute();
            String result = registration.get().toString();
            if (result.trim().length() == 10) {
                String appID = result.trim();
                Vendor vendor1 = new Vendor(Home.this, appID);
                vendor1.register(this.vendorInfo.getName());
                isRegistered = true;
            } else {
                Toast.makeText(Home.this, result, Toast.LENGTH_LONG).show();
            }
        }catch (ExecutionException e){
            e.printStackTrace();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return isRegistered;
    }

    protected int captureRegInput(int clickIndex){
        if(clickIndex == 0){
            if(this.getVendorName().length() > 0) {
                this.vendorInfo.setName(this.getVendorName());
                setContentView(R.layout.activity_vendor_stall_registration);
                clickIndex++;
            }
        }else if(clickIndex == 1){
            if(this.getStallNumber().length() > 0) {
                this.vendorInfo.setStallNumber(this.getStallNumber());
                setContentView(R.layout.activity_phone_number_registration);
                clickIndex++;
            }
        }else if(clickIndex == 2){
            if(this.getPhoneNumber().length() > 0) {
                this.vendorInfo.setPhoneNumber(this.getPhoneNumber());
                setContentView(R.layout.activity_market_registration);
                try {
                    this.setRegisteredMarkets();
                }catch (Exception e){
                    e.printStackTrace();
                }
                clickIndex++;
            }
        }else if(clickIndex == 3){
            int position = this.getMarketIndex();
            if(position > -1) {
                MarketInfo marketInfo = this.markets.get(position);
                int marketID = marketInfo.getID();
                String marketName = marketInfo.getName();
                this.vendorInfo.setMarketID(marketID);
                this.vendorInfo.setMarketName(marketName);

                boolean isRegistered = this.registerVendor();
                if (isRegistered) {
                    Intent intent = new Intent(getApplicationContext(), Home.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        }
        return clickIndex;
    }

    protected void setRegisteredMarkets(){
        RadioButton[] radioButtons = new RadioButton[this.markets.size()];
        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.radioRegLocation);
        for(int i = 0; i < this.markets.size(); i++){
            radioButtons[i] = new RadioButton(this);
            MarketInfo marketInfo = this.markets.get(i);
            radioButtons[i].setText(marketInfo.getName());
            radioGroup.addView(radioButtons[i]);
        }
    }

    protected String getVendorName(){
        EditText editText = (EditText)findViewById(R.id.txtRegVendorName);
        return editText.getText().toString();
    }

    protected String getStallNumber(){
        EditText editText = (EditText)findViewById(R.id.txtRegStallNumber);
        return editText.getText().toString();
    }

    protected String getPhoneNumber(){
        EditText editText = (EditText)findViewById(R.id.txtRegPhoneNumber);
        return editText.getText().toString();
    }

    protected int getMarketIndex(){
        int position = -1;
        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.radioRegLocation);
        int radioButtonID = radioGroup.getCheckedRadioButtonId();
        if(radioButtonID > -1) {
            View radioButton = radioGroup.findViewById(radioButtonID);
            position = radioGroup.indexOfChild(radioButton);
        }
        return position;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        this.loadMenu(menu);
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

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        if(this.clickIndex == 3){
            setContentView(R.layout.activity_phone_number_registration);
            this.clickIndex--;
            return;
        }else if(this.clickIndex == 2){
            setContentView(R.layout.activity_vendor_stall_registration);
            this.clickIndex--;
            return;
        }else if(this.clickIndex == 1){
            setContentView(R.layout.activity_registration);
            this.clickIndex--;
            return;
        }
    }
}
