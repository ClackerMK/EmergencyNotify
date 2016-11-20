package lev.perschin.emergencynotify;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class UserInformationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        setupSPEditText();
    }

    private void setupSPEditText() {
        EditText forename = (EditText) findViewById(R.id.forename);
        EditText surname = (EditText) findViewById(R.id.surname);

        setTextChangedListener(forename);
        setTextChangedListener(surname);

    }

    /**
     * Sets up EditText connection to Shared Preferences.
     */
    private void setTextChangedListener(EditText editText) {
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editText.setText(prefs.getString(String.valueOf(editText.getId()), ""));
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                prefs.edit().putString(String.valueOf(editText.getId()), s.toString()).apply();
            }
        });
    }
}
