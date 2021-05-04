package com.midterm.proj.warehousemanagement.features.dashboard;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.midterm.proj.warehousemanagement.R;
import com.midterm.proj.warehousemanagement.features.export_ticket.show.ShowExportTicketFragment;
import com.midterm.proj.warehousemanagement.features.import_ticket.show.ShowImportTicketFragment;


public class ImportExportFragment extends Fragment {

    Button btnImport, btnExport;
    private FragmentActivity myContext;
    LinearLayout importExportmenu;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_import_export, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setControl();
        setEvent();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        myContext = (FragmentActivity) context;
        super.onAttach(context);
    }

    private void setEvent() {
        btnImport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                importExportmenu.setVisibility(View.INVISIBLE);
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_import_export_container, new ShowImportTicketFragment());
                fragmentTransaction.commit();
            }
        });

        btnExport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                importExportmenu.setVisibility(View.INVISIBLE);
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_import_export_container, new ShowExportTicketFragment());
                fragmentTransaction.commit();
            }
        });
    }

    private void setControl() {
        btnImport = getView().findViewById(R.id.btn_menu_import);
        btnExport = getView().findViewById(R.id.btn_menu_export);
        importExportmenu = getView().findViewById(R.id.import_export_menu);
    }
}