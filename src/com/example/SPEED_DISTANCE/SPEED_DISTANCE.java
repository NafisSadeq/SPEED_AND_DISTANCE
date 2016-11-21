package com.example.SPEED_DISTANCE;


import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.IntentSender;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.widget.*;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;


import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.IntentSender;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;


import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.widget.TextView;


import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.widget.TextView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.ErrorDialogFragment;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class SPEED_DISTANCE extends Activity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    /**
     * Called when the activity is first created.
     */
    Calendar c1;

    Calendar c2, c3;

    long t1, t2, t3;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Location mCurrentLocation;
    Boolean mRequestingLocationUpdates;
    LocationRequest mLocationRequest;
    private TextView mLatitudeText;
    private TextView mLongitudeText;
    private TextView mLatitudeTextView;
    private TextView mLongitudeTextView;
    private TextView mLastUpdateTimeTextView;
    private TextView distance_unit_Text;
    private TextView distance2Text;
    private TextView demoText;
    private TextView speed_unit_text;
    private  TextClock time;
    private int content_view=3;
    private TextView demoText3;
    private ImageView compass;
    double x1, y1, x2, y2, x3, y3, distance1, distance2, distance, dx, dy;
    String mLastUpdateTime;
    String REQUESTING_LOCATION_UPDATES_KEY;
    String LOCATION_KEY;
    String LAST_UPDATED_TIME_STRING_KEY;
    // Request code to use when launching the resolution activity
    private static final int REQUEST_RESOLVE_ERROR = 1001;
    // Unique tag for the error dialog fragment
    private static final String DIALOG_ERROR = "dialog_error";
    // Bool to track whether the app is already resolving an error
    private boolean mResolvingError = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x1 = 0;
        y1 = 0;
        x2 = 0;
        y2 = 0;
        x3 = 0;
        y3 = 0;
        distance2 = 0;
        setContentView(R.layout.main3);
        mRequestingLocationUpdates = true;
        compass = (ImageView) findViewById(R.id.imageView);
        time = (TextClock) findViewById(R.id.textClock);
        //mLatitudeText = (TextView) findViewById(R.id.textView2);
        //mLongitudeText = (TextView) findViewById(R.id.textView3);
        mLatitudeTextView = (TextView) findViewById(R.id.lattitude);
        mLongitudeTextView = (TextView) findViewById(R.id.longitude);
        //mLastUpdateTimeTextView=(TextView) findViewById(R.id.textView6);
        //distance1Text=(TextView) findViewById(R.id.textView7);
        distance2Text = (TextView) findViewById(R.id.distance);
        demoText = (TextView) findViewById(R.id.current_speed);
        distance_unit_Text= (TextView) findViewById(R.id.distance_unit);
        speed_unit_text=(TextView) findViewById(R.id.current_speed_unit);
        //demoText2 = (TextView) findViewById(R.id.textView10);

        Typeface tf = Typeface.createFromAsset(getAssets(),
                "LED.Font.ttf");
        //TextView tv = (TextView) findViewById(R.id.CustomFontText);
        time.setTypeface(tf);
        if(content_view==3){
            mLatitudeTextView.setTypeface(tf);
            mLongitudeTextView.setTypeface(tf);
        }

        distance2Text.setTypeface(tf);
        distance_unit_Text.setTypeface(tf);
        speed_unit_text.setTypeface(tf);
        demoText.setTypeface(tf);
        //demoText2.setTypeface(tf);
        /*distance2Text.setTextSize(50);
        demoText.setTextSize(50);
        //demoText2.setTextSize(50);
        mLatitudeTextView.setTextSize(30);
        mLongitudeTextView.setTextSize(30);*/
        //demoText3=(TextView) findViewById(R.id.textView11);
        buildGoogleApiClient();
        updateValuesFromBundle(savedInstanceState);
        start();
    }

    public void start() {

        createLocationRequest();
        mGoogleApiClient.connect();


    }

    protected synchronized void buildGoogleApiClient() {

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }


    @Override
    public void onConnected(Bundle connectionHint) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {

            //mLatitudeText.setText("INITIAL LATTITUDE : " + String.valueOf(mLastLocation.getLatitude()));
            //mLongitudeText.setText("INITIAL LONGITUDE : " + String.valueOf(mLastLocation.getLongitude()));
            if (x1 == 0 && y1 == 0) {
                x1 = Math.toRadians(mLastLocation.getLatitude());
                y1 = Math.toRadians(mLastLocation.getLongitude());
                x2 = x1;
                x3 = x1;
                y2 = y1;
                y3 = y1;
                //c1 = Calendar.getInstance();
                t1 = mLastLocation.getTime();
                t2 = t1;
            }


        }


        if (mRequestingLocationUpdates) {
            startLocationUpdates();
        }


    }

    double calculate(double lat1, double long1, double lat2, double long2) {
        double dx = lat2 - lat1;
        double dy = long2 - long1;

        double a = Math.sin(dx / 2) * Math.sin(dx / 2) + Math.cos(lat1) * Math.cos(lat2) * Math.sin(dy / 2) * Math.sin(dy / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt((1 - a)));

        return c * 6371000;
    }

    @Override
    public void onConnectionSuspended(int i) {
        //mLatitudeText.setText("Connection suspended");
        //mLongitudeText.setText("Connection suspended");
        mGoogleApiClient.connect();
    }

    @TargetApi(Build.VERSION_CODES.DONUT)
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        //mLatitudeText.setText("Connection Failed");
        //mLongitudeText.setText("Connection Failed");
        //mGoogleApiClient.connect();

        if (mResolvingError) {
            // Already attempting to resolve an error.
            return;
        } else if (connectionResult.hasResolution()) {
            try {
                mResolvingError = true;
                connectionResult.startResolutionForResult(this, REQUEST_RESOLVE_ERROR);
            } catch (IntentSender.SendIntentException e) {
                // There was an error with the resolution intent. Try again.
                mGoogleApiClient.connect();
            }
        } else {
            // Show dialog using GooglePlayServicesUtil.getErrorDialog()
            showErrorDialog(connectionResult.getErrorCode());
            mResolvingError = true;
        }

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void showErrorDialog(int errorCode) {
        // Create a fragment for the error dialog
        ErrorDialogFragment dialogFragment = new ErrorDialogFragment();
        // Pass the error that should be displayed
        Bundle args = new Bundle();
        args.putInt(DIALOG_ERROR, errorCode);
        dialogFragment.setArguments(args);
        //dialogFragment.show(getSupportFragmentManager(), "errordialog");
    }


    /* Called from ErrorDialogFragment when the dialog is dismissed. */
    public void onDialogDismissed() {
        mResolvingError = false;
    }

    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        String mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
        updateUI();
    }

    private void updateUI() {
        //demoText.setText(String.valueOf(calculate(Math.toRadians(23.77),Math.toRadians(90.33),Math.toRadians(24.55),Math.toRadians(91.55))));

        //demoText3.setText("SPEED : "+String.valueOf(mCurrentLocation.getSpeed()*3.6)+" Km/h");
        ;




        //mLatitudeTextView.setText(Location.convert(mCurrentLocation.getLatitude(),Location.FORMAT_DEGREES));
        //mLongitudeTextView.setText(Location.convert(mCurrentLocation.getLongitude(),Location.FORMAT_SECONDS));


        if(content_view==3){
            Typeface tf = Typeface.createFromAsset(getAssets(),
                    "LED.Font.ttf");
            //TextView tv = (TextView) findViewById(R.id.CustomFontText);
            time.setTypeface(tf);
            mLatitudeTextView.setTypeface(tf);
            mLongitudeTextView.setTypeface(tf);
            mLatitudeTextView.setText(String.valueOf(mCurrentLocation.getLatitude()) + (mCurrentLocation.getLatitude() > 0 ? " NORTH" : " SOUTH"));
            mLongitudeTextView.setText(String.valueOf(mCurrentLocation.getLongitude()) + (mCurrentLocation.getLongitude() > 0 ? " EAST" : " WEST"));
        }

        if(content_view==2){
            double angle1 = -mCurrentLocation.getBearing();
            compass.setRotation((float) angle1);
        }


        // mLastUpdateTimeTextView.setText(mLastUpdateTime);
        x3 = Math.toRadians(mCurrentLocation.getLatitude());
        y3 = Math.toRadians(mCurrentLocation.getLongitude());
        c3 = Calendar.getInstance();
        t3 = mCurrentLocation.getTime();
        distance1 = calculate(x1, y1, x3, y3);
        int temp3 = (int) (distance1 * 100);
        double distance1_temp = temp3 / 100.0;
        //distance1Text.setText("DISTANCE LIN : "+String.valueOf(Math.abs(distance1_temp))+" meter");
        distance = calculate(x2, y2, x3, y3);
        double temp1 = mCurrentLocation.getSpeed();
        temp1 = temp1 * 3.6;
        int temp2 = (int) (temp1 * 100);
        temp1 = temp2 / 100.0;
        demoText.setText(String.valueOf(Math.abs(temp1)));
        distance2 = distance2 + distance;
        double temp_km=distance2/1000;
        temp3 = (int) (temp_km * 100);
        double distance2_temp = temp3 / 100.0;
        distance2Text.setText(String.valueOf(Math.abs(distance2_temp)) );
        temp1 = distance2 / ((t3 - t1) / 1000.0);
        temp1 = temp1 * 3.6;
        temp2 = (int) (temp1 * 100);
        temp1 = temp2 / 100.0;
        //demoText2.setText( String.valueOf(Math.abs(temp1)) + " Km/h");
        x2 = x3;
        y2 = y3;
        t2 = t3;
    }

    /* A fragment to display an error dialog */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class ErrorDialogFragment extends DialogFragment {
        public ErrorDialogFragment() {
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Get the error code and retrieve the appropriate dialog
            int errorCode = this.getArguments().getInt(DIALOG_ERROR);
            return GooglePlayServicesUtil.getErrorDialog(errorCode,
                    this.getActivity(), REQUEST_RESOLVE_ERROR);
        }

        @Override
        public void onDismiss(DialogInterface dialog) {
            ((SPEED_DISTANCE) getActivity()).onDialogDismissed();
        }
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(500);
        mLocationRequest.setFastestInterval(500);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }


    protected void startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mGoogleApiClient.isConnected() && !mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {

        savedInstanceState.putBoolean(REQUESTING_LOCATION_UPDATES_KEY,
                mRequestingLocationUpdates);

        savedInstanceState.putParcelable(LOCATION_KEY, mCurrentLocation);

        savedInstanceState.putString(LAST_UPDATED_TIME_STRING_KEY, mLastUpdateTime);
        super.onSaveInstanceState(savedInstanceState);
    }

    private void updateValuesFromBundle(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            // Update the value of mRequestingLocationUpdates from the Bundle, and
            // make sure that the Start Updates and Stop Updates buttons are
            // correctly enabled or disabled.

            if (savedInstanceState.keySet().contains(REQUESTING_LOCATION_UPDATES_KEY)) {
                mRequestingLocationUpdates = savedInstanceState.getBoolean(
                        REQUESTING_LOCATION_UPDATES_KEY);
                setButtonsEnabledState();
            }

            // Update the value of mCurrentLocation from the Bundle and update the
            // UI to show the correct latitude and longitude.
            if (savedInstanceState.keySet().contains(LOCATION_KEY)) {
                // Since LOCATION_KEY was found in the Bundle, we can be sure that
                // mCurrentLocationis not null.
                mCurrentLocation = savedInstanceState.getParcelable(LOCATION_KEY);
            }

            // Update the value of mLastUpdateTime from the Bundle and update the UI.
            if (savedInstanceState.keySet().contains(LAST_UPDATED_TIME_STRING_KEY)) {
                mLastUpdateTime = savedInstanceState.getString(
                        LAST_UPDATED_TIME_STRING_KEY);
            }
            updateUI();
        }
    }

    private void setButtonsEnabledState() {
        mRequestingLocationUpdates = false;
    }

}


