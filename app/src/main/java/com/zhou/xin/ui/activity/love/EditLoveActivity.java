package com.zhou.xin.ui.activity.love;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.IdRes;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.ta.utdid2.android.utils.StringUtils;
import com.yuyh.library.imgsel.ImageLoader;
import com.yuyh.library.imgsel.ImgSelActivity;
import com.yuyh.library.imgsel.ImgSelConfig;
import com.zhou.xin.Constant;
import com.zhou.xin.R;
import com.zhou.xin.base.App;
import com.zhou.xin.base.BaseActivity;
import com.zhou.xin.base.other.PermissionsResultListener;
import com.zhou.xin.bean.BaseInfo;
import com.zhou.xin.bean.PersonalBean;
import com.zhou.xin.bean.SelectBean;
import com.zhou.xin.bean.UserInfo;
import com.zhou.xin.ui.activity.huanxin.MainActivity;
import com.zhou.xin.utils.CurrentTimeUtil;
import com.zhou.xin.utils.DateUtil;
import com.zhou.xin.utils.GlideRoundTransform;
import com.zhou.xin.utils.Md5Util;
import com.zhou.xin.utils.PersonalFormTools;
import com.zhou.xin.utils.ToastUtil;
import com.zhou.xin.utils.actionPhotoUtil;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class EditLoveActivity extends BaseActivity {

    private static final String TAG = "EditActivity";
    @BindView(R.id.img_1) ImageView img1;
    @BindView(R.id.img_2) ImageView img2;
    @BindView(R.id.img_3) ImageView img3;
    @BindView(R.id.img_4) ImageView img4;
    @BindView(R.id.img_5) ImageView img5;
    @BindView(R.id.del_1) ImageView del_1;
    @BindView(R.id.del_2) ImageView del_2;
    @BindView(R.id.del_3) ImageView del_3;
    @BindView(R.id.del_4) ImageView del_4;
    @BindView(R.id.del_5) ImageView del_5;
    @BindView(R.id.ll_details) LinearLayout ll_details;
    @BindView(R.id.bt_next) Button bt_next;
    @BindView(R.id.bt_skip) Button bt_skip;
    @BindView(R.id.radioGroup) RadioGroup radioGroup;
    @BindView(R.id.tv_major)TextView tv_major;
    @BindView(R.id.tv_conste) TextView tv_conste;
    @BindView(R.id.tv_travels) TextView tv_travels;
    @BindView(R.id.tv_labels) TextView tv_labels;
    @BindView(R.id.tv_books) TextView tv_books;
    @BindView(R.id.tv_video) TextView tv_video;
    @BindView(R.id.tv_foots) TextView tv_foots;
    @BindView(R.id.tv_musics) TextView tv_musics;
    @BindView(R.id.tv_sport) TextView tv_sport;
    @BindView(R.id.tv_birthday) TextView tv_birthday;
    @BindView(R.id.et_nickname) EditText et_nickname;
    @BindView(R.id.et_realname) EditText et_realname;
    @BindView(R.id.et_wechat) EditText et_wechat;
    @BindView(R.id.tv_head) TextView tv_head;
    @BindView(R.id.et_autograph) EditText et_autograph;


    private int index = 0;
    private int mark = 0;
    private static final int REQUEST_CODE = 1000;
    private static final int PER_REQUEST_CODE = 1;
    private List<BaseInfo> data = new ArrayList<>();
    private List<ImageView> imgsView = new ArrayList<>();
    private List<ImageView> delView = new ArrayList<>();
    private OptionsPickerView optionsPickerView;

    private List<String> listMajor;
    private List<String> listCity;
    private List<String> listProvince;
    private List<String> constellationList;
    private List<String> listLabel;
    private List<String> listSport;
    private List<String> listMusic;
    private List<String> listFood;
    private List<String> listFilm;
    private List<String> listBook;
    private List<String> listTravel;
    private List<String> uploadLabel;
    private List<String> uploadFilm;
    private List<String> uploadFood;
    private List<String> uploapBook;
    private List<String> uploadSport;
    private List<String> uploadMusic;
    private List<String> uploadTravel;

    private Map<String, String> mapLabel;
    private Map<String, String> mapSport;
    private Map<String, String> mapMusic;
    private Map<String, String> mapFoot;
    private Map<String, String> mapFilm;
    private Map<String, String> mapBook;
    private Map<String, String> mapTravel;
    private Map<String, Integer> mapMajor;
    private Map<Integer, String> mapCategory;
    private Map<Integer, String> mapProvince;
    private Map<String, String> constellationMap;
    private Map<String, List<String>> mapCity;

    private String label_others = "暂无";
    private String character_others = "暂无";
    private String sport_others = "暂无";
    private String music_others = "暂无";
    private String food_others = "暂无";
    private String film_others = "暂无";
    private String book_others = "暂无";
    private String travel_others = "暂无";


    private String brithday = DateUtil.lineDate(new Date());
    private int sex = 1;//性别

    /**
     * ======================================================
     **/
    private Integer majorID = 1;
    private String constellationID = "";

    /**
     * ======================================================
     **/
    @Override
    protected int getLayout() {
        return R.layout.activity_edit_love;
    }

    @Override
    protected void init() {
        tv_head.setText("完善信息");

        ll_details.setVisibility(View.GONE);
        initData();
    }

    private void initData() {

        data.add(new BaseInfo(false, ""));
        data.add(new BaseInfo(false, ""));
        data.add(new BaseInfo(false, ""));
        data.add(new BaseInfo(false, ""));
        data.add(new BaseInfo(false, ""));
        //data.add(new BaseInfo(false, ""));

        imgsView.add(img1);
        imgsView.add(img2);
        imgsView.add(img3);
        imgsView.add(img4);
        imgsView.add(img5);

        delView.add(del_1);
        delView.add(del_2);
        delView.add(del_3);
        delView.add(del_4);
        delView.add(del_5);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                if (checkedRadioButtonId == 2131296688)
                    sex = 1;
                if (checkedRadioButtonId == 2131296686)
                    sex = 0;
            }
        });
        if (App.getInstance() == null) return;
        if (App.getInstance().getSelectBean() == null) return;
        SelectBean selectBean = App.getInstance().getSelectBean();
        List<SelectBean.CategoryListBean> categoryList = selectBean.getCategoryList();

        if (selectBean == null) return;
        mapCategory = new HashMap<>();
        mapMajor = new HashMap<>();
        mapProvince = new HashMap<>();
        mapCity = new HashMap<>();

        listMajor = new ArrayList<>();//学院
        //城市
        listCity = new ArrayList<>();
        //省份
        listProvince = new ArrayList<>();
        //星座类型
        constellationList = new ArrayList<>();
        constellationMap = new HashMap<>();

        //我的个性标签类型
        listLabel = new ArrayList<>();
        uploadLabel = new ArrayList<>();
        mapLabel = new HashMap<>();
        //我喜欢的运动类型
        listSport = new ArrayList<>();
        uploadSport = new ArrayList<>();
        mapSport = new HashMap<>();
        //我喜欢的音乐类型
        listMusic = new ArrayList<>();
        uploadMusic = new ArrayList<>();
        mapMusic = new HashMap<>();
        //我喜欢的食物类型
        listFood = new ArrayList<>();
        uploadFood = new ArrayList<>();
        mapFoot = new HashMap<>();
        //我喜欢的电影类型
        listFilm = new ArrayList<>();
        uploadFilm = new ArrayList<>();
        mapFilm = new HashMap<>();
        //我喜欢的书和动漫类型
        listBook = new ArrayList<>();
        uploapBook = new ArrayList<>();
        mapBook = new HashMap<>();
        //我的旅行足迹
        listTravel = new ArrayList<>();
        uploadTravel = new ArrayList<>();
        mapTravel = new HashMap<>();

        for (int i = 0; i < categoryList.get(0).getTypes().size(); i++) {
            listLabel.add(categoryList.get(0).getTypes().get(i).getName());
            mapLabel.put(categoryList.get(0).getTypes().get(i).getName(), categoryList.get(0).getTypes().get(i).getKey());
        }

        for (int i = 0; i < categoryList.get(1).getTypes().size(); i++) {
            listSport.add(categoryList.get(1).getTypes().get(i).getName());
            mapSport.put(categoryList.get(1).getTypes().get(i).getName(), categoryList.get(1).getTypes().get(i).getKey());
        }
        for (int i = 0; i < categoryList.get(2).getTypes().size(); i++) {
            listMusic.add(categoryList.get(2).getTypes().get(i).getName());
            mapMusic.put(categoryList.get(2).getTypes().get(i).getName(), categoryList.get(2).getTypes().get(i).getKey());
        }
        for (int i = 0; i < categoryList.get(3).getTypes().size(); i++) {
            listFood.add(categoryList.get(3).getTypes().get(i).getName());
            mapFoot.put(categoryList.get(3).getTypes().get(i).getName(), categoryList.get(3).getTypes().get(i).getKey());
        }
        for (int i = 0; i < categoryList.get(4).getTypes().size(); i++) {
            listFilm.add(categoryList.get(4).getTypes().get(i).getName());
            mapFilm.put(categoryList.get(4).getTypes().get(i).getName(), categoryList.get(4).getTypes().get(i).getKey());
        }
        for (int i = 0; i < categoryList.get(5).getTypes().size(); i++) {
            listBook.add(categoryList.get(5).getTypes().get(i).getName());
            mapBook.put(categoryList.get(5).getTypes().get(i).getName(), categoryList.get(5).getTypes().get(i).getKey());
        }
        for (int i = 0; i < categoryList.get(6).getTypes().size(); i++) {
            listTravel.add(categoryList.get(6).getTypes().get(i).getName());
            mapTravel.put(categoryList.get(6).getTypes().get(i).getName(), categoryList.get(6).getTypes().get(i).getKey());
        }
        for (int i = 0; i < categoryList.get(7).getTypes().size(); i++) {
            constellationList.add(categoryList.get(7).getTypes().get(i).getName());
            constellationMap.put(categoryList.get(7).getTypes().get(i).getName(), categoryList.get(7).getTypes().get(i).getKey());
        }

        for (int i = 0; i < selectBean.getMajorList().size(); i++) {
            mapMajor.put(selectBean.getMajorList().get(i).getMajorName(), selectBean.getMajorList().get(i).getId());
            listMajor.add(selectBean.getMajorList().get(i).getMajorName());
        }
        for (int i = 0; i < selectBean.getProvinceList().size(); i++) {
            listProvince.add(selectBean.getProvinceList().get(i).getName());
            for (int j = 0; j < selectBean.getProvinceList().get(i).getCitys().size(); j++) {
                listCity.add(selectBean.getProvinceList().get(i).getCitys().get(j).getName());
            }
            mapCity.put(selectBean.getProvinceList().get(i).getName(), listCity);//好复杂的省份嵌套城市
        }
    }

    @OnClick({R.id.bt_next, R.id.bt_skip, R.id.back, R.id.layout_1, R.id.layout_2, R.id.layout_3, R.id.layout_4
            , R.id.layout_5, R.id.del_1, R.id.del_2, R.id.del_3, R.id.del_4, R.id.del_5, R.id.bt_submit, R.id.tv_province_city
            , R.id.tv_sport, R.id.tv_musics, R.id.tv_foots, R.id.tv_video, R.id.tv_major, R.id.tv_conste,
            R.id.tv_books, R.id.tv_travels, R.id.tv_labels, R.id.tv_birthday})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_birthday:
                initTime(tv_birthday);
                break;
            case R.id.tv_travels:
                showMultiChioceDialog1(listTravel.toArray(new String[listTravel.size()]), uploadTravel, mapTravel, tv_travels);
                break;
            case R.id.tv_labels://我的个性标签
                showMultiChioceDialog(listLabel.toArray(new String[listLabel.size()]), uploadLabel, mapLabel, tv_labels);
                break;
            case R.id.tv_books:
                showMultiChioceDialog1(listBook.toArray(new String[listBook.size()]), uploapBook, mapBook, tv_books);
                break;
            case R.id.tv_video:
                showMultiChioceDialog1(listFilm.toArray(new String[listFilm.size()]), uploadFilm, mapFilm, tv_video);
                break;
            case R.id.tv_foots:
                showMultiChioceDialog1(listFood.toArray(new String[listFood.size()]), uploadFood, mapFoot, tv_foots);
                break;
            case R.id.tv_musics:
                showMultiChioceDialog1(listMusic.toArray(new String[listMusic.size()]), uploadMusic, mapMusic, tv_musics);
                break;
            case R.id.tv_sport:
                showMultiChioceDialog(listSport.toArray(new String[listSport.size()]), uploadSport, mapSport, tv_sport);
                break;
            case R.id.tv_province_city:

                break;
            case R.id.tv_conste:
                initOptionPicker("我的星座类型：", constellationList, tv_conste);
                optionsPickerView.setPicker(constellationList);
                optionsPickerView.show();
                break;
            case R.id.tv_major:
                initOptionPicker("我的学历：", listMajor, tv_major);
                optionsPickerView.setPicker(listMajor);
                optionsPickerView.show();
                break;
            //继续完善
            case R.id.bt_next:
                ll_details.setVisibility(View.VISIBLE);
                bt_next.setVisibility(View.GONE);
                bt_skip.setVisibility(View.GONE);
                break;
            //提交一次
            case R.id.bt_skip:
                submit();
                break;
            case R.id.bt_submit:
                submit();
                break;
            case R.id.back:
                finish();
                break;
            case R.id.del_1:
                setDelPhoto(1);
                break;
            case R.id.del_2:
                setDelPhoto(2);
                break;
            case R.id.del_3:
                setDelPhoto(3);
                break;
            case R.id.del_4:
                setDelPhoto(4);
                break;
            case R.id.del_5:
                setDelPhoto(5);
                break;
            case R.id.layout_1:
                openPhotoPermissions(1);
                break;
            case R.id.layout_2:
                openPhotoPermissions(2);
                break;
            case R.id.layout_3:
                openPhotoPermissions(3);
                break;
            case R.id.layout_4:
                openPhotoPermissions(4);
                break;
            case R.id.layout_5:
                openPhotoPermissions(5);
                break;
        }
    }

    /**
     * 选择生日
     *
     * @param et
     */
    private void initTime(final TextView et) {
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        startDate.set(1920, 0, 1);
        //endDate.set(selectedDate.get(Calendar.YEAR),selectedDate.get(Calendar.MONTH),selectedDate.get(Calendar.DATE));
        endDate.set(2022, 2, 2);
        new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                et.setText("生日:" + format.format(date));
                brithday = format.format(date);
            }
        })
                //年月日时分秒 的显示与否，不设置则默认全部显示
                .setType(new boolean[]{true, true, true, false, false, false})
                .setSubmitText("Ok")//确定按钮文字
                .setCancelText("Cancel")//取消按钮文字
                .setLabel("", "", "", "", "", "")
                .isCenterLabel(false)
                .setDividerColor(Color.DKGRAY)
                .setContentSize(16)
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setBackgroundId(0x00FFFFFF) //设置外部遮罩颜色
                .setDecorView(null)
                .build().show();
    }

    /**
     * 浪费了
     *
     * @param shuzu
     * @param uoload
     * @param map
     * @param tv
     */
    public void showMultiChioceDialog(final String[] shuzu, final List<String> uoload, final Map<String, String> map, final TextView tv) {
        final List<String> list = new ArrayList<>();
        //final EditText editText = new EditText(this);
        //editText.setHint("    若还有其他，请输入");
        //editText.setMaxLines(3);
        //editText.setBackground(getResources().getDrawable(R.drawable.btn_bg));
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //builder.setView(editText);
        builder.setTitle("多选对话框");
        builder.setIcon(R.mipmap.ic_launcher);
        final boolean[] checkedItems = new boolean[shuzu.length];/*设置多选默认状态*/
        builder.setMultiChoiceItems(shuzu, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {/*设置多选的点击事件*/
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                checkedItems[which] = isChecked;
                if (isChecked == true) {
                    list.add(shuzu[which]);
                    uoload.add(map.get(shuzu[which]));
                    Log.d(TAG, "onClick: " + uoload.toString());
                }
                if (isChecked == false) {
                    int i = list.indexOf(shuzu[which]);
                    list.remove(i);
                    uoload.remove(map.get(shuzu[which]));
                    Log.d(TAG, "onClick: " + uoload.toString());
                }
                // Toast.makeText(getApplicationContext(), shuzu[which]  + isChecked, Toast.LENGTH_SHORT).show();
            }
        });

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //System.out.println(editText.getText());
                System.out.println(list.toString());
                System.out.println("map" + uoload.toString());
                tv.setText(list.toString());
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }


    public void showMultiChioceDialog1(final String[] shuzu, final List<String> uoload, final Map<String, String> map, final TextView tv) {
        final List<String> list = new ArrayList<>();
        final EditText editText = new EditText(this);
        editText.setHint("    若还有其他，请输入");
        editText.setMaxLines(3);
        editText.setBackground(getResources().getDrawable(R.drawable.btn_bg));
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //builder.setView(editText);
        builder.setTitle("多选对话框");
        builder.setIcon(R.mipmap.ic_launcher);
        final boolean[] checkedItems = new boolean[shuzu.length];/*设置多选默认状态*/
        builder.setMultiChoiceItems(shuzu, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {/*设置多选的点击事件*/
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                checkedItems[which] = isChecked;
                if (isChecked == true) {
                    list.add(shuzu[which]);
                    uoload.add(map.get(shuzu[which]));
                    Log.d(TAG, "onClick: " + uoload.toString());
                }
                if (isChecked == false) {
                    int i = list.indexOf(shuzu[which]);
                    list.remove(i);
                    uoload.remove(map.get(shuzu[which]));
                    Log.d(TAG, "onClick: " + uoload.toString());
                }
                // Toast.makeText(getApplicationContext(), shuzu[which]  + isChecked, Toast.LENGTH_SHORT).show();
            }
        });

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.out.println(editText.getText());
                System.out.println(list.toString());
                System.out.println("map" + uoload.toString());
                tv.setText(list.toString() + editText.getText());
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    /**
     * 单项选择  星座和学历 完成
     *
     * @param string
     * @param list
     * @param txt
     */
    private void initOptionPicker(final String string, final List<String> list, final TextView txt) {
        optionsPickerView = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                txt.setText(string + list.get(options1));
                //同时获取相应的id，因为上传需要
                if (listMajor.contains(list.get(options1))) {
                    majorID = mapMajor.get(list.get(options1));
                }
                if (constellationList.contains(list.get(options1))) {
                    constellationID = constellationMap.get(list.get(options1));
                }
            }
        }).build();
    }

    /**
     * 简单上传 与完整上传
     */
    private void submit() {
        String nickname = et_nickname.getText().toString();
        if (TextUtils.isEmpty(nickname)) {
            ToastUtil.show(getApplicationContext(), "昵称不能为空");
            return;
        }
        if (!PersonalFormTools.getInstall().verf(data)) {
            ToastUtil.show(getApplicationContext(), "照片不能为空");
            return;
        }
        List<String> entity = getTureData(data);//照片地址相关
        OkHttpClient okHttpClient = new OkHttpClient();
        MultipartBody.Builder builider = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builider.addFormDataPart("uid", App.getInstance().getUserInfo().getUid());
        builider.addFormDataPart("token", App.getInstance().getUserInfo().getToken());
        builider.addFormDataPart("nickname", nickname);//昵称
        builider.addFormDataPart("sex", sex + "");//性别
        if (!TextUtils.isEmpty(et_realname.getText().toString().trim()))
            builider.addFormDataPart("username", et_realname.getText().toString().trim());//真实姓名
        if (brithday != DateUtil.lineDate(new Date())) {
            int age = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date())) - Integer.parseInt(brithday.substring(0, 4));//年龄用当前时间减去生日的时间
            builider.addFormDataPart("age", age + "");//年龄用当前时间减去生日的时间
            builider.addFormDataPart("birthday", brithday);//生日
        }
        builider.addFormDataPart("city", "");//城市id
        if (!TextUtils.isEmpty(et_wechat.getText().toString().trim()))
            builider.addFormDataPart("wechat", et_wechat.getText().toString().trim());//微信号
        if (majorID != null) {
            builider.addFormDataPart("major", majorID + "");//学历
        }
        if (!TextUtils.isEmpty(constellationID))
            builider.addFormDataPart("constellation", constellationID);//星座id
        if (!TextUtils.isEmpty(et_autograph.getText().toString().trim()))
            builider.addFormDataPart("autograph", et_autograph.getText().toString().trim());//签名
        if (uploadLabel.size() > 0)
            builider.addFormDataPart("labels[]", uploadLabel.toString().substring(1, uploadLabel.toString().length() - 1).replace(" ", ""));//个性标签
        //builider.addFormDataPart("label_others", label_others);
        if (uploadSport.size() > 0)
            builider.addFormDataPart("sports[]", uploadSport.toString().substring(1, uploadSport.toString().length() - 1).replace(" ", ""));//运动类型
        //builider.addFormDataPart("sport_others", sport_others);
        if (uploadMusic.size() > 0)
            builider.addFormDataPart("music[]", uploadMusic.toString().substring(1, uploadMusic.toString().length() - 1).replace(" ", ""));//音乐类型
        //builider.addFormDataPart("music_others", music_others);
        if (uploadFood.size() > 0)
            builider.addFormDataPart("foods[]", uploadFood.toString().substring(1, uploadFood.toString().length() - 1).replace(" ", ""));//食物
        //builider.addFormDataPart("food_others", food_others);
        if (uploadFilm.size() > 0)
            builider.addFormDataPart("films[]", uploadFilm.toString().substring(1, uploadFilm.toString().length() - 1).replace(" ", ""));//影视
        //builider.addFormDataPart("film_others", film_others);
        if (uploapBook.size() > 0)
            builider.addFormDataPart("books[]", uploapBook.toString().substring(1, uploapBook.toString().length() - 1).replace(" ", ""));//书籍
        //builider.addFormDataPart("book_others", book_others);
        builider.addFormDataPart("travels[]", uploadTravel.toString().substring(1, uploadTravel.toString().length() - 1).replace(" ", ""));//旅行足迹
        //builider.addFormDataPart("travel_others", travel_others);
        for (int i = 0; i < entity.size(); i++) {
            builider.addFormDataPart("photo", entity.get(i), RequestBody.create(MediaType.parse("image/*"), new File(entity.get(i))));
        }
        MultipartBody body = builider.build();
        dialog.show();
        Request request = new Request.Builder()
                .url(Constant.UPDATE_URL)
                .post(body)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                dialog.dismiss();
                Log.d(TAG, "修改会员信息onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Log.d(TAG, "更新信息成功: " + string);
                getResult();
            }
        });
    }

    private void getResult() {
        String uid = App.getInstance().getUserInfo().getUid();
        String token = App.getInstance().getUserInfo().getToken();
        String opt = "5";
        String _t = CurrentTimeUtil.nowTime();
        String joint = "_t=" + _t + "&opt=" + opt + "&token=" + token + "&uid=" + uid + Constant.APP_ENCRYPTION_KEY;
        String _s = Md5Util.encoder(joint);
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody build = new FormBody.Builder()
                .add("uid", uid)
                .add("token", token)
                .add("opt", opt)
                .add("_t", _t)
                .add("_s", _s)
                .build();
        Request request = new Request.Builder().url(Constant.LOGIN_URL).post(build).build();
        Call call2 = okHttpClient.newCall(request);
        call2.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                Log.d(TAG, "---------onFailure: " + e.getMessage());
                dialog.dismiss();
            }

            @Override
            public void onResponse(Call call, Response res) throws IOException {
                String string = res.body().string();
                dialog.dismiss();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.show(getApplicationContext(), "更新信息成功！");
                    }
                });
                Log.d(TAG, "获取新的信息成功: " + string);
                PersonalBean personalBean = new Gson().fromJson(string, PersonalBean.class);
                App.getInstance().setPersonalBean(personalBean);
            }
        });
    }

    private void openPhotoPermissions(final int i) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            performRequestPermissions(getResources().getString(R.string.permission_desc)
                    , new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}
                    , PER_REQUEST_CODE, new PermissionsResultListener() {
                        @Override
                        public void onPermissionGranted() {
                            index = i;
                            actionPhotoUtil.actionPhoto(EditLoveActivity.this,REQUEST_CODE);
                        }

                        @Override
                        public void onPermissionDenied() {
                            //finish();
                        }
                    });
            return;
        }
        this.index = i;
        actionPhotoUtil.actionPhoto(EditLoveActivity.this,REQUEST_CODE);
    }

    private void setDelPhoto(int index) {
        switch (index) {
            case 1:
                img1.setImageDrawable(null);
                del_1.setVisibility(View.GONE);
                data.set((index - 1), new BaseInfo(false, ""));
                break;
            case 2:
                img2.setImageDrawable(null);
                del_2.setVisibility(View.GONE);
                data.set((index - 1), new BaseInfo(false, ""));
                break;
            case 3:
                img3.setImageDrawable(null);
                del_3.setVisibility(View.GONE);
                data.set((index - 1), new BaseInfo(false, ""));
                break;
            case 4:
                img4.setImageDrawable(null);
                del_4.setVisibility(View.GONE);
                data.set((index - 1), new BaseInfo(false, ""));
                break;
            case 5:
                img5.setImageDrawable(null);
                del_5.setVisibility(View.GONE);
                data.set((index - 1), new BaseInfo(false, ""));
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 图片选择结果回调
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra(ImgSelActivity.INTENT_RESULT);
            for (String path : pathList) {
                mark = 1;
                Log.e("PersActivity", "onActivityResult:  path = " + path);
                setPhoto(path);
            }
        }
    }

    private void setPhoto(String path) {
        switch (index) {
            case 1:
                Glide.with(this).load(path).centerCrop().transform(new GlideRoundTransform(this, 12)).into(img1);
                del_1.setVisibility(View.VISIBLE);
                data.set((index - 1), new BaseInfo(true, path));

                break;
            case 2:
                Glide.with(this).load(path).centerCrop().transform(new GlideRoundTransform(this, 12)).into(img2);
                del_2.setVisibility(View.VISIBLE);
                data.set((index - 1), new BaseInfo(true, path));
                break;
            case 3:
                Glide.with(this).load(path).centerCrop().transform(new GlideRoundTransform(this, 12)).into(img3);
                del_3.setVisibility(View.VISIBLE);
                data.set((index - 1), new BaseInfo(true, path));
                break;
            case 4:
                Glide.with(this).load(path).centerCrop().transform(new GlideRoundTransform(this, 12)).into(img4);
                del_4.setVisibility(View.VISIBLE);
                data.set((index - 1), new BaseInfo(true, path));
                break;
            case 5:
                Glide.with(this).load(path).centerCrop().transform(new GlideRoundTransform(this, 12)).into(img5);
                del_5.setVisibility(View.VISIBLE);
                data.set((index - 1), new BaseInfo(true, path));
                break;
        }
    }


    /**
     * 获取被选择的数据
     *
     * @param data
     * @return
     */
    public List<String> getTureData(List<BaseInfo> data) {

        List<String> entity = new ArrayList<>();
        int count = data.size();
        for (int i = 0; i < count; i++) {
            BaseInfo info = data.get(i);
            if (info.isCheck()) {
                entity.add(info.getName());
            }
        }

        return entity;
    }
}