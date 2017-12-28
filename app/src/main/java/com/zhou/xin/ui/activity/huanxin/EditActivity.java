package com.zhou.xin.ui.activity.huanxin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zhou.xin.R;
import com.zhou.xin.base.BaseActivity;

import butterknife.BindView;

public class EditActivity extends BaseActivity {

    @BindView(R.id.edittext) EditText editText;
    @BindView(R.id.btn_save) Button btn_save;


    @Override
    public int getLayout() {
        return R.layout.activity_edit;
    }

    @Override
    public void initV() {
        String title = getIntent().getStringExtra("title");
        String data = getIntent().getStringExtra("data");
        Boolean editable = getIntent().getBooleanExtra("editable", false);
        if(title != null)
            ((TextView)findViewById(R.id.tv_title)).setText(title);
        if(data != null)
            editText.setText(data);

        editText.setEnabled(editable);
        editText.setSelection(editText.length());
        btn_save.setEnabled(editable);
    }

    public void save(View view){
        setResult(RESULT_OK, new Intent().putExtra("data", editText.getText().toString()));
        finish();
    }

    public void back(View view) {
        finish();
    }
}
