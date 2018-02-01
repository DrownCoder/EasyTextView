package com.study.xuan.easytextview;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.study.xuan.library.widget.EasyTextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EasyTextView textView;
    private int[] color = new int[]{
            Color.parseColor("#ff2193"),
            Color.parseColor("#192193"),
            Color.parseColor("#dcdcdc")
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (EasyTextView) findViewById(R.id.easyText);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setTextLeftColor(Color.GREEN);
                textView.setTextLeftSize(24);
                //textView.setTextRightSize(24);

            }
        });
        final List<String> data = new ArrayList();
        for (int i = 0; i < 100; i++) {
            data.add("第" + i + "个");
        }
        RecyclerView rcy = (RecyclerView) findViewById(R.id.rcy);
        rcy.setLayoutManager(new LinearLayoutManager(this));
        rcy.setAdapter(new RecyclerView.Adapter<ViewHolder>() {
            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new ViewHolder(LayoutInflater.from(MainActivity.this).inflate(R.layout
                        .item_layout, parent, false));
            }

            @Override
            public void onBindViewHolder(ViewHolder holder, int position) {
                holder.tv.setText(data.get(position));
                holder.tv.setIconColor(color[position % 3]);
            }

            @Override
            public int getItemCount() {
                return data.size();
            }
        });

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        EasyTextView tv;

        public ViewHolder(View root) {
            super(root);
            tv = root.findViewById(R.id.easyText);
        }
    }

}
