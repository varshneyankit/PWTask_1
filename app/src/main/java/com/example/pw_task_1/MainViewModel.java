package com.example.pw_task_1;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pw_task_1.api.ApiClient;
import com.example.pw_task_1.api.ApiInterface;
import com.example.pw_task_1.pojos.Exclusions;
import com.example.pw_task_1.pojos.Facilities;
import com.example.pw_task_1.pojos.RootData;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends ViewModel {

    private final MutableLiveData<ApiResponseBundle> liveApiResponse = new MutableLiveData<>();

    public MainViewModel() {
        makeApiRequest();
    }

    public MutableLiveData<ApiResponseBundle> getLiveApiResponse() {
        return liveApiResponse;
    }

    public static class ApiResponseBundle {
        public List<Facilities> facilitiesList = new LinkedList<>();
        public List<List<Exclusions>> exclusionsList = new LinkedList<>();
        public HashMap<Integer, Integer> exclusionsMap = new HashMap<>();
    }

    private void makeApiRequest() {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        apiInterface.getAllData().enqueue(new Callback<RootData>() {
            @Override
            public void onResponse(Call<RootData> call, Response<RootData> response) {
                if(response.body()!=null){
                    ApiResponseBundle apiResponseBundle = new ApiResponseBundle();
                    apiResponseBundle.facilitiesList.addAll(response.body().getFacilities());
                    apiResponseBundle.exclusionsList.addAll(response.body().getExclusions());

                    for(int i=0;i<response.body().getExclusions().size();i++){
                        int option1 = Integer.parseInt(response.body().getExclusions().get(i).get(0).getOptionsId());
                        int option2 = Integer.parseInt(response.body().getExclusions().get(i).get(1).getOptionsId());
                        if(!apiResponseBundle.exclusionsMap.containsKey(option1))
                            apiResponseBundle.exclusionsMap.put(option1,option2);
                    }
                    liveApiResponse.setValue(apiResponseBundle);
                }
            }

            @Override
            public void onFailure(Call<RootData> call, Throwable t) {
                Log.e("TAG", "onFailure: "+t.getMessage() );
            }
        });

    }
}
