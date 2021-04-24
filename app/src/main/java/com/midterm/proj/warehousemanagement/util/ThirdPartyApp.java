package com.midterm.proj.warehousemanagement.util;

import android.content.Intent;
import android.net.Uri;

import static androidx.core.content.ContextCompat.startActivity;

public class ThirdPartyApp {
    public static void dialPhoneNumber(String phone){
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(MyApp.context, intent, null);
    }

    public static void googlemapSearchForAddress(String address){

    }
}
