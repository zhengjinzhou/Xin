package com.zhou.xin.ui.activity.love;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zhou.xin.Constant;
import com.zhou.xin.R;
import com.zhou.xin.base.BaseActivity;
import com.zhou.xin.utils.SpUtil;
import com.zhou.xin.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 修改密码
 */
public class ChangeActivity extends BaseActivity {

    @BindView(R.id.tv_head) TextView tv_head;
    @BindView(R.id.et_old) EditText et_old;
    @BindView(R.id.et_new) EditText et_new;
    @BindView(R.id.et_confirm) EditText et_confirm;

    @Override
    protected int getLayout() {
        return R.layout.activity_change;
    }

    @Override
    protected void init() {
        tv_head.setText("修改密码");
    }

    @OnClick({R.id.back,R.id.bt_change}) void onClick(View view){
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.bt_change:
                //修改密码
                changePassword();
                break;
        }
    }

    /**
     * 修改密码
     */
    private void changePassword() {
        String etOld = et_old.getText().toString().trim();
        String etNew = et_new.getText().toString().trim();
        String etConfirm = et_confirm.getText().toString().trim();
        if (TextUtils.isEmpty(etOld)){
            ToastUtil.show(getApplicationContext(),"原密码不能为空");
            return;
        }
        if (TextUtils.isEmpty(etNew)){
            ToastUtil.show(getApplicationContext(),"新密码不能为空");
            return;
        }
        if (TextUtils.isEmpty(etConfirm)){
            ToastUtil.show(getApplicationContext(),"确认密码不能为空");
            return;
        }
        if (!etNew.equals(etConfirm)){
            ToastUtil.show(getApplicationContext(),"新密码输入不一致");
            return;
        }

        if (SpUtil.getString(getApplicationContext(), Constant.PASSWORD,"") != null){
            if (!SpUtil.getString(getApplicationContext(), Constant.PASSWORD,"").equals(etOld)){
                ToastUtil.show(getApplicationContext(),"原密码不正确");
                return;
            }
        }else {
            ToastUtil.show(getApplicationContext(),"原密码没有？");
            return;
        }
        //余下修改成功之后记得再次保存一次原密码  SpUtil.putString(getApplicationContext(), Constant.PASSWORD,password); 
    }
}
