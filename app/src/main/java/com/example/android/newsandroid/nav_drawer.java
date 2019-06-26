package com.example.android.newsandroid;

import android.app.SearchManager;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.ArrayList;


public class nav_drawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ArrayList<list> blue = new ArrayList<list>();
    private TextView mainpicText,Breaking;
    private ImageView mainpic;
    private RequestQueue mqueue;
    String i_title,i_img,i_source,i_description;
    String newUrl="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        String title,content,imageUrl;



        setContentView(R.layout.activity_nav_drawer);
        mainpicText = (TextView) findViewById(R.id.mainpicText);
        mainpic = findViewById(R.id.mainpic);
        mqueue = Volley.newRequestQueue(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Breaking=findViewById(R.id.breaking);
        Intent intent =getIntent();
        Bundle bundle=intent.getExtras();
        if(bundle!= null){
            newUrl=String.valueOf(bundle.get("query"));
            Breaking.setText(String.valueOf(bundle.get("name")));
        }

        final RecyclerView newsList = findViewById(R.id.newsList);
        newsList.setLayoutManager(new LinearLayoutManager(this));
        ViewCompat.setNestedScrollingEnabled(newsList, false);
        String url = "https://newsapi.org/v2/top-headlines?country=in&apiKey=987cf3634d544e6d9b43a86e5b967b30"+newUrl;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                            try {
                                JSONArray jsonArray = response.getJSONArray("articles");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject news = jsonArray.getJSONObject(i);
                                    String title = news.getString("title");
                                    String image = news.getString("urlToImage");
                                    JSONObject source = news.getJSONObject("source");
                                    String name = source.getString("name");
                                    String dus = news.getString("content");
                                    if (i == 0) {
                                        mainpicText.setText(title);
                                        i_title = title;
                                        i_img = image;
                                        i_source = name;
                                        i_description = dus;
                                        Picasso.with(nav_drawer.this).load(image)
                                                .into(mainpic);
                                        continue;
                                    }
                                    list mlist = new list();
                                    mlist.setTitle(title);
                                    mlist.setImage(image);
                                    mlist.setSource(name);
                                    mlist.setDescription(dus);
                                    blue.add(mlist);
                                }
                                newsList.setAdapter(new Recycler_View(getApplicationContext(), blue));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mqueue.add(request);

        mainpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(nav_drawer.this,expand_news.class);
                intent.putExtra("title",i_title);
                intent.putExtra("image",i_img);
                intent.putExtra("source",i_source);
                intent.putExtra("content",i_description);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
//private Boolean exit = false;
//    @Override
//    public void onBackPressed() {
//        if (exit) {
//            finish(); // finish activity
//        } else {
//            Toast.makeText(this, "Press Back again to Exit.",
//                    Toast.LENGTH_SHORT).show();
//            exit = true;
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    exit = false;
//                }
//            }, 3 * 1000);
//
//        }
//
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_drawer, menu);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Intent i= new Intent(nav_drawer.this,nav_drawer.class);
                i.putExtra("query","&q="+searchView.getQuery());
                i.putExtra("name",s+" News");
                startActivity(i);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_business) {
            // Handle the camera action
//            findViewById(R.id.nav_business).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
                    Intent i= new Intent(nav_drawer.this,nav_drawer.class);
                    i.putExtra("query","&category=business");
                    i.putExtra("name","Business News");
                    startActivity(i);
//                }
//            });
        } else if (id == R.id.nav_entertainment) {
//            findViewById(R.id.nav_entertainment).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
                    Intent i= new Intent(nav_drawer.this,nav_drawer.class);
                    i.putExtra("query","&category=entertainment");
            i.putExtra("name","Entertainment News");
                    startActivity(i);
//                }
//            });

        } else if (id == R.id.nav_sports) {
//            findViewById(R.id.nav_sports).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
                    Intent i= new Intent(nav_drawer.this,nav_drawer.class);
                    i.putExtra("query","&category=sports");
            i.putExtra("name","Sports News");
                    startActivity(i);
//                }
//            });

        } else if (id == R.id.nav_technology) {
//            findViewById(R.id.nav_technology).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
                    Intent i= new Intent(nav_drawer.this,nav_drawer.class);
                    i.putExtra("query","&category=technology");
            i.putExtra("name","Technology News");
                    startActivity(i);
//                }
//            });

        }
        else if (id == R.id.nav_science) {
//            findViewById(R.id.nav_science).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
                    Intent i= new Intent(nav_drawer.this,nav_drawer.class);
                    i.putExtra("query","&category=science");
            i.putExtra("name","Science News");
                    startActivity(i);
//                }
//            });

        }

//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
