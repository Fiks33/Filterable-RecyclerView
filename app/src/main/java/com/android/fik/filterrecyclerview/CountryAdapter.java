package com.android.fik.filterrecyclerview;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mochamad Taufik on 03-Nov-16.
 * Email   : thidayat13@gmail.com
 * Company : TRUSTUDIO
 */

public class CountryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<Country> arrlist;
    private String search = null;

    private static Context context;
    private Activity parentAct;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_country, parent, false);
        return new RecyclerView.ViewHolder(view) {
        };
    }


    public void setArray(List<Country> dList) {
        this.arrlist = dList;
    }

    public CountryAdapter(Context c) {
        context = c;
    }

    public void setDataSearch(String data) {
        this.search = data;
    }

    public void setActivity(Activity act) {
        parentAct = act;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        TextView mName      = (TextView) holder.itemView.findViewById(R.id.country);
        if(search != null) {
            mName.setText(highlight(search, arrlist.get(position).getCountry_name()));
        }else if (search == null){
            mName.setText(arrlist.get(position).getCountry_name());
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return arrlist.size();
    }

    public void setFilter(List<Country> countryModels) {
        arrlist = new ArrayList<>();
        arrlist.addAll(countryModels);
        notifyDataSetChanged();
    }

    public static CharSequence highlight(String search, String originalText) {

        String normalizedText = Normalizer.normalize(originalText, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "").toLowerCase();

        int start = normalizedText.indexOf(search);

        Spannable highlighted = new SpannableString(originalText);
        if (start < 0) {
            // not found, nothing to to
            return originalText;
        } else {

            while (start >= 0) {

                int spanStart 	= Math.min(start, originalText.length());
                int spanEnd 	= Math.min(start + search.length(), originalText.length());

                highlighted.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), spanStart, spanEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                highlighted.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.colorAccent)), spanStart, spanEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                start = normalizedText.indexOf(search, spanEnd);
            }

            return highlighted;
        }
    }
}