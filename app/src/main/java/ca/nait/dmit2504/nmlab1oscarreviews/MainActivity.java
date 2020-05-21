package ca.nait.dmit2504.nmlab1oscarreviews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private TextView mReviewerText;
    private EditText mReviewText;
    private EditText mNomineeText;
    //private EditText mPasswordText;
    private RadioGroup radioGroup;
    private String category;
    private String password;


    private Button mSendButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //find all view's in layout
        mSendButton = findViewById(R.id.activity_post_button);
        mReviewerText = findViewById(R.id.activity_post_reviewer_TextView);
        mReviewText = findViewById(R.id.activity_post_review_edittext);
        mNomineeText = findViewById(R.id.activity_post_nominee_edittext);
        radioGroup = findViewById(R.id.activity_post_radiogroup);

        password = prefs.getString("password_prefs", " ");
        mReviewerText.setText(prefs.getString("username_prefs","Anonymous"));





        mSendButton.setOnClickListener((View view) -> {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://www.youcode.ca")
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();
            YoucodeService youcodeService = retrofit.create(YoucodeService.class);
            int checkedId = radioGroup.getCheckedRadioButtonId();
            findRadioButton(checkedId);
            Call<String> getCall = youcodeService.postlab1Servlet(mReviewText.getText().toString(),mReviewerText.getText().toString(),mNomineeText.getText().toString(),category,password);
            getCall.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {

                    Log.i(TAG, "Post was successful");
                    mNomineeText.setText("");
                    mReviewText.setText("");
                    Toast.makeText(MainActivity.this, "Post was successful", Toast.LENGTH_SHORT).show();
                    
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.e(TAG, "Post failed");
                }
            });
        });




    }

    private void findRadioButton(int checkedId) {
        switch  (checkedId) {
            case R.id.activity_post_film_radiobutton:
                category = "film";
                break;
            case R.id.activity_post_actor_radiobutton:
                category = "actor";
                break;
            case R.id.activity_post_actress_radiobutton:
                category = "actress";
                break;
            case R.id.activity_post_editing_radiobutton:
                category = "editing";
                break;
            case R.id.activity_post_effects_radiobutton:
                category = "effects";
                break;
            default:
                category = "";
                break;

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Create an instance of the Menu inflator
        MenuInflater inflater = getMenuInflater();
        // Inflate the menu
        inflater.inflate(R.menu.options_menu, menu);
        // Return true if the menu inflated OK
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Get the id of the selected menu item
        switch (item.getItemId()) {
            case R.id.menu_item_preferences:
                Intent goToPreferenceIntent = new Intent(this, SettingsActivity.class);
                startActivity(goToPreferenceIntent);
                return true;
            case R.id.menu_item_view_reviews:
                int checkedId = radioGroup.getCheckedRadioButtonId();
                findRadioButton(checkedId);
                Intent listOscarIntent = new Intent(this, ViewReviews.class);
                listOscarIntent.putExtra("category",category);
                startActivity(listOscarIntent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String username = prefs.getString("username_prefs", "Anonymous");
        String password = prefs.getString("password_pref", "Password2504");



        TextView usernameText = findViewById(R.id.activity_post_reviewer_TextView);
        usernameText.setText(username);


    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.registerOnSharedPreferenceChangeListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.unregisterOnSharedPreferenceChangeListener(this);
        password = prefs.getString("password_prefs", " ");

    }
}
