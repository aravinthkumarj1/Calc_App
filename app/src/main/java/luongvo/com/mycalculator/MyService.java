package luongvo.com.mycalculator;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MyService extends Service {
    public MyService() {
    }

    public static class LogsUtils {
        static final String processId = Integer.toString(android.os.Process
                .myPid());

        public static StringBuilder readLog() {
            StringBuilder logBuilder = new StringBuilder();
            try {
                String[] command = new String[] { "logcat", "-d"};
                Process process = Runtime.getRuntime().exec(command);
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(process.getInputStream()));

                String line;
                command = new String[] { "logcat", "-c", "threadtime" };
                Runtime.getRuntime().exec(command);
                while ((line = bufferedReader.readLine()) != null) {
                    if (line.contains(processId)) {
                        logBuilder.append(line);
                    //String str = logs.toString();
                    //Log.d("Aravinth1:", line);

                    //Code here
                    }
                }
            } catch (IOException e) {
            }
            return logBuilder;
        }
    }

    private static final String TAG = "Aravinth:BS:";
    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        onTaskRemoved(intent);

        Toast.makeText(getApplicationContext(),"This is a Service running in Background",
                Toast.LENGTH_SHORT).show();
        StringBuilder logs = MainActivity.LogsUtils.readLog();
        if(logs.toString().contains("flush")) {
            Log.d(TAG, "Aravinth:Matched:flush ");
            }
        //else
           // Log.d(TAG, "Aravinth:unMatched: ");
        return START_STICKY;
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Intent restartServiceIntent = new Intent(getApplicationContext(),this.getClass());
        restartServiceIntent.setPackage(getPackageName());
        startService(restartServiceIntent);
        super.onTaskRemoved(rootIntent);
    }
}
