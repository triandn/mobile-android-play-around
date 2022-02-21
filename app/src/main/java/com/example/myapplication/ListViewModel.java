package com.example.myapplication;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class ListViewModel extends ViewModel {
    private MutableLiveData<ArrayList<String>> arrListNumber;
    public LiveData<ArrayList<String>> getArrayListNumber() {
        if (arrListNumber == null) {
            arrListNumber = new MutableLiveData<ArrayList<String>>();
            arrListNumber.setValue(new ArrayList<String>());
            arrListNumber.getValue().add("0");
        }
        return arrListNumber;
    }
    public  void InsertNumber(String item)
    {
        ArrayList<String> newArrList = arrListNumber.getValue();
        newArrList.add(item);
        arrListNumber.setValue(newArrList);
    }
    public void DeleteItem(int index)
    {
        ArrayList<String> newArrList = arrListNumber.getValue();
        newArrList.remove(index);
        arrListNumber.setValue(newArrList);
    }
    public void UpdateArrList (int index ,String newItem)
    {
        ArrayList<String> newArrList = arrListNumber.getValue();
        newArrList.set(index,newItem);
        arrListNumber.setValue(newArrList);
    }
}
