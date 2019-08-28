package com.mvp.databind;

import com.mvp.view.AppDelegate;

/**
 * Created by jack06.li on 2019-08-26.
 */
public abstract class DataBindDelegate extends AppDelegate {

    public void notifyViewChanged(int id){
        ((DataBindActivity)getActivity()).notifyViewChanged(id);
    }
}
