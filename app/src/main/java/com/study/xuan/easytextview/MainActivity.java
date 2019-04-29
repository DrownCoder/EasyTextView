package com.study.xuan.easytextview;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.UnderlineSpan;
import android.view.View;

import com.study.xuan.library.widget.EasyTextView;

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
        /*SpannableStringBuilder stringBuilder = new SpannableStringBuilder("呵呵呵呵呵");
        stringBuilder.setSpan(new UnderlineSpan(), 1, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        SpannableStringBuilder insert = new SpannableStringBuilder("你好啊啊啊");
        insert.setSpan(new UnderlineSpan(), 1, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        stringBuilder.insert(0, insert);*/
        /*TextView text = (TextView) findViewById(R.id.text);
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder("呵呵呵呵呵");
        SpannableStringBuilder insert = new SpannableStringBuilder("你好啊啊啊");
        insert.setSpan(new UnderlineSpan(), 1, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        stringBuilder.insert(0, insert);
        text.setText(stringBuilder);*/
        //textView.setTextRight(insert);
        textView.spanLeft(new UnderlineSpan(), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                .spanLeft(new StrikethroughSpan(), 1, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                .spanLeft(new ForegroundColorSpan(Color.RED), 1, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                .spanRight(new ForegroundColorSpan(Color.RED), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                .spanRight(new ForegroundColorSpan(Color.BLUE), 1, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                .build();
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setTextLeftColor(Color.GREEN);
                textView.setTextLeftSize(24);
                //textView.setTextRightSize(24);

            }
        });
        /*final List<String> data = new ArrayList();
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
*/
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        EasyTextView tv;

        public ViewHolder(View root) {
            super(root);
            tv = root.findViewById(R.id.easyText);
        }
    }

}
