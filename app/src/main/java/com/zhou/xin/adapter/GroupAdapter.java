package com.zhou.xin.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.chat.EMGroup;
import com.zhou.xin.R;

import java.util.List;

/**
 * Created by zhou on 2017/12/20.
 */

public class GroupAdapter extends ArrayAdapter<EMGroup> {

    private LayoutInflater inflater;
    private String newGroup;

    public GroupAdapter(Context context, int res, List<EMGroup> groups) {
        super(context, res, groups);
        this.inflater = LayoutInflater.from(context);
        newGroup = context.getResources().getString(R.string.The_new_group_chat);
    }

    @Override
    public int getViewTypeCount() {
        return 4;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        } else if (position == 1) {
            return 1;
        } else {
            return 2;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (getItemViewType(position) == 0) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.em_search_bar_with_padding, parent, false);
            }
            final EditText query = (EditText) convertView.findViewById(R.id.query);
            final ImageButton clearSearch = (ImageButton) convertView.findViewById(R.id.search_clear);
            query.addTextChangedListener(new TextWatcher() {
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    getFilter().filter(s);
                    if (s.length() > 0) {
                        clearSearch.setVisibility(View.VISIBLE);
                    } else {
                        clearSearch.setVisibility(View.INVISIBLE);
                    }
                }

                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                public void afterTextChanged(Editable s) {
                }
            });
            clearSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    query.getText().clear();
                }
            });
        } else if (getItemViewType(position) == 1) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.em_row_add_group, parent, false);
            }
            ((ImageView) convertView.findViewById(R.id.avatar)).setImageResource(R.drawable.em_create_group);
            ((TextView) convertView.findViewById(R.id.name)).setText(newGroup);
        } else {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.em_row_group, parent, false);
            }
            ((TextView) convertView.findViewById(R.id.name)).setText(getItem(position - 2).getGroupName());

        }

        return convertView;
    }

    @Override
    public int getCount() {
        return super.getCount() + 2;
    }

}
