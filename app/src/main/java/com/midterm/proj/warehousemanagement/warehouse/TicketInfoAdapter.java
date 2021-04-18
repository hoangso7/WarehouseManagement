package com.midterm.proj.warehousemanagement.warehouse;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.midterm.proj.warehousemanagement.R;

import java.util.ArrayList;
import java.util.List;

public class TicketInfoAdapter extends ArrayAdapter {
    private Context mContext;
    private ArrayList<Ticket> ticketList = new ArrayList<>();

    public TicketInfoAdapter(@NonNull Context context, @SuppressLint("SupportAnnotationUsage") @LayoutRes ArrayList<Ticket> list){
        super(context, 0 , list);
        mContext=context;
        ticketList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        Ticket currentTicket = ticketList.get(position);
        String info = "";
        if(currentTicket instanceof ImportTicket){
            listItem = LayoutInflater.from(mContext).inflate(R.layout.import_ticket_list_item, parent, false);
            info = "Nhập " + currentTicket.getiNumber()+ " " + currentTicket.getsCalcUnit() + " "+ currentTicket.getProduct().getsName();
        }else{
            listItem = LayoutInflater.from(mContext).inflate(R.layout.export_ticket_list_item, parent, false);
            ExportTicket temp = (ExportTicket) currentTicket;
            info = "Xuất " + currentTicket.getiNumber()+ " " + currentTicket.getsCalcUnit() + " "+ temp.getExportedProductName();
        }

        TextView name = listItem.findViewById(R.id.textview_ticket_info);
        name.setText(info);
        return listItem;
    }
}
