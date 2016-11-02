package com.toquescript.www.norcexproject.callbacks;

import com.toquescript.www.norcexproject.model.MeasureModel;
import com.toquescript.www.norcexproject.model.ObjectMeasureModel;
import com.toquescript.www.norcexproject.model.ObjectRealmModel;

import java.util.List;

import io.realm.RealmObject;

/**
 * Created by BALAREZO on 07/06/2016.
 */
public interface CallbackTaskFinish {
    void taskFinishMe(RealmObject obj);
    void taskFinishObject(List<ObjectRealmModel> obj);
    void taskFinishMeasure(List<MeasureModel> obj);
    void taskFinishObjectMeasure(List<ObjectMeasureModel> obj);
}
