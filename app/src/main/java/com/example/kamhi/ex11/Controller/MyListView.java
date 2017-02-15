package com.example.kamhi.ex11.Controller;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.ContextMenu;
import android.widget.EditText;
import android.widget.ListView;

/**
 * Created by Kamhi on 9/12/2016.
 */

public class MyListView extends ListView{

    public MyListView(Context context) {
        super(context);
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MyListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected ContextMenu.ContextMenuInfo getContextMenuInfo() {
        return new myMenuInfo(this);
    }

    public static class myMenuInfo implements ContextMenu.ContextMenuInfo{

        public ListView listView;

        public myMenuInfo(ListView listView) {
            this.listView = listView;        }
    }

}
