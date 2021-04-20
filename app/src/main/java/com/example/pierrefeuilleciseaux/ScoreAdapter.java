package com.example.pierrefeuilleciseaux;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder> {

    private Context context;
    private Cursor cursor;


    public ScoreAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
    }

    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ScoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater;
        layoutInflater = LayoutInflater.from(context);
        View itemView = layoutInflater.inflate(R.layout.scores_list, parent, false);
        ScoreViewHolder scoreViewHolder = new ScoreViewHolder(itemView);
        return scoreViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreViewHolder holder, int position) {
        cursor.moveToPosition(position);
        holder.tv_score.setText(String.valueOf(cursor.getInt(cursor.getColumnIndex("scorePlayer"))));
        holder.tv_name.setText(cursor.getString(cursor.getColumnIndex("namePlayer")));
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public class ScoreViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_name;
        private TextView tv_score;


        public ScoreViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.textView_NameScoreList);
            tv_score = itemView.findViewById(R.id.textView_ScoreList);
        }
    }
}
