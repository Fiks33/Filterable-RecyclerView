package com.android.fik.filterrecyclerview;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private RecyclerView mRecyclerView;
    private Toolbar mToolbar;

    private CountryAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    private List<Country> mList = new ArrayList<Country>();;

    String[] countryArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView	= (RecyclerView) findViewById(R.id.rv_country);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);

        mLayoutManager      = new LinearLayoutManager(this);
        mAdapter            = new CountryAdapter(this);

        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        setData();
    }

    public void setData(){
        countryArray = getResources().getStringArray(R.array.countries);
        for (int i = 0; i < countryArray.length ; i++) {
            Country data = new Country();
            data.setCountry_name(countryArray[i]);

            mList.add(data);
        }

        mAdapter.setArray(mList);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);

        searchView.setQueryHint("Search Country ...");
        searchView.setOnQueryTextListener(this);

        MenuItemCompat.setOnActionExpandListener(item,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        // Do something when collapsed
                        if (mList == null) {

                        }else{
                            mAdapter.setFilter(mList);
                        }
                        return true; // Return true to collapse action view
                    }

                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        // Do something when expanded
                        return true; // Return true to expand action view
                    }
                });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if(mList == null){
            return false;
        }else{
            int textlength  = newText.length();
            final List<Country> filteredModelList = filter(mList, newText);
            mAdapter.setFilter(filteredModelList);

            if (textlength == 0) {

                mAdapter.setDataSearch(null);

            } else {
                String searchData = newText.toString().toLowerCase();
                mAdapter.setDataSearch(searchData);
            }
            return true;
        }
    }

    private List<Country> filter(List<Country> models, String query) {
        query = query.toLowerCase();

        final List<Country> filteredModelList = new ArrayList<>();
        for (Country model : models) {
            final String text = model.getCountry_name().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }
}
