package com.example.testanxietycbt;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Timer;
import java.util.TimerTask;

public class NotificationService extends Service {
    String CHANNEL_ID;
    Timer timer;
    CountDownTimer timer2;
    TimerTask timerTask;
    String TAG = "Timers";
    FirebaseDatabase database;
    DatabaseReference rootRef;
    int Your_X_SECS = 99999;
    int count = 0;
    NotificationChannel channel;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    static long timeleft;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand");
        super.onStartCommand(intent, flags, startId);

        database = FirebaseDatabase.getInstance();
        rootRef = database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("NextActivity");

        rootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ExerciseSchedule exerciseSchedule = snapshot.getValue(ExerciseSchedule.class);
                snapshot.getValue(ExerciseSchedule.class);
                Your_X_SECS = (int)exerciseSchedule.SecondsTillExercise;
                Log.i("NotiTest", "Notification Test => Pinging in " + Your_X_SECS + " seconds");

                if (Your_X_SECS > 0)
                startTimer();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return START_STICKY;
    }


    @Override
    public void onCreate() {
        Log.e(TAG, "onCreate");


    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy");
        stoptimertask();
        super.onDestroy();


    }

    //we are going to use a handler to be able to run in our TimerTask
    final Handler handler = new Handler();


    public void startTimer() {
        //set a new Timer
        timer = new Timer();
        timer2 = new CountDownTimer(Your_X_SECS, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeleft = millisUntilFinished;
            }

            @Override
            public void onFinish() {

            }
        };
        //initialize the TimerTask's job
        initializeTimerTask();

        //schedule the timer, after the first 5000ms the TimerTask will run every 10000ms


        Log.i("YOUR_X_SECS", String.valueOf(Your_X_SECS));
        timer.schedule(timerTask, Your_X_SECS * 1000, Your_X_SECS * 1000); //
        //timer.schedule(timerTask, 5000,1000); //
    }

    public void stoptimertask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public void initializeTimerTask() {

        timerTask = new TimerTask() {
            public void run() {

                //use a handler to run a toast that shows the current timestamp
                handler.post(new Runnable() {
                    public void run() {

                        //TODO CALL NOTIFICATION FUNC
                        Notificationtest();

                    }
                });
            }
        };
    }




    private void Notificationtest() {
        prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        editor = prefs.edit();
        editor.putInt("countdown", 0);
        int NOTIFICATION_ID = 234;



        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            CHANNEL_ID = "my_channel_01";
            CharSequence name = "my_channel";
            String Description = "This is my channel";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.setDescription(Description);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mChannel.setShowBadge(false);
            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.test_img))
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentTitle("Test Anxiety CBT")
                .setContentText("An activity is ready to be completed!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        Intent resultIntent = new Intent(this, GroundingTemplate.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(Process.class);

        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setSmallIcon(R.drawable.ic_notifications_black_24dp);
            builder.setColor(getResources().getColor(R.color.mdtp_red));
        } else {
            builder.setSmallIcon(R.drawable.ic_notifications_black_24dp);
        }

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // notificationID allows you to update the notification later on.
        int notificationID = 0;
        mNotificationManager.notify(notificationID, builder.build());

    }



}