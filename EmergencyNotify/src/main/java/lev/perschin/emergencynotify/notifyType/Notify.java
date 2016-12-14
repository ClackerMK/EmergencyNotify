package lev.perschin.emergencynotify.notifyType;

import android.util.Log;

import lev.perschin.emergencynotify.info.PersonalInformation;

/**
 * Created by Lev Perschin on 20.10.16.
 * e-mail: lev.perschin@gmail.com
 */

abstract class Notify {
    abstract protected void send(PersonalInformation pi);

    final public void notify(PersonalInformation pi) {
        Log.v("Notify", "Sending information about: " + pi.getForename() + ", " + pi.getSurname());
        send(pi);
        Log.v("Notify", "Notification send");
    }
}
