package com.midterm.proj.warehousemanagement.features.export_ticket.show;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.midterm.proj.warehousemanagement.R;
import com.midterm.proj.warehousemanagement.model.ExportTicket;

import java.util.ArrayList;

public class ExportTicketAdapter extends ArrayAdapter<ExportTicket> {
    private Context mContext;
    private ArrayList<ExportTicket> exportTicketsArrayList = new ArrayList<>();

    public ExportTicketAdapter(@NonNull Context context, @SuppressLint("SupportAnnotationUsage") @LayoutRes ArrayList<ExportTicket> list){
        super(context, 0 , list);
        mContext = context;
        exportTicketsArrayList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null){
            listItem = LayoutInflater.from(mContext).inflate(R.layout.export_ticket_list_item, parent, false);
        }
        ExportTicket exportTicket = exportTicketsArrayList.get(position);
        TextView idExportTicket = listItem.findViewById(R.id.tv_id_export_ticket);
        TextView idWarehouse = listItem.findViewById(R.id.tv_id_warehouse);
        TextView idEmployee = listItem.findViewById(R.id.tv_id_employee);
        TextView idCustomer = listItem.findViewById(R.id.tv_id_customer);
        TextView tvCreateDate = listItem.findViewById(R.id.tv_create_date);
        idExportTicket.setText(String.valueOf(exportTicket.getID_ExportTicket()));
        idWarehouse.setText(String.valueOf(exportTicket.getWarehouseID()));
        idEmployee.setText(String.valueOf(exportTicket.getEmployeeID()));
        idCustomer.setText(String.valueOf(exportTicket.getCustomerID()));
        tvCreateDate.setText(exportTicket.getCreateDate());

        return listItem;
    }
}
