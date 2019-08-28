/*
 * Copyright (c) 2015, 张涛.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mvp.databind;

import android.os.Bundle;

import com.mvp.model.IModel;
import com.mvp.presenter.ActivityPresenter;
import com.mvp.view.IDelegate;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


public abstract class DataBindActivity<T extends IDelegate> extends
        ActivityPresenter<T> {

    protected IModel bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        bean = getDataBean();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void bindEvenListener() {
        viewDelegate.bindEvenListener();
    }

    @Override
    protected void initData() {
        getDataBean();
        notifyModelChanged();
    }


    //获取页面对应的model
    public abstract IModel getDataBean();

    public <D extends IModel> void notifyModelChanged() {
        Method[] methods = viewDelegate.getClass().getDeclaredMethods();
        for(Method m : methods){
            if (m.isAnnotationPresent(Bind.class)){
                Bind input = m.getAnnotation(Bind.class);
                if (input.type() == BindType.input){
                    String getName = new StringBuilder("get").append(captureName(input.name())).toString();
                    Method[] beanMethods = bean.getClass().getDeclaredMethods();
                    for(Method b: beanMethods){
                        if(getName.equalsIgnoreCase(b.getName())){
                            try {
                                Object value = b.invoke(bean);
                                m.invoke(viewDelegate,value);
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

            }
        }

    }

    private  String captureName(String name) {
        char[] cs=name.toCharArray();
        cs[0]-=32;
        return String.valueOf(cs);
    }

    //进行脏检测
    private void checkAndUpdateView(Object value,String name){
        Method[] methods = viewDelegate.getClass().getDeclaredMethods();
        List<BindMethod> inputBindArr = new ArrayList();
        List<BindMethod> outputBindArr = new ArrayList();
        for(Method m : methods){
            if (m.isAnnotationPresent(Bind.class)){
                Bind bind = m.getAnnotation(Bind.class);
                if(name.equalsIgnoreCase(bind.name())){
                    if(bind.type() == BindType.input){
                        inputBindArr.add(new BindMethod(bind,m));
                    }
                    else{
                        outputBindArr.add(new BindMethod(bind,m));
                    }
                }

            }
        }
        for(BindMethod output:outputBindArr){
            for(BindMethod input:inputBindArr){
                if (output.bind.id() == input.bind.id()){
                    try {
                        Object outValue = output.method.invoke(viewDelegate);
                        if (!value.equals(outValue)){
                            input.method.invoke(viewDelegate,value);
                        }

                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    class BindMethod{
        Bind bind;
        Method method;

        public BindMethod(Bind bind, Method method) {
            this.bind = bind;
            this.method = method;
        }
    }

    public <D extends IModel> void notifyViewChanged(int id) {
        Method[] methods = viewDelegate.getClass().getDeclaredMethods();
        for(Method m : methods){
            if (m.isAnnotationPresent(Bind.class)){
                Bind bind = m.getAnnotation(Bind.class);
                if(id == bind.id() && bind.type() == BindType.output){
                    try {
                        Object value = m.invoke(viewDelegate);
                        String getName = new StringBuilder("get").append(captureName(bind.name())).toString();
                        Method getMethod = bean.getClass().getDeclaredMethod(getName);
                        Object getValue = getMethod.invoke(bean);
                        if (!value.equals(getValue)) {
                            String setName = new StringBuilder("set").append(captureName(bind.name())).toString();
                            Method[] beanMethods = bean.getClass().getDeclaredMethods();
                            for (Method b : beanMethods) {
                                if (setName.equalsIgnoreCase(b.getName())) {
                                    b.invoke(bean, value);
                                    checkAndUpdateView(value, bind.name());
                                }
                            }
                        }
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }


}