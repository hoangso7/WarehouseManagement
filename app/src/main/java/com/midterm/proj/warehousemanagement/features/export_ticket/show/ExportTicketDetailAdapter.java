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
import com.midterm.proj.warehousemanagement.model.ExportTicketDetail;

import java.util.ArrayList;

public class ExportTicketDetailAdapter extends ArrayAdapter<ExportTicketDetail> {
    private Context mContext;
    TextView tvIdExportTicket, tvProductName, tvNumber, tvPriceUnit;
    private ArrayList<ExportTicketDetail> exportTicketsDetailArrayList = new ArrayList<>();

    public ExportTicketDetailAdapter(@NonNull Context context, @SuppressLint("SupportAnnotationUsage") @LayoutRes ArrayList<ExportTicketDetail> list){
        super(context, 0 , list);
        mContext = context;
        exportTicketsDetailArrayList = list;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null){
            listItem = LayoutInflater.from(mContext).inflate(R.layout.export_ticket_detail_list_item, parent, false);
        }
        ExportTicketDetail exportTicketDetail = exportTicketsDetailArrayList.get(position);

        tvIdExportTicket = listItem.findViewById(R.id.tv_id_export_ticket);
        tvProductName = listItem.findViewById(R.id.tv_product_name);
        tvNumber = listItem.findViewById(R.id.tv_product_number);
        tvPriceUnit = listItem.findViewById(R.id.tv_price_unit);
        tvIdExportTicket.setText(String.valueOf(exportTicketDetail.getID_ExportTicket()));
        tvProductName.setText(String.valueOf(exportTicketDetail.getID_Product()));
        tvNumber.setText(String.valueOf(exportTicketDetail.getNumber()));
        tvPriceUnit.setText(String.valueOf(exportTicketDetail.getPricePerUnit()));

        return listItem;
    }
}
