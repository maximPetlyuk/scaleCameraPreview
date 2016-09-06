package com.onix.scalecamerapreview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.onix.scalecamerapreview.models.Size;

import java.util.List;

/**
 * Created by Maxim on 06.09.2016.
 */
public class PreviewSizeAdapter extends ArrayAdapter<Size> {

    public PreviewSizeAdapter(Context context, List<Size> mPreviewSizeList) {
        super(context, android.R.layout.simple_list_item_2, mPreviewSizeList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Size size = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(android.R.layout.simple_list_item_2, null);
        }
        ((TextView) convertView.findViewById(android.R.id.text1))
                .setText(String.valueOf(size.width));
        ((TextView) convertView.findViewById(android.R.id.text2))
                .setText(String.valueOf(size.height));
        return convertView;
    }
}
