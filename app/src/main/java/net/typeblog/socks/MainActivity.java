package net.typeblog.socks;

import android.app.Activity;
import android.content.Intent;
import android.net.VpnService;
import android.os.Bundle;

import net.typeblog.socks.util.Profile;
import net.typeblog.socks.util.ProfileManager;
import net.typeblog.socks.util.Utility;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Attempt to start the VPN service
        ProfileManager profileManager = new ProfileManager(this);
        Profile defaultProfile = profileManager.getDefault();

        // Prepare the VPN connection (this will request permission if needed)
        Intent prepareIntent = VpnService.prepare(this);
        if (prepareIntent != null) {
            // Permission not yet granted, start the activity to request it
            startActivityForResult(prepareIntent, 0);
        } else {
            // Permission already granted, start the VPN service directly
            Utility.startVpn(this, defaultProfile);
        }

        this.getFragmentManager().beginTransaction().replace(android.R.id.content, new ProfileFragment()).commit();
    }
}
