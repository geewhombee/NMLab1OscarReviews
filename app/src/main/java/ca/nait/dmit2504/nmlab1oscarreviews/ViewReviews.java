package ca.nait.dmit2504.nmlab1oscarreviews;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ViewReviews extends AppCompatActivity {

    private ListView mOscarListView;
    private static final String TAG = ViewReviews.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reviews);

        mOscarListView = findViewById(R.id.activity_view_listview);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.youcode.ca")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        YoucodeService youcodeService = retrofit.create(YoucodeService.class);

        Call<String> getCall = youcodeService.listLab1Servlet();
        getCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String responseBody = response.body();

                List<Oscar> oscarList = new ArrayList<>();
                String[] oscarArray = responseBody.split("\r\n");
                int oscarArrayLength = oscarArray.length;
                for (int i = 0; i < oscarArrayLength; i += 5) {
                    Oscar currentOscar = new Oscar();
                    String date = oscarArray[i];
                    String reviewer = oscarArray[i+1];
                    String category = oscarArray[i + 2];
                    String nominee = oscarArray[i + 3];
                    String review = oscarArray[i+4];

                    currentOscar.setDate(date);
                    currentOscar.setReviewer(reviewer);
                    currentOscar.setCategory(category);
                    currentOscar.setNominee(nominee);
                    currentOscar.setReview(review);

                    oscarList.add(currentOscar);
                }
                ListView oscarsListView = findViewById(R.id.activity_view_listview);
                OscarListViewAdapter oscarAdapter = new OscarListViewAdapter(getApplicationContext(),oscarList);
                mOscarListView.setAdapter(oscarAdapter);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Fetch reviews was not successful.", Toast.LENGTH_LONG).show();
            }
        });
    }
}
