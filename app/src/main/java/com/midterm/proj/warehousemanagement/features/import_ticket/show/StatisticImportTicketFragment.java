package com.midterm.proj.warehousemanagement.features.import_ticket.show;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.midterm.proj.warehousemanagement.R;
import com.midterm.proj.warehousemanagement.database.QueryResponse;
import com.midterm.proj.warehousemanagement.database.daoImplementation.ImportTicketQuery;
import com.midterm.proj.warehousemanagement.database.daoImplementation.ProductQuery;
import com.midterm.proj.warehousemanagement.database.daoImplementation.WarehouseQuery;
import com.midterm.proj.warehousemanagement.database.daoInterface.DAO;
import com.midterm.proj.warehousemanagement.model.ExportTicket;
import com.midterm.proj.warehousemanagement.model.ImportTicket;
import com.midterm.proj.warehousemanagement.model.Product;
import com.midterm.proj.warehousemanagement.model.Warehouse;

import java.util.ArrayList;
import java.util.List;

public class StatisticImportTicketFragment extends Fragment {
    BarChart chart;
    private ArrayList<Warehouse> allWarehouse = new ArrayList<>();
    private ArrayList<ImportTicket> allImportTicket = new ArrayList<>();
    private ArrayList<Product> product = new ArrayList<>();
    private static int start;
    private static int end;
    private static int month;
    //    private ArrayList<Integer> month = new ArrayList<>();
    private ArrayList<Integer> totalMoneyMoth = new ArrayList<>();

    private void fetchStatistics(){
        totalMoneyMoth.add(0);
        totalMoneyMoth.add(0);
        totalMoneyMoth.add(0);
        totalMoneyMoth.add(0);
        totalMoneyMoth.add(0);
        totalMoneyMoth.add(0);
        totalMoneyMoth.add(0);
        totalMoneyMoth.add(0);
        totalMoneyMoth.add(0);
        totalMoneyMoth.add(0);
        totalMoneyMoth.add(0);
        totalMoneyMoth.add(0);


        allWarehouse.clear();
        allImportTicket.clear();

        DAO.WarehouseQuery warehouseQuery = new WarehouseQuery();
        DAO.ImportTicketQuery importTicketQuery = new ImportTicketQuery();
        DAO.ProductQuery productQuery = new ProductQuery();

        warehouseQuery.readAllWarehouse(new QueryResponse<List<Warehouse>>() {
            @Override
            public void onSuccess(List<Warehouse> data) {
                allWarehouse.addAll(data);
            }

            @Override
            public void onFailure(String message) {

            }
        });

        for (Warehouse w: allWarehouse){
            importTicketQuery.readAllImportTicketFromWarehouse(w.getID_Warehouse(), new QueryResponse<List<ImportTicket>>() {
                @Override
                public void onSuccess(List<ImportTicket> data) {
                    allImportTicket.addAll(data);
                }

                @Override
                public void onFailure(String message) {

                }
            });
        }

        for(ImportTicket i: allImportTicket){
            start = i.getCreateDate().indexOf("/");
            end = i.getCreateDate().indexOf("/", start +1);
            month = Integer.parseInt(i.getCreateDate().substring(start+1, end)) -1;
            Log.d("TAG", "month: " + String.valueOf(month+1));
//            month.add(Integer.parseInt(i.getCreateDate().substring(start+1,end)));
            productQuery.readProduct(i.getProductID(), new QueryResponse<Product>() {
                @Override
                public void onSuccess(Product data) {
                    product.clear();
                    product.add(data);
                    totalMoneyMoth.set(month, (int) (product.get(0).getPrice()*i.getNumber() + totalMoneyMoth.get(month)));

                }

                @Override
                public void onFailure(String message) {

                }
            });
        }
        Log.d("TAG", "fetchAllImportTicket: "+ totalMoneyMoth);


    }

    private void showBarChart(){
        ArrayList noOfEmp = new ArrayList();
        noOfEmp.add(new BarEntry(totalMoneyMoth.get(0),0));     //1
        noOfEmp.add(new BarEntry(totalMoneyMoth.get(1),1));     //2
        noOfEmp.add(new BarEntry(totalMoneyMoth.get(2),2));     //3
        noOfEmp.add(new BarEntry(totalMoneyMoth.get(3),3));     //4
        noOfEmp.add(new BarEntry(totalMoneyMoth.get(4),4));     //5
        noOfEmp.add(new BarEntry(totalMoneyMoth.get(05),5));     //6
        noOfEmp.add(new BarEntry(totalMoneyMoth.get(6),6));     //7
        noOfEmp.add(new BarEntry(totalMoneyMoth.get(7),7));     //8
        noOfEmp.add(new BarEntry(totalMoneyMoth.get(8),8));     //9
        noOfEmp.add(new BarEntry(totalMoneyMoth.get(9),9));     //10
        noOfEmp.add(new BarEntry(totalMoneyMoth.get(10),10));     //11
        noOfEmp.add(new BarEntry(totalMoneyMoth.get(11),11));     //12

        ArrayList month = new ArrayList();

        month.add("1");month.add("2");month.add("3");month.add("4");month.add("5");month.add("6");month.add("7");month.add("8");month.add("9");month.add("10");month.add("11");month.add("12");

        BarDataSet barDataSet = new BarDataSet(noOfEmp, "No Of product");
        chart.animateY(5000);
        BarData data = new BarData(month, barDataSet);
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        chart.setData(data);
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_statistic_import_ticket, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setControl();
        setEvent();
    }

    private void setEvent() {
        fetchStatistics();
        showBarChart();
    }

    private void setControl() {
        chart = getView().findViewById(R.id.barchart);
    }


}