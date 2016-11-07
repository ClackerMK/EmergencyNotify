package lev.perschin.emergencynotify.information;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.location.Location;
import android.util.Log;

import java.util.List;

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

        gatherPersonalInformation();
    }

    private void gatherPersonalInformation() throws SecurityException {
        final AccountManager accountManager = AccountManager.get(context);
        final Account[] accounts = accountManager.getAccountsByType("com.google");
        List<String> names = null;
        for (Account account : accounts) {
            names.add(account.name);
            Log.i(TAG, account.name);
        }
    }
}
