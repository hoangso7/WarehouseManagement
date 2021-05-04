package com.midterm.proj.warehousemanagement.features.import_ticket.show;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.midterm.proj.warehousemanagement.R;
import com.midterm.proj.warehousemanagement.database.QueryResponse;
import com.midterm.proj.warehousemanagement.database.daoImplementation.ImportTicketQuery;
import com.midterm.proj.warehousemanagement.database.daoImplementation.WarehouseQuery;
import com.midterm.proj.warehousemanagement.database.daoInterface.DAO;
import com.midterm.proj.warehousemanagement.features.export_ticket.show.ExportTicketAdapter;
import com.midterm.proj.warehousemanagement.features.export_ticket.show.ShowDetailExportTicketFragment;
import com.midterm.proj.warehousemanagement.model.ImportTicket;
import com.midterm.proj.warehousemanagement.model.Warehouse;

import java.util.ArrayList;
import java.util.List;

public class ShowImportTicketFragment extends Fragment {

    private ListView lvImportTicketListView;
    private Spinner spnWarehouse;
    private ImportTicketAdapter adapter;
    private ArrayList<Warehouse> warehouses = new ArrayList<>();
    private ArrayList<ImportTicket> importTicketArrayList = new ArrayList<>();
    private static int warehouseId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_show_import_ticket, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setControl();
        setEvent();
    }

    private void setEvent() {
        fetchWarehouseList();
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
                fetchImportTicket();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setControl() {
        spnWarehouse = getView().findViewById(R.id.spiner_choose_warehouse);
        lvImportTicketListView = getView().findViewById(R.id.lv_import_ticket_list);
    }
    private void fetchWarehouseList() {
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


    private void showOptions(int idImportTicket, int warehouseId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Tùy chọn");
        builder.setIcon(R.drawable._warehouse);
        String[] options = {"Chi tiết"};
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0: // Chỉnh sửa thông tin
                        ShowDetailImportTicketFragment showDetailImportTicketFragment = ShowDetailImportTicketFragment.newInstance(idImportTicket, warehouseId);
                        showDetailImportTicketFragment.show(getFragmentManager(), "show_detail_import_ticket");
                        break;
                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private  void fetchImportTicket(){
        DAO.ImportTicketQuery importTicketQuery = new ImportTicketQuery();
        importTicketQuery.readAllImportTicketFromWarehouse(warehouseId, new QueryResponse<List<ImportTicket>>() {
            @Override
            public void onSuccess(List<ImportTicket> data) {
                importTicketArrayList.clear();
                importTicketArrayList.addAll(data);
                adapter = new ImportTicketAdapter(getContext(), importTicketArrayList);
                lvImportTicketListView.setAdapter(adapter);
            }

            @Override
            public void onFailure(String message) {
            }
        });

        lvImportTicketListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showOptions(importTicketArrayList.get(position).getImportTicketID(), importTicketArrayList.get(position).getID_Warehouse());
                return false;
            }
        });
    }

}
