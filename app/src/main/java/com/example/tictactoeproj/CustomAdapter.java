package com.example.tictactoeproj;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    private Context mContext;
    DBHelper DB;
    private ArrayList<String> playerone=new ArrayList<>();
    private ArrayList<String> playertwo=new ArrayList<>();
    private ArrayList<String> winner=new ArrayList<>();





    public CustomAdapter(Context mContext,ArrayList<String> playerone,ArrayList<String> playertwo,ArrayList<String> winner) {
        this.mContext = mContext;
        this.playerone=playerone;
        this.playertwo=playertwo;
        this.winner=winner;

    }


    @Override
    public int getCount() {
        return playerone.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint("Range")
    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        final ViewHolder holder;
        DB=new DBHelper(mContext);
        LayoutInflater layoutInflater;
        if (convertView==null){
            layoutInflater=(LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=layoutInflater.inflate(R.layout.layout,null);
            holder=new ViewHolder();
            holder.playerone=convertView.findViewById(R.id.tvplayrone);
            holder.playertwo=convertView.findViewById(R.id.tvplayertwo);
            holder.winner=convertView.findViewById(R.id.tvwinner);
            convertView.setTag(holder);
        }
        else{
            holder=(ViewHolder) convertView.getTag();

        }
        holder.playerone.setText(playerone.get(position));
        holder.playertwo.setText(playertwo.get(position));
        holder.winner.setText(winner.get(position));



        return convertView;



    }
}
