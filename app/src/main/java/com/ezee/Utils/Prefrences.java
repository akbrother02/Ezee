package com.ezee.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by info3 on 17-07-2017.
 */

public class Prefrences {
    public boolean isLoggedIn() {
        return IsLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        IsLoggedIn = loggedIn;
    }

    boolean IsLoggedIn=false;

}
