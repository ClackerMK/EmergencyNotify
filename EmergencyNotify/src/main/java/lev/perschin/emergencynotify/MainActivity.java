package lev.perschin.emergencynotify;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import lev.perschin.emergencynotify.info.PersonalInformation;
import lev.perschin.emergencynotify.notifyType.SMSNotify;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static int VIBRATION_TIME_SHORT = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Setup short messages in spinner
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.shortMessages, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // Setup actions for the notifyButton
        setupNotifyButton();

        // Initial notify for not abusing this app.
        noteAbusiveUse();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_contacts) {
            Intent intent = new Intent(this, ContactsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_notificationType) {
            Intent intent = new Intent(this, NotifyTypeActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_userInformation) {
            Intent intent = new Intent(this, UserInformationActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void noteAbusiveUse() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.abusive_use)
                .setPositiveButton("I accept", (dialog, id) -> {
                    dialog.cancel();
                    noteEmptyPersonalInfo();
                })
                .setNegativeButton("I decline", (dialog, id) -> finish())
                .setTitle("No abusive use.");
        builder.create().show();
    }

    private void noteEmptyPersonalInfo() {
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Log.i("blub", prefs.getString("forename", ""));
        Log.i("blub", prefs.getString("surname", ""));
        if (prefs.getString("forename", "").equals("")
                || prefs.getString("surname", "").equals("")) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Please set fore- and surname.")
                    .setPositiveButton("OK", (dialog, id) -> {
                        dialog.cancel();
                        startActivity(new Intent(this, UserInformationActivity.class));
                    });
            builder.create().show();
        }

    }

    private void setupNotifyButton() {
        Button notifyButton = (Button) findViewById(R.id.notifyButton);
        Button notifyButtonBig = (Button) findViewById(R.id.notifyButtonBig);

        notifyButton.setOnClickListener(e1 -> sendSmallGroup());
        notifyButtonBig.setOnClickListener(e2 -> sendBigGroup());
    }

    /**
     * Vibrate device to interact with user. Vibrates a specific time in milliseconds.
     * Only works, when device has Vibrator.
     */
    private void vibrate(int time) {
        Vibrator vibratorService = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        if (vibratorService.hasVibrator()) {
            vibratorService.vibrate(time);
        }
    }

    /**
     * Send notification on a small amount of notification types.
     */
    private void sendSmallGroup() {
        vibrate(VIBRATION_TIME_SHORT);

        PersonalInformation personalInformation = new PersonalInformation(this);
        personalInformation.gatherPersonalInformation();

        SMSNotify smsNotify = new SMSNotify();
        smsNotify.send(personalInformation);

        // Notify user
        String message = getString(R.string.sent_small);
        Snackbar snackbar = Snackbar.make(findViewById(R.id.content_main), message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    /**
     * Send notification on a big amount/ on all possibilities of notification types.
     */
    private void sendBigGroup() {
        vibrate(VIBRATION_TIME_SHORT);

        PersonalInformation personalInformation = new PersonalInformation(this);

        // Notify user
        String message = getString(R.string.sent_big);
        Snackbar snackbar = Snackbar.make(findViewById(R.id.content_main), message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }
}
