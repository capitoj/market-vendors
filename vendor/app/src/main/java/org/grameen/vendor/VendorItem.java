package org.grameen.vendor;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.grameen.vendor.adapter.ItemAdapter;
import org.grameen.vendor.data.ItemInfo;
import org.grameen.vendor.data.UnitInfo;
import org.grameen.vendor.data.VendorItemInfo;
import org.grameen.vendor.models.Vendor;
import org.grameen.vendor.remote.MarketVendorItem;
import org.grameen.vendor.utils.Constants;
import org.grameen.vendor.xml.XmlParser;

import java.util.List;


public class VendorItem extends Activity {

    protected List<ItemInfo> itemList;
    protected ListView listViewItems;
    protected ArrayAdapter<ItemInfo> adapter;
    protected VendorItemInfo vendorItemInfo;
    protected int clickIndex;
    protected Menu menu;
    protected List<UnitInfo> unitList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_item);
        this.init();
    }

    protected void init(){
        this.clickIndex = 0;
        this.vendorItemInfo = new VendorItemInfo();
        this.itemList = XmlParser.getItems(VendorItem.this);
        this.unitList = XmlParser.getUnits(VendorItem.this);
        this.adapter = new ItemAdapter(VendorItem.this, this.itemList);
        this.listViewItems = (ListView)findViewById(R.id.listViewItems);
        this.listViewItems.setAdapter(this.adapter);
        this.registerCallback();
    }

    protected void registerCallback(){
        this.listViewItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ItemInfo itemInfo = itemList.get(position);
                int itemID = itemInfo.getItemID();
                vendorItemInfo.setItemID(itemID);
                setContentView(R.layout.vendor_item_price);

                MenuItem menuItem = menu.findItem(R.id.vendor_item_accept);
                menuItem.setVisible(true);
                clickIndex = 1;
            }
        });
    }

    protected void menuAction(Menu menu){
        MenuItem menuItem = menu.findItem(R.id.vendor_item_accept);
        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                clickIndex = captureVendorItemInput(clickIndex);
                return true;
            }
        });
    }

    protected int captureVendorItemInput(int clickIndex){
        if(clickIndex == 1){
            if(this.getPrice() != -1) {
                this.vendorItemInfo.setPrice(this.getPrice());
                setContentView(R.layout.activity_vendor_item_units);
                this.setUnits();
                clickIndex++;
            }
        }else if(clickIndex == 2){
            int position = this.getUnitIndex();
            if(position > -1) {
                UnitInfo unitInfo = this.unitList.get(position);
                int unitID = unitInfo.getUnitID();
                this.vendorItemInfo.setUnit(unitID);
                this.saveVendorItems();
            }
        }
        return clickIndex;
    }

    protected void saveVendorItems(){
        Vendor vendor = new Vendor(VendorItem.this);
        AsyncTask<String, Integer, String> vendorItems = new MarketVendorItem(VendorItem.this, vendor.getAppID(), this.vendorItemInfo.getItemID(), this.vendorItemInfo.getPrice(), this.vendorItemInfo.getUnitID()).execute();
        try{
            String result = vendorItems.get().trim();
            if(result.equals(Constants.RESPONSE_SUCCESS)){
                Intent intent = new Intent(VendorItem.this, Home.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }else{
                Toast.makeText(VendorItem.this, result, Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    protected int getPrice(){
        EditText editTextPrice = (EditText)findViewById(R.id.editTextPrice);
        return editTextPrice.getText().toString().length() > 0 ? Integer.parseInt(editTextPrice.getText().toString()) : -1;
    }

    protected int getUnitIndex(){
        int position = -1;
        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.radioItemUnit);
        int radioButtonID = radioGroup.getCheckedRadioButtonId();
        if(radioButtonID > -1) {
            View radioButton = radioGroup.findViewById(radioButtonID);
            position = radioGroup.indexOfChild(radioButton);
        }
        return position;
    }

    protected void setUnits(){
        RadioButton[] radioButtons = new RadioButton[this.unitList.size()];
        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.radioItemUnit);
        for(int i = 0; i < this.unitList.size(); i++){
            radioButtons[i] = new RadioButton(this);
            UnitInfo unitInfo = this.unitList.get(i);
            radioButtons[i].setText(unitInfo.getUnitName());
            radioGroup.addView(radioButtons[i]);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_vendor_item, menu);
        this.menuAction(menu);
        this.menu = menu;
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
