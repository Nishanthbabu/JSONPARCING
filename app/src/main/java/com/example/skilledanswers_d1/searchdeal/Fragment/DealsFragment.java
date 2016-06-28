package com.example.skilledanswers_d1.searchdeal.Fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.hardware.input.InputManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.skilledanswers_d1.searchdeal.Adapters.OfferAdapter;
import com.example.skilledanswers_d1.searchdeal.ImageLoader;
import com.example.skilledanswers_d1.searchdeal.InternetChecker;
import com.example.skilledanswers_d1.searchdeal.MainActivity;
import com.example.skilledanswers_d1.searchdeal.Models.OffersModel;
import com.example.skilledanswers_d1.searchdeal.R;
import com.example.skilledanswers_d1.searchdeal.RecyclerItemClickListener;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import static android.graphics.Color.BLACK;

/**
 * Created by SkilledAnswers-D1 on 30-03-2016.
 */
public class DealsFragment extends Fragment {

    String responses;
    ProgressDialog progressDialog;
    //// widgits
    private EditText searchView = null;
    private RecyclerView recyclerView = null;
    private Button clearButton = null;
    private SwipeRefreshLayout refreshLayout = null;
    Toolbar toolbar = null;
    private FloatingActionMenu floatingActionMenu = null;
    private FloatingActionButton faball = null;
    private FloatingActionButton fabSortBySites = null;
    private FloatingActionButton fabSortByprice = null;
    private FloatingActionButton fabSortByDiscount = null;


    /////// adapter
    private OfferAdapter adapter = null;
    /////// data stortage arraylist
    public  static ArrayList<OffersModel> offersModels = new ArrayList<>();

    ///// private tempdatastorage for sorting
    private String[] tempSites = null;
    private String tempSortSiteSelectedString = null;

//    /////////// dummy data
//    private String[] company = {"amazon",
//            "ebay",
//            "flipcart",
//            "jabang",
//            "mytra",
//            "shopclues",
//            "swiggy"};
//    private String[] productName = {"book",
//            "mobile",
//            "Pants",
//            "shirts",
//            "pants",
//            "mobile",
//            "food"};
//    private int[] productImage = {R.drawable.book_icon,
//            R.drawable.mobile_icon,
//            R.drawable.pant_icon,
//            R.drawable.shirt_icon,
//            R.drawable.pant_icon,
//            R.drawable.mobile_icon,
//            R.drawable.food_icon};
//    private String[] salePrice = {"100",
//            "5000",
//            "700",
//            "800",
//            "700",
//            "10000",
//            "800"};
//    private String[] actualPrice = {"400",
//            "10000",
//            "350",
//            "1600",
//            "350",
//            "20000",
//            "1600"};
//    private String[] discountDetails = {"75% off",
//            "50% off from 1pm",
//            "50% off from 3pm",
//            "buy one get one free",
//            "10% off on sbi card",
//            "1gb vodofone 4G free ",
//            "10% off on SBI card"};
//    private int[] companyLogo = {R.drawable.amazonlogo,
//            R.drawable.ebay,R.drawable.flipcart,R.drawable.jabang,R.drawable.mytra,
//            R.drawable.shopclueslogo,R.drawable.swiggy};
//    private String[] discount = {"75","50","50","0","10","0","10"};
    /////////////////////////
    //// temp data input function
//    private ArrayList<OffersModel> getdata()
//    {
//        for(int i=0;i<company.length;i++)
//        {
//            model=new OffersModel();
//            model.set_company(company[i]);
//            model.set_productName(productName[i]);
//            int intproductImage = productImage[i];
//            Bitmap productImage =BitmapFactory.decodeResource(getResources(),intproductImage);
//            model.set_productImage(BITMAP_RESIZER(productImage,175,175));
//            model.set_saleprice(salePrice[i]);
//            model.set_actualPrice(actualPrice[i]);
//            model.set_saleDetails(discountDetails[i]);
//            int intCompanyLogo=companyLogo[i];
//            Bitmap companyLogo = BitmapFactory.decodeResource(getResources(),intCompanyLogo);
//            model.set_companyLogo(BITMAP_RESIZER(companyLogo,75,75));
//            model.set_discount(discount[i]);
//            offersModels.add(model);
//        }
//        return offersModels;
//    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.deal_grid_view_frag,container,false);

