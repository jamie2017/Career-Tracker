package com.example.username.careertracker;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.Arrays;
import java.util.List;

public class ComListActivity extends AppCompatActivity {
    private static final int HIGHLIGHT_COLOR = 0x999be6ff;

    // list of data items
    private List<ListData> mDataList = Arrays.asList(
            new ListData("Microsoft"),
            new ListData("Oracle"),
            new ListData("WMware"),
            new ListData("Adobe"),
            new ListData("Salesforce"),
            new ListData("Symantec"),
            new ListData("IBM"),
            new ListData("Apple"),
            new ListData("Google"),
            new ListData("Linkedin"),
            new ListData("Amazon"),
            new ListData("Facebook"),
            new ListData("Yelp"),
            new ListData("Uber"),
            new ListData("Airbnb"),
            new ListData("Dropbox"),
            new ListData("SAP"),
            new ListData("Marketo"),
            new ListData("Slack"),
            new ListData("Yahoo"),
            new ListData("NetApp"),
            new ListData("Nokia"),
            new ListData("Dell"),
            new ListData("WalmartLab"),
            new ListData("Samsung"),
            new ListData("HP"),
            new ListData("Intel"),
            new ListData("Cisco"),
            new ListData("Qualcomm"),
            new ListData("eBay"),
            new ListData("Paypal"),
            new ListData("Pinterest")
    );

    // declare the color generator and drawable builder
    private ColorGenerator mColorGenerator = ColorGenerator.MATERIAL;
    private TextDrawable.IBuilder mDrawableBuilder;
    private ListView listView;
    private SampleAdapter listContactAdp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_com_activity_list);

        Intent intent = getIntent();
        mDrawableBuilder = TextDrawable.builder()
                        .beginConfig()
                        .withBorder(4)
                        .endConfig()
                        .round();
//        int type = intent.getIntExtra(AddCompanyActivity.TYPE, DrawableProvider.SAMPLE_RECT);
//
//        // initialize the builder based on the "TYPE"
//        switch (type) {
//            case DrawableProvider.SAMPLE_RECT:
//                mDrawableBuilder = TextDrawable.builder()
//                        .rect();
//                break;
//            case DrawableProvider.SAMPLE_ROUND_RECT:
//                mDrawableBuilder = TextDrawable.builder()
//                        .roundRect(10);
//                break;
//            case DrawableProvider.SAMPLE_ROUND:
//                mDrawableBuilder = TextDrawable.builder()
//                        .round();
//                break;
//            case DrawableProvider.SAMPLE_RECT_BORDER:
//                mDrawableBuilder = TextDrawable.builder()
//                        .beginConfig()
//                        .withBorder(4)
//                        .endConfig()
//                        .rect();
//                break;
//            case DrawableProvider.SAMPLE_ROUND_RECT_BORDER:
//                mDrawableBuilder = TextDrawable.builder()
//                        .beginConfig()
//                        .withBorder(4)
//                        .endConfig()
//                        .roundRect(10);
//                break;
//            case DrawableProvider.SAMPLE_ROUND_BORDER:
//                mDrawableBuilder = TextDrawable.builder()
//                        .beginConfig()
//                        .withBorder(4)
//                        .endConfig()
//                        .round();
//                break;
//        }

        // init the list view and its adapter
        listView = (ListView) findViewById(R.id.listView);
        listContactAdp = new SampleAdapter();
        listView.setAdapter(listContactAdp);


    }


//    @Override
//    protected void onStart(){
//        super.onStart();
//        listView = (ListView) findViewById(R.id.listView);
//        listContactAdp = new SampleAdapter();
//        listView.setAdapter(listContactAdp);
//    }
    private class SampleAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mDataList.size();
        }

        @Override
        public ListData getItem(int position) {
            return mDataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(ComListActivity.this, R.layout.list_item_layout, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            ListData item = getItem(position);

            // provide support for selected state
            updateCheckedState(holder, item);
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // when the image is clicked, update the selected state
                    ListData data = getItem(position);
                    data.setChecked(!data.isChecked);
                    updateCheckedState(holder, data);
                }
            });
            holder.textView.setText(item.data);

            return convertView;
        }

        private void updateCheckedState(ViewHolder holder, ListData item) {
            if (item.isChecked) {
                holder.imageView.setImageDrawable(mDrawableBuilder.build(" ", 0xff616161));
                holder.view.setBackgroundColor(HIGHLIGHT_COLOR);
                holder.checkIcon.setVisibility(View.VISIBLE);
            }
            else {
                TextDrawable drawable = mDrawableBuilder.build(String.valueOf(item.data.charAt(0)), mColorGenerator.getColor(item.data));
                holder.imageView.setImageDrawable(drawable);
                holder.view.setBackgroundColor(Color.TRANSPARENT);
                holder.checkIcon.setVisibility(View.GONE);
            }
        }


    }

    private static class ViewHolder {

        private View view;

        private ImageView imageView;

        private TextView textView;

        private ImageView checkIcon;

        private ViewHolder(View view) {
            this.view = view;
            imageView = (ImageView) view.findViewById(R.id.imageView);
            textView = (TextView) view.findViewById(R.id.textView);
            checkIcon = (ImageView) view.findViewById(R.id.check_icon);
        }
    }

    private static class ListData {

        private String data;

        private boolean isChecked;

        public ListData(String data) {
            this.data = data;
        }

        public void setChecked(boolean isChecked) {
            this.isChecked = isChecked;
        }

    }
}
