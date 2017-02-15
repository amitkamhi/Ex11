package com.example.kamhi.ex11.Model;

import android.content.Context;
import android.content.res.AssetManager;

import com.example.kamhi.ex11.View.MyApplication;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Kamhi on 24/1/2017.
 */

public class DataLoader {

    public static ArrayList<Country> getCountries(){

        ArrayList<Country> data = null;
        Context context = MyApplication.getAppContext();
        try{
            InputStream inputStream = openAssetDataStream(context);
            data = XMLParser.parseFromStream(inputStream);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;

    }

    public static InputStream openAssetDataStream(Context context){
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = null;
        try{
            inputStream = assetManager.open("countries.xml");
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return inputStream;
    }
}