        floatingActionMenu = (FloatingActionMenu)view.findViewById(R.id.deal_gridview_floatingActionMenu);
        floatingActionMenu.getMenuIconView().setImageResource(R.drawable.filter);
        faball = new FloatingActionButton(getActivity());
        faball.setLabelText("All");
        faball.setImageResource(R.drawable.all_icon);
        fabSortBySites = new FloatingActionButton(getActivity());
        fabSortBySites.setImageResource(R.drawable.sites_icon);
        fabSortBySites.setLabelText("Sort By Sites");
        fabSortByprice = new FloatingActionButton(getActivity());
        fabSortByprice.setImageResource(R.drawable.price_icon);
        fabSortByprice.setLabelText("Sort By Price");
        fabSortByDiscount = new FloatingActionButton(getActivity());
        fabSortByDiscount.setImageResource(R.drawable.discount_icon);
        fabSortByDiscount.setLabelText("Sort By Discount");
        floatingActionMenu.addMenuButton(faball);
        floatingActionMenu.addMenuButton(fabSortBySites);
        floatingActionMenu.addMenuButton(fabSortByprice);
        floatingActionMenu.addMenuButton(fabSortByDiscount);
        searchView = (EditText)view.findViewById(R.id.deal_gridview_searchview);
        recyclerView = (RecyclerView)view.findViewById(R.id.deal_gridview_recycleview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));


        clearButton=(Button)view.findViewById(R.id.deal_clearTextButton);
        refreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.deal_gridview_swipeRefreshLayout);
        if(InternetChecker.isConnectingToInternet(getActivity())) {
            new getGridviewData().execute("http://aftertargets.com/dhamakha_deal/dealofthedayindia_deal.json");
        }else
        {
            Toast.makeText(getActivity(),"No internet conection",Toast.LENGTH_LONG).show();
        }
        searchView.addTextChangedListener(searchWatcher);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setText(null);
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
                floatingActionMenu.close(true);
            }
        });
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                floatingActionMenu.close(true);

            }
        }));
        floatingActionMenu.setOnMenuToggleListener(new FloatingActionMenu.OnMenuToggleListener() {
            @Override
            public void onMenuToggle(boolean opened) {
                if (opened) {

                    float alpha = 0.10f;
                    AlphaAnimation alphaUp = new AlphaAnimation(alpha, alpha);
                    alphaUp.setFillAfter(true);
                    recyclerView.startAnimation(alphaUp);

                } else {
                    float alpha = 255;
                    AlphaAnimation alphaUp = new AlphaAnimation(alpha, alpha);
                    alphaUp.setFillAfter(true);
                    recyclerView.startAnimation(alphaUp);

                }
            }
        });
        fabSortBySites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floatingActionMenu.close(true);
                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.listview);
                dialog.setTitle("Sort by sites");
                ListView listView = (ListView) dialog.findViewById(R.id.listviewID);
                tempSites = new String[offersModels.size()];
                for (int i = 0; i < offersModels.size(); i++) {
                    tempSites[i] = offersModels.get(i).get_company().toString();
                }
                String[] sorted = removeDuplicates(tempSites);  ////// sorted String to remove the duplicate
                final ArrayAdapter<String> Stringadapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_single_choice, sorted);
                listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                listView.setAdapter(Stringadapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        System.out.println("******* pos" + position);
                        System.out.println("jjjjjjj clicked" + position);
                        tempSortSiteSelectedString = Stringadapter.getItem(position).toString();
                        ArrayList<OffersModel> models = new ArrayList<OffersModel>();
                        for (int i = 0; i < offersModels.size(); i++) {
                            if (offersModels.get(i).get_company().toString().equalsIgnoreCase(tempSortSiteSelectedString)) {
                                models.add(new OffersModel(offersModels.get(i).get_company(),
                                        offersModels.get(i).get_productName(),
                                        offersModels.get(i).get_productImage(),
                                        offersModels.get(i).get_saleprice(),
                                        offersModels.get(i).get_actualPrice(),
                                        offersModels.get(i).get_saleDetails(),
                                        offersModels.get(i).get_companyLogo(),
                                        offersModels.get(i).get_discount()));
                            }
                        }
                        adapter = new OfferAdapter(getActivity(), models);
                        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        fabSortByprice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floatingActionMenu.close(true);
                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.listview);
                dialog.setTitle("Sort by price");
                ListView listView = (ListView)dialog.findViewById(R.id.listviewID);
                String[] strings = new String[]{"Low To High","High to Low"};
                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_single_choice,strings);
                listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                listView.setAdapter(arrayAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        switch (position)
                        {
                            case 0: ArrayList<OffersModel> models ;
                                models = offersModels;
                                Collections.sort(models,OffersModel.priceComparatorLowToHigh);
                                adapter = new OfferAdapter(getActivity(),models);
                                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                                recyclerView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                                dialog.dismiss();
                                break;
                            case 1: ArrayList<OffersModel> models1;
                                models1 = offersModels;
                                Collections.sort(models1, OffersModel.priceComparatorHighToLow);
                                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                                adapter = new OfferAdapter(getActivity(),models1);
                                recyclerView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                                dialog.dismiss();
                                break;
                        }
                    }
                });
                dialog.show();

            }
        });
        fabSortByDiscount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floatingActionMenu.close(true);
                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.listview);
                dialog.setTitle("Sort by discount");
                ListView listView = (ListView) dialog.findViewById(R.id.listviewID);
                String[] strings = new String[]{"Low To High", "High to Low"};
                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_single_choice, strings);
                listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                listView.setAdapter(arrayAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        switch (position) {
                            case 0:
                                ArrayList<OffersModel> models;
                                models = offersModels;
                                Collections.sort(models, OffersModel.discountComparatorLowToHigh);
                                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                                adapter = new OfferAdapter(getActivity(), models);
                                recyclerView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                                dialog.dismiss();

                                break;
                            case 1:
                                ArrayList<OffersModel> models1;
                                models1 = offersModels;
                                Collections.sort(models1, OffersModel.discountComparatorHighToLow);
                                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                                adapter = new OfferAdapter(getActivity(), models1);
                                recyclerView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                                dialog.dismiss();
                                break;
                        }
                    }
                });
                dialog.show();
            }
        });
        faball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floatingActionMenu.close(true);
                if(InternetChecker.isConnectingToInternet(getActivity())) {
                    clearData();
                    new getGridviewData().execute("http://aftertargets.com/dhamakha_deal/dealofthedayindia_deal.json");
                }else
                {
                    Toast.makeText(getActivity(), "No connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (InternetChecker.isConnectingToInternet(getActivity())) {
                    clearData();
                    floatingActionMenu.close(true);
                    refreshLayout.setRefreshing(true);
                    new getGridviewData().execute("http://aftertargets.com/dhamakha_deal/dealofthedayindia_deal.json");
                    refreshLayout.setRefreshing(false);
                } else {
                    Toast.makeText(getActivity(), "No connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                floatingActionMenu.close(true);
            }
        });
        return  view;
    }
    TextWatcher searchWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence query, int start, int before, int count) {
            query = query.toString().toLowerCase();
            ArrayList<OffersModel> models = new ArrayList<>();
            for(int i=0;i<offersModels.size();i++)
            {
                if(offersModels.get(i).get_productName().toLowerCase().contains(query))
                {
                    models.add(new OffersModel(offersModels.get(i).get_company(),
                            offersModels.get(i).get_productName(),
                            offersModels.get(i).get_productImage(),
                            offersModels.get(i).get_saleprice(),
                            offersModels.get(i).get_actualPrice(),
                            offersModels.get(i).get_saleDetails(),
                            offersModels.get(i).get_companyLogo(),
                            offersModels.get(i).get_discount()));
                }
            }
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
            adapter = new OfferAdapter(getActivity(),models);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();


        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    ////// fuction to resize the bitmap image and keep the asperation ratio
    public Bitmap BITMAP_RESIZER(Bitmap bitmap,int newWidth,int newHeight) {
        Bitmap scaledBitmap = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888);

        float ratioX = newWidth / (float) bitmap.getWidth();
        float ratioY = newHeight / (float) bitmap.getHeight();
        float middleX = newWidth / 2.0f;
        float middleY = newHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bitmap, middleX - bitmap.getWidth() / 2, middleY - bitmap.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

        return scaledBitmap;

    }
    public void clearData()
    {
        offersModels.clear();
    }


    class getGridviewData extends AsyncTask<String, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = null;
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Loading... please wait");
            progressDialog.show();
        }

        @Override
        protected Boolean doInBackground(String... urls) {

            responses = performPostCall(urls[0]);

            return true;
        }

        protected void onPostExecute(Boolean result) {

            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
                progressDialog = null;

            }

            if (isAdded()) {

                if (responses != null) {

                    try {
                        JSONObject o = new JSONObject(responses);
                        JSONArray jarray = o.getJSONArray("deal");
                        Log.e("length", String.valueOf(jarray.length()));

                        for (int i = 0; i < jarray.length() - 1; i++) {     ////  last item in json doest have price and sell price
//                        this._company = _company;
//                        this._productName = _productName;
//                        this._productImage = _productImage;
//                        this._saleprice = _saleprice;
//                        this._actualPrice = _actualPrice;
//                        this._saleDetails = _saleDetails;
//                        this._companyLogo = _companyLogo;
//                        this._discount = _discount;

                            JSONObject object = jarray.getJSONObject(i);
                            if (!object.getString("price").equalsIgnoreCase("")) {    ////  last item in json doest have price and sell price
                                String _company = object.getString("store-name");
                                String _productName = object.getString("title");
                                String _productImage = object.getString("image");
                                String _appendedImage = "http:" + _productImage;
                                String _saleprice = object.getString("price");
                                StringBuilder stringBuilder = new StringBuilder(_saleprice);
                                stringBuilder.delete(0, 3);
                                _saleprice = stringBuilder.toString();
                                String _actualPrice = object.getString("disc-price");
                                StringBuilder stringBuilder1 = new StringBuilder(_actualPrice);
                                stringBuilder1.delete(0, 3);
                                _actualPrice = stringBuilder1.toString();
                                String _saleDetails = "buy one get one free";

                                Bitmap _companyLogo = BitmapFactory.decodeResource(getResources(), R.drawable.amazonlogo);
                                int discal = Integer.parseInt(_saleprice) * Integer.parseInt(_actualPrice);

                                String _discount = String.valueOf(discal);

                                offersModels.add(new OffersModel(_company, _productName, _appendedImage, _saleprice, _actualPrice, _saleDetails, BITMAP_RESIZER(_companyLogo, 75, 75), _discount));
                            }
                        }

                        Log.e("Length", String.valueOf(offersModels.size()));



                        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

                       adapter = new OfferAdapter(getActivity(), offersModels);
                        recyclerView.setAdapter(adapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(getActivity(), "Unable to load data", Toast.LENGTH_LONG).show();
                }
            }
        }
    }





    public String performPostCall(String requestURL) {

        URL url;
        String response = "";
        try {
            url = new URL(requestURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(30000);
            conn.setConnectTimeout(30000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            OutputStream os = conn.getOutputStream();
            os.close();

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            } else {
                response = "Server Not Responding";

                // throw new HttpException(responseCode + "");
            }
            conn.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }

        //   Log.e("response ", response);
        return response;
    }
    ///// function to remove the dupilicate
    public static String[] removeDuplicates(String[] arr) {
        return new HashSet<String>(Arrays.asList(arr)).toArray(new String[0]);
    }


}

