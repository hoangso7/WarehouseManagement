package com.midterm.proj.warehousemanagement.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.midterm.proj.warehousemanagement.MainActivity;
import com.midterm.proj.warehousemanagement.R;
import com.midterm.proj.warehousemanagement.warehouse.ExportTicket;
import com.midterm.proj.warehousemanagement.warehouse.ImportTicket;
import com.midterm.proj.warehousemanagement.warehouse.Ticket;
import com.midterm.proj.warehousemanagement.warehouse.TicketInfoAdapter;
import com.midterm.proj.warehousemanagement.warehouse.Warehouse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class WarehouseManagementFragment extends Fragment {
    private TextView txt;
    private ListView listView;
    private TicketInfoAdapter ticketInfoAdapter;
    private Spinner spnWarehouse;
    private TextView tvWarehouseID, tvWarehouseName, tvWarehouseAddress;
    private ToggleButton tgbtnTicketSwitch;
    private static int status = 0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_warehouse_management, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setControl();
        setEvent();
    }

    private void setEvent() {
        //tgbtnTicketSwitch.setChecked(true);
        tgbtnTicketSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tgbtnTicketSwitch.setBackgroundColor(Color.parseColor("#E0857E"));
                    ticketInfoAdapter = new TicketInfoAdapter(getView().getContext(), getImportTicketList(MainActivity.warehouses.get(spnWarehouse.getSelectedItemPosition())));
                    listView.setAdapter(ticketInfoAdapter);
                } else {
                    tgbtnTicketSwitch.setBackgroundColor(Color.parseColor("#A2DDA4"));
                    ticketInfoAdapter = new TicketInfoAdapter(getView().getContext(), getExportTicketList(MainActivity.warehouses.get(spnWarehouse.getSelectedItemPosition())));
                    listView.setAdapter(ticketInfoAdapter);
                }
            }
        });
    }

    private void setControl() {
        listView = getView().findViewById(R.id.lv_ticketLists);
        spnWarehouse = getView().findViewById(R.id.spiner_choose_warehouse_mngmt);
        tvWarehouseID = getView().findViewById(R.id.textview_warehouseID_mngmt);
        tvWarehouseName = getView().findViewById(R.id.textview_warehouseName_mngmt);
        tvWarehouseAddress = getView().findViewById(R.id.textview_warehouseAddress_mngmt);
        tgbtnTicketSwitch = getView().findViewById(R.id.tgbtn_ticket_type_switch);
        setWarehouseSpinnerControl();
    }

    private void setWarehouseSpinnerControl(){
        List<String> warehouseNameList = new ArrayList<>();
        for (Warehouse w: MainActivity.warehouses) {
            warehouseNameList.add(w.getsName());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getView().getContext(),R.layout.spiner_item,warehouseNameList);
        dataAdapter.setDropDownViewResource(R.layout.custom_spiner_item);

        spnWarehouse.setAdapter(dataAdapter);
        spnWarehouse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(MainActivity.this, spnCategory.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                tvWarehouseID.setText(MainActivity.warehouses.get(i).getsID());
                tvWarehouseName.setText(MainActivity.warehouses.get(i).getsName());
                tvWarehouseAddress.setText(MainActivity.warehouses.get(i).getsAddress());
                ticketInfoAdapter = new TicketInfoAdapter(getView().getContext(), getImportTicketList(MainActivity.warehouses.get(i)));
//                if(status == 0){
//                    if(ticketInfoAdapter != null)
//                        ticketInfoAdapter.clear();
//                    ticketInfoAdapter = new TicketInfoAdapter(getView().getContext(), getImportTicketList(MainActivity.warehouses.get(i)));
//                }
//                else{
//                    if(ticketInfoAdapter != null)
//                        ticketInfoAdapter.clear();
//                    ticketInfoAdapter = new TicketInfoAdapter(getView().getContext(), getExportTicketList(MainActivity.warehouses.get(i)));
//                }

                listView.setAdapter(ticketInfoAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private ArrayList<Ticket> getImportTicketList(Warehouse warehouse){
        // do the casting
        ArrayList<Ticket> returnList = new ArrayList<>();
        for(ImportTicket t : warehouse.getImportTickets()){
            ImportTicket a = new ImportTicket(t);
            Ticket b = (Ticket) a;
            returnList.add(b);
        }
        return returnList;
    }

    private ArrayList<Ticket> getExportTicketList(Warehouse warehouse){
        // do the casting
        ArrayList<Ticket> returnList = new ArrayList<>();
        for(ExportTicket t : warehouse.getExportTickets()){
            ExportTicket a = new ExportTicket(t);
            Ticket b = (Ticket) a;
            returnList.add(b);
        }
        return returnList;
    }

    // Sort by creation date
    private ArrayList<Ticket> getTicketList(Warehouse warehouse){
        ArrayList<Ticket> ticketList = new ArrayList<>();
        ArrayList<ImportTicket> importTicketList = new ArrayList<>();
        ArrayList<ExportTicket> exportTicketList = new ArrayList<>();

        importTicketList = warehouse.getImportTickets();
        exportTicketList = warehouse.getExportTickets();

        for (ImportTicket it: importTicketList) {
            ticketList.add(it);
        }

        for(ExportTicket et :exportTicketList){
            ticketList.add(et);
        }

        Collections.sort(ticketList, new Comparator<Ticket>() {
            @Override
            public int compare(Ticket lhs, Ticket rhs) {
                try {
                    SimpleDateFormat dateFormatlhs = new SimpleDateFormat("dd-MM-yyyy hh:mm");
                    Date convertedDatelhs = dateFormatlhs.parse(lhs.getsDate());
                    Calendar calendarlhs = Calendar.getInstance();
                    calendarlhs.setTime(convertedDatelhs);

                    SimpleDateFormat dateFormatrhs = new SimpleDateFormat("dd-MM-yyyy hh:mm");
                    Date convertedDaterhs = dateFormatrhs.parse(rhs.getsDate());
                    Calendar calendarrhs = Calendar.getInstance();
                    calendarrhs.setTime(convertedDaterhs);

                    if(calendarlhs.getTimeInMillis() > calendarrhs.getTimeInMillis())
                    {
                        return -1;
                    }
                    else
                    {
                        return 1;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
        return ticketList;
    }

}
