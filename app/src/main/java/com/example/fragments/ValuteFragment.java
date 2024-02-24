package com.example.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

public class ValuteFragment extends Fragment {
    Handler handler;
    ArrayList<Valute> valutes = new ArrayList<>();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataThread dataThread = new DataThread();
        dataThread.start();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.RV);
        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);

                valutes = (ArrayList<Valute>) msg.obj;
                ValuteAdapter valuteAdapter = new ValuteAdapter(valutes);
                recyclerView.setAdapter(valuteAdapter);
            }
        };

        SwipeRefreshLayout refreshLayout = view.findViewById(R.id.refresh);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recyclerView.removeAllViews();
                valutes.clear();
                DataThread dataThread = new DataThread();
                dataThread.start();
                if (refreshLayout.isRefreshing()) {
                    refreshLayout.setRefreshing(false);
                }
            }
        });

        return view;
    }

    class DataThread extends Thread {
        ArrayList<Valute> valutes = new ArrayList<>();
        @Override
        public void run() {
            super.run();
            try {
                URL infolink = new URL("https://www.cbr-xml-daily.ru/daily_json.js");
                URL picturelink = new URL("https://gist.githubusercontent.com/sanchezzzhak/8606e9607396fb5f8216/raw/8a7209a4c1f4728314ef4208abc78be6e9fd5a2f/ISO3166_RU.json");
                String infoString = "";
                String pictureString = "";
                Bitmap bitm = null;

                Scanner in = new Scanner(infolink.openStream());
                while (in.hasNext()) {
                    infoString += in.nextLine();
                }
                in.close();
                in = new Scanner(picturelink.openStream());
                while (in.hasNext()) {
                    pictureString += in.nextLine();
                }
                in.close();

                JSONObject jsonInfo = new JSONObject(infoString);
                JSONArray jsonPicture = new JSONArray(pictureString);
                JSONObject jsonValutes = jsonInfo.getJSONObject("Valute");
                for (int i = 0; i < jsonValutes.names().length(); i++) {
                    JSONObject jsonVal = jsonValutes.getJSONObject(jsonValutes.names().getString(i));
                    String charCode = jsonVal.getString("CharCode").substring(0, 2);
                    for (int j = 0; j < jsonPicture.length(); j++) {
                        if (jsonPicture.getJSONObject(j).getString("iso_code2").equals(charCode)) {
                            String picURL = jsonPicture.getJSONObject(j).getString("flag_url");
                            URL url = new URL("https:" + picURL);

                            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
                            con.setConnectTimeout(1500);
                            con.setReadTimeout(1500);
                            con.connect();

                            int responseCode = con.getResponseCode();
                            if (responseCode == 200) {
                                InputStream inputStream = con.getInputStream();
                                bitm = BitmapFactory.decodeStream(inputStream);
                            }
                        }
                    }
                    valutes.add(new Valute(jsonVal.getString("Name"), jsonVal.getString("Value"), bitm));
                }

                Message msg = new Message();
                msg.obj = valutes;
                handler.sendMessage(msg);

            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
