package com.example.android.newsandroid;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

public class Recycler_View extends RecyclerView.Adapter<Recycler_View.RecyclerViewHolder> {
    private RequestQueue mqueue;
    TextView headline, anchor;
    ImageView img;
    private Context context;
    ArrayList<list> items=new ArrayList<list>();
    String a,b,c,d;

    public Recycler_View(Context context,ArrayList<list> head) {
        this.context = context;
        items=head;
    }



    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.recycler_view, viewGroup, false);
        mqueue = Volley.newRequestQueue(context);
        return new RecyclerViewHolder(view);
    }

//
//    List<String> jsonParse(final int index) {
//        String url = "https://newsapi.org/v2/top-headlines?country=in&apiKey=987cf3634d544e6d9b43a86e5b967b30";
//        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            JSONArray jsonArray = response.getJSONArray("articles");
//                            JSONObject news = jsonArray.getJSONObject(2);
//                            String title = news.getString("title");
//                            String image = news.getString("urlToImage");
////                            JSONObject source=news.getJSONObject("source");
//                            String name = news.getString("description");
////                            items.add(title);
////                            items.add(image);
////                            items.add(name);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
//            }
//        });
//        mqueue.add(request);
//        return items;
//    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder recyclerViewHolder, final int i) {
//        List<String> data = jsonParse(i);
        recyclerViewHolder.newsHeadline.setText(String.valueOf(items.get(i).getTitle()));
        Picasso.with(context).load(String.valueOf(items.get(i).getImage())).into(recyclerViewHolder.newsImage);
        recyclerViewHolder.newsSource.setText(String.valueOf(items.get(i).getSource()));
        a=String.valueOf(items.get(i).getTitle());
        b=String.valueOf(items.get(i).getImage());
        c=String.valueOf(items.get(i).getSource());
        d=String.valueOf(items.get(i).getDescription());
        recyclerViewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   passData(a,b,c,d);

                Intent intent=new Intent(context,expand_news.class);
                intent.putExtra("title",String.valueOf(items.get(i).getTitle()));
                intent.putExtra("image",String.valueOf(items.get(i).getImage()));
                intent.putExtra("source",String.valueOf(items.get(i).getSource()));
                intent.putExtra("content",String.valueOf(items.get(i).getDescription()));
                view.getContext().startActivity(intent);
            }
        });

    }
//    private void passData(String p, String q, String r, String s){
//        Intent intent=new Intent(context,expand_news.class);
//        intent.putExtra("title",p);
//        intent.putExtra("image",q);
//        intent.putExtra("source",r);
//        intent.putExtra("content",s);
//        context.startActivity(intent);
//
//    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView newsHeadline, newsSource;
        ImageView newsImage;
        LinearLayout linearLayout;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            newsHeadline = itemView.findViewById(R.id.newsHeadline);
            newsSource = itemView.findViewById(R.id.newsSource);
            newsImage = itemView.findViewById(R.id.newsImage);
           linearLayout=itemView.findViewById(R.id.linear);
        }
    }
}
