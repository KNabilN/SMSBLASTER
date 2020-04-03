package com.fitness.android.smsgeneratormvvm.UI;

import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SMSViewModel extends ViewModel {
    MutableLiveData<List<String>> numbers = new MutableLiveData<>();


    public void getNumbers()
    {
        // get the data saved in the list
        numbers.setValue(MainActivity.numbers);
    }


}
