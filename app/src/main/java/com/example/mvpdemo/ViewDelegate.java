package com.example.mvpdemo;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.mvp.databind.BindType;
import com.mvp.databind.DataBindDelegate;
import com.mvp.databind.Bind;

/**
 * Created by jack06.li on 2019-08-26.
 */
public class ViewDelegate extends DataBindDelegate {
    @Override
    public int getRootLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initWidget() {
        super.initWidget();
    }

    @Override
    public void bindEvenListener() {
        notifyAllView();
    }


    @Bind(name = "phone",id =R.id.phone1,type = BindType.input)
    public void setPhone1(String phone){
        EditText phone1 = get(R.id.phone1);
        phone1.setText(phone);
    }

    @Bind(name = "phone",id = R.id.phone1,type = BindType.output)
    public String getPhone1(){
        EditText phone1 = get(R.id.phone1);
        return phone1.getText().toString();
    }
    @Bind(name = "phone",id= R.id.phone2,type = BindType.input)
    public void setPhone2(String phone){
        EditText phone2 = get(R.id.phone2);
        phone2.setText(phone);
    }
    @Bind(name = "phone",id = R.id.phone2,type = BindType.output)
    public String getPhone2(){
        EditText phone2 = get(R.id.phone2);
        return phone2.getText().toString();
    }



    public String getPhone(int id){
        EditText phone = get(id);
        return phone.getText().toString();
    }

    private void notifyAllView(){
        notifyPhoneViewChanged(R.id.phone1);
        notifyPhoneViewChanged(R.id.phone2);
    }



    private void notifyPhoneViewChanged(final int id){
        EditText phone = get(id);
        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                notifyViewChanged(id);
            }
        });
    }


}
