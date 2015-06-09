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
 * Created by Joseph Capito on 6/7/15.
 */
public class ItemAdapter extends ArrayAdapter<ItemInfo> {

    protected Context context;
    protected List<ItemInfo> itemList;

    public ItemAdapter(Context context, List<ItemInfo> itemList){
        super(context, R.layout.vendor_item, itemList);
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup){
        View itemView = view;
        if(itemView == null){
            itemView = ((Activity)context).getLayoutInflater().inflate(R.layout.vendor_item, viewGroup, false);
        }
        ItemInfo itemInfo = this.itemList.get(position);
        TextView textViewItem = (TextView)itemView.findViewById(R.id.textViewItem);
        textViewItem.setText(itemInfo.getItemName());
        return itemView;
    }
}
