package lev.perschin.emergencynotify;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import lev.perschin.emergencynotify.information.PersonalInformation;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

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

        // Initial notify for not abusing this app.
        initialAlert();

        // Setup actions for the notifyButton
        setupNotifyButton();
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

    @SuppressWarnings("StatementWithEmptyBody")
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
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initialAlert() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.abusive_use)
                .setPositiveButton("I accept", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                })
                .setNegativeButton("I decline", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                }).setTitle("No abusive use.");
        builder.create().show();
    }

    private void setupNotifyButton() {
        Button notifyButton = (Button) findViewById(R.id.notifyButton);
        Button notifyButtonBig = (Button) findViewById(R.id.notifyButtonBig);

        notifyButton.setOnClickListener(e1 -> sendSmallGroup());
        notifyButtonBig.setOnClickListener(e2 -> sendBigGroup());
    }

    /**
     * Send notification on a small amount of notification types.
     */
    private void sendSmallGroup() {
        // TODO: Vibrating
        // TODO: Getting information
        // TODO: Sending information
        PersonalInformation personalInformation = new PersonalInformation(this);

        // Notify user
        String message = "Sent on small selection.";
        Snackbar snackbar = Snackbar.make(findViewById(R.id.content_main), message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    /**
     * Send notification on a big amount/ on all possibilities of notification types.
     */
    private void sendBigGroup() {

        // Notify user
        String message = "Sent on BIG selection.";
        Snackbar snackbar = Snackbar.make(findViewById(R.id.content_main), message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }
}
