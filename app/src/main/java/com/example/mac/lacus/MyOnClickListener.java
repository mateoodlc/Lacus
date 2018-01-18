package com.example.mac.lacus;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by jufe9 on 18/01/2018.
 */

public class MyOnClickListener implements View.OnClickListener {

    Context context;

    RecyclerView mRecyclerView;
    ArrayList<CasoVeeduria> mList;

    public MyOnClickListener() {

    }

    public MyOnClickListener(Context context, RecyclerView mRecyclerView, ArrayList<CasoVeeduria> mList) {

        this.context = context;
        this.mRecyclerView = mRecyclerView;
        this.mList = mList;

    }

    @Override
    public void onClick(View v) {

        int itemPosition = mRecyclerView.getChildLayoutPosition(v);
        //String item = mList.get(itemPosition);

        Intent intent = new Intent(context, GestionInformacion.class);

        intent.putExtra("ID_CASO", mList.get(itemPosition).getId());

        context.startActivity(intent);
        //context.finish();

    }

}
