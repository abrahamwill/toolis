package id.ac.ui.cs.mobileprogramming.abraham_williams_lumbantobing.toolis;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class ConnectivityBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo!=null){
            if(networkInfo.getType()==ConnectivityManager.TYPE_MOBILE){
                Toast.makeText(context, "Using mobile data", Toast.LENGTH_SHORT).show();
            }
            if(networkInfo.getType()==ConnectivityManager.TYPE_MOBILE){
                Toast.makeText(context,"Using Wifi", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(context,"No internet", Toast.LENGTH_SHORT).show();
        }
    }

}
