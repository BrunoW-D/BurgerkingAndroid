package com.example.bruno.burgerkinglivraison;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SimpleAdapter;

import java.util.List;
import java.util.Map;


/**
 * Created by Bruno on 06/06/2016.
 */
public class MyCustomAdapter extends SimpleAdapter {

    private Context context;
    private List<? extends Map<String, ?>> data;
    private String[] from;

    /**
     * Constructor
     *
     * @param context  The context where the View associated with this SimpleAdapter is running
     * @param data     A List of Maps. Each entry in the List corresponds to one row in the list. The
     *                 Maps contain the data for each row, and should include all the entries specified in
     *                 "from"
     * @param resource Resource identifier of a view layout that defines the views for this list
     *                 item. The layout file should include at least those named views defined in "to"
     * @param from     A list of column names that will be added to the Map associated with each
     *                 item.
     * @param to       The views that should display column in the "from" parameter. These should all be
     *                 TextViews. The first N views in this list are given the values of the first N columns
     */
    public MyCustomAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        this.context = context;
        this.data = data;
        this.from = from;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = super.getView(position, convertView, parent);

        Button b=(Button)v.findViewById(R.id.idItineraire);
        b.setTag(position);
        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                int position=(Integer)arg0.getTag();

                //Appel d'une nouvelle vue !
                Intent unIntent = new Intent(context, ItineraireActivity.class);
                unIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                unIntent.putExtra("adresse", String.valueOf(data.get(position).get(from[1])));
                unIntent.putExtra("cp", String.valueOf(data.get(position).get(from[2])));
                unIntent.putExtra("ville", String.valueOf(data.get(position).get(from[3])));
                context.startActivity(unIntent);
                Log.e("oki ", "doki");
            }
        });
        return v;
    }

}
