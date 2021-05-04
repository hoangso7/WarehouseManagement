package com.midterm.proj.warehousemanagement.features.import_ticket.show;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.midterm.proj.warehousemanagement.R;
import com.midterm.proj.warehousemanagement.database.QueryResponse;
import com.midterm.proj.warehousemanagement.database.daoImplementation.ImportTicketQuery;
import com.midterm.proj.warehousemanagement.database.daoImplementation.ProductQuery;
import com.midterm.proj.warehousemanagement.database.daoInterface.DAO;
import com.midterm.proj.warehousemanagement.model.ImportTicket;
import com.midterm.proj.warehousemanagement.model.Product;

import java.util.ArrayList;


public class ShowDetailImportTicketFragment extends DialogFragment {

  private ListView lvImportTicketDetailListView;
  private ImportTicketDetailAdapter adapter;
  private static int importTicketId;


  private ArrayList<ImportTicket> importTicketsDetailArrayList = new ArrayList<>();
    public ShowDetailImportTicketFragment() {
        // Required empty public constructor
    }


    public static ShowDetailImportTicketFragment newInstance(int pos) {
        ShowDetailImportTicketFragment fragment = new ShowDetailImportTicketFragment();
        Bundle args = new Bundle();
        importTicketId = pos;
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show_detail_import_ticket, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setControl();
        setEvent();
    }

    private void setEvent() {
        DAO.ImportTicketQuery importTicketQuery = new ImportTicketQuery();
        importTicketQuery.readImportTicket(importTicketId, new QueryResponse<ImportTicket>() {
            @Override
            public void onSuccess(ImportTicket data) {
                importTicketsDetailArrayList.clear();
                importTicketsDetailArrayList.add(data);
                adapter = new ImportTicketDetailAdapter(getContext(), importTicketsDetailArrayList);
                lvImportTicketDetailListView.setAdapter(adapter);
            }

            @Override
            public void onFailure(String message) {

            }
        });
    }

    private void setControl() {
        lvImportTicketDetailListView = getView().findViewById(R.id.lv_import_ticket_detail_list);
    }
}