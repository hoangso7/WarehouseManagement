package com.midterm.proj.warehousemanagement.features.export_ticket.show;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.midterm.proj.warehousemanagement.R;
import com.midterm.proj.warehousemanagement.database.QueryResponse;
import com.midterm.proj.warehousemanagement.database.daoImplementation.ExportTicketQuery;
import com.midterm.proj.warehousemanagement.database.daoImplementation.WarehouseQuery;
import com.midterm.proj.warehousemanagement.database.daoInterface.DAO;
import com.midterm.proj.warehousemanagement.model.ExportTicket;
import com.midterm.proj.warehousemanagement.model.Warehouse;
import com.midterm.proj.warehousemanagement.util.ThirdPartyApp;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class ShowExportTicketFragment extends Fragment {
    private ListView lvExportTicketListView;
    private Spinner spnWarehouse;
    private ExportTicketAdapter adapter;
    private ArrayList<Warehouse> warehouses = new ArrayList<>();
    private ArrayList<ExportTicket> exportTicketsArrayList = new ArrayList<>();
    private static int warehouseId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show_export_ticket, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setControl();
        setEvent();
    }

    private void setEvent() {
        fetchWarehouseList();
        final int[] WarehouseId = {-1};
        ArrayList<String> warehouseNameList = new ArrayList<>();
        for(Warehouse w:warehouses){
            warehouseNameList.add(w.getName());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getView().getContext(), R.layout.spiner_item,warehouseNameList);
        dataAdapter.setDropDownViewResource(R.layout.custom_spiner_item);

        spnWarehouse.setAdapter(dataAdapter);
        spnWarehouse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                warehouseId = (warehouses.get(i).getID_Warehouse());
                fetchExportTicket();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }
    private void ExportTicketInfo(int position) {
    }
    private void setControl() {
        spnWarehouse = getView().findViewById(R.id.spiner_choose_warehouse);
        lvExportTicketListView = getView().findViewById(R.id.lv_export_ticket_list);

    }

    private void fetchWarehouseList(){
        DAO.WarehouseQuery warehouseQuery = new WarehouseQuery();
        warehouseQuery.readAllWarehouse(new QueryResponse<List<Warehouse>>() {
            @Override
            public void onSuccess(List<Warehouse> data) {
                warehouses.clear();
                warehouses.addAll(data);
            }

            @Override
            public void onFailure(String message) {

            }
        });


    }

    private void showOptions(int idExportTicket) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Tùy chọn");
        builder.setIcon(R.drawable._warehouse);
        String[] options = {"Chi tiết"};
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0: // Chỉnh sửa thông tin
                        ShowDetailExportTicketFragment showDetailExportTicketFragment = ShowDetailExportTicketFragment.newInstance(idExportTicket);
                        showDetailExportTicketFragment.show(getFragmentManager(),"show_detail_export_ticket");
                        break;
                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void fetchExportTicket() {
        DAO.ExportTicketQuery exportTicketQuery = new ExportTicketQuery();
        exportTicketQuery.readAllExportTicket(warehouseId , new QueryResponse<List<ExportTicket>>() {
            @Override
            public void onSuccess(List<ExportTicket> data) {
                exportTicketsArrayList.clear();
                exportTicketsArrayList.addAll(data);
                adapter = new ExportTicketAdapter(getContext(), exportTicketsArrayList);
                lvExportTicketListView.setAdapter(adapter);
            }

            @Override
            public void onFailure(String message) {
            }
        });

        lvExportTicketListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showOptions(exportTicketsArrayList.get(position).getID_ExportTicket());
                return false;
            }
        });
    }
}