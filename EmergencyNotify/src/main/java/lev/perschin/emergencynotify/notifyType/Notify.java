package lev.perschin.emergencynotify.notifyType;

import android.util.Log;

/**
 * Created by Lev Perschin on 20.10.16.
 * e-mail: lev.perschin@gmail.com
 */

abstract class Notify {
    public Notify() {}

    public void send() {
        Log.v("Notify", "Notify send.");
    }
}
