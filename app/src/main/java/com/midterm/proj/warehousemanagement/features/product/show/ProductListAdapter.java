package com.midterm.proj.warehousemanagement.features.product.show;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.midterm.proj.warehousemanagement.R;
import com.midterm.proj.warehousemanagement.model.Product;
import com.midterm.proj.warehousemanagement.util.BitmapHelper;
import com.midterm.proj.warehousemanagement.util.UniversalImageLoader;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProductListAdapter extends ArrayAdapter<Product> {
    private LayoutInflater mInflater;
    private List<Product> mProducts = null;
    private ArrayList<Product> arrayList; //used for the search bar
    private int layoutResource;
    private Context mContext;
    private String mAppend;

    public ProductListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Product> products) {
        super(context, resource, products);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutResource = resource;
        this.mContext = context;
        //mAppend = append;
        this.mProducts = products;
        arrayList = new ArrayList<>();
        this.arrayList.addAll(mProducts);
    }

    private static class ViewHolder {
        TextView name;
        CircleImageView productImage;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder holder;
        if(convertView == null){
            convertView = mInflater.inflate(layoutResource, parent, false);
            holder = new ViewHolder();
            holder.name = convertView.findViewById(R.id.productName);
            holder.productImage = convertView.findViewById(R.id.productImage);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder)convertView.getTag();
        }

        String productName = getItem(position).getName();
        String productImagePath = BitmapHelper.saveProductImageToFile(getItem(position));
        holder.name.setText(productName);
        UniversalImageLoader universalImageLoader = new UniversalImageLoader(getContext());
        ImageLoader.getInstance().init(universalImageLoader.getConfig());
        ImageLoader imageLoader = ImageLoader.getInstance();
        String decodedImgUri = Uri.fromFile(new File(productImagePath)).toString();
        imageLoader.displayImage(decodedImgUri, holder.productImage, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        });
        return convertView;
    }

    // filter name in Search Bar
    public void filter(String characterText) {
        characterText = characterText.toLowerCase(Locale.getDefault());
        mProducts.clear();
        if (characterText.length() == 0) {
            mProducts.addAll(arrayList);
        } else {
            mProducts.clear();
            for (Product product: arrayList) {
                if (product.getName().toLowerCase(Locale.getDefault()).contains(characterText)) {
                    mProducts.add(product);
                }
            }
        }
        notifyDataSetChanged();
    }
}
