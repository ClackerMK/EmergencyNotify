package lev.perschin.emergencynotify.info;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.preference.PreferenceManager;

/**
 * Created by Lev Perschin on 21.10.16.
 * e-mail: lev.perschin@gmail.com
 */

public class PersonalInformation {
    private static String TAG = "PersonalInformation";
    private Context context;
    private String forename;
    private String surname;
    private Location location;

    public PersonalInformation(Context context) {
        this.context = context;
    }

    public void gatherPersonalInformation() {
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        forename = prefs.getString("forename", "");
        surname = prefs.getString("surname", "");
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
