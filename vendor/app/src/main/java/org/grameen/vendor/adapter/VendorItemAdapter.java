package org.grameen.vendor.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.grameen.vendor.R;
import org.grameen.vendor.data.ItemInfo;

import java.util.List;

/**
 * Created by niyetu on 6/7/15.
 */
public class VendorItemAdapter extends ArrayAdapter<ItemInfo> {

    protected Context context;
    protected List<ItemInfo> itemList;

    public VendorItemAdapter(Context context, List<ItemInfo> itemList){
        super(context, R.layout.market_vendor_item, itemList);
        this.context = context;
        this.itemList = itemList;
    }

    public View getView(int position, View view, ViewGroup viewGroup){
        View itemView = view;
        if(itemView == null){
            itemView = ((Activity)context).getLayoutInflater().inflate(R.layout.market_vendor_item, viewGroup, false);
        }
        ItemInfo itemInfo = this.itemList.get(position);

        TextView textViewItemName = (TextView)itemView.findViewById(R.id.textViewItemName);
        textViewItemName.setText(itemInfo.getItemName());

        TextView textViewItemPrice = (TextView)itemView.findViewById(R.id.textViewItemPrice);
        textViewItemPrice.setText("UGX " + String.valueOf(itemInfo.getPrice()) + " @ ");

        TextView textViewItemUnit = (TextView)itemView.findViewById(R.id.textViewItemUnit);
        textViewItemUnit.setText(itemInfo.getUnit());

        return itemView;
    }
}
