package com.chatdemo.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
 * Created by decimal on 28/10/15.
 */
public class UtilFunctions {


    private static ProgressDialog pd;

    public static boolean isOnlyAlbhabet(String name) {
        return name.matches("[a-zA-Z]+");
    }

    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    public static void requestFocus(View view, Activity activity) {
        if (view.requestFocus()) {
            activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    public static void displaySnackbar(Context context, String message) {

        final Snackbar snackbar=Snackbar.make(((Activity) context).findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT);

        snackbar .show();

    }


    public static void showProgressDialog(Context context,String message){
        if (pd!=null) {
            pd.dismiss();
        }
        pd=new ProgressDialog(context);
        pd.setMessage(message);

        //  pd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pd.setCancelable(false);

        pd.show();

    }

    public static void dismissProgressDialog(){
        if (pd!=null) {
            pd.dismiss();
        }
    }

    public static Date stringToDate(String dateInString) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
        Date date = null;

        try {

            date = formatter.parse(dateInString);
            System.out.println(date);
            System.out.println(formatter.format(date));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    //update time in 12 hour clock
    public static String twelveHourTimeFormat(int hours, int mins) {

        String timeSet = "";
        if (hours > 12) {
            hours -= 12;
            timeSet = "PM";
        } else if (hours == 0) {
            hours += 12;
            timeSet = "AM";
        } else if (hours == 12)
            timeSet = "PM";
        else
            timeSet = "AM";


        String minutes = "";
        if (mins < 10)
            minutes = "0" + mins;
        else
            minutes = String.valueOf(mins);

        // Append in a StringBuilder
        String aTime = new StringBuilder().append(hours).append(':')
                .append(minutes).append(" ").append(timeSet).toString();

        return aTime;
    }


    public static String getCurrentDateIn_yyyyMMdd() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }


    public static String getCurrentDateIn_ddMMyyyy() {
        return new SimpleDateFormat("dd-MM-yyyy").format(new Date());
    }


    public static Date getDateObjIn_yyyyMMdd(String stringDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        Date date = null;
        try {
            date = formatter.parse(stringDate);

        } catch (ParseException e) {

        }
        return date;
    }


    public static boolean isNull(String string) {

        boolean isNull = false;
        if (string == null || string.trim().isEmpty() || string.equalsIgnoreCase("null")) {
            isNull = true;
        }
        return isNull;
    }

    public static boolean hasStorage(boolean requireWriteAccess) {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        } else if (!requireWriteAccess
                && Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }


    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }


    public static long getDateDiffInDays(Date createdDate, Date currentDate) {
        long diff = currentDate.getTime() - createdDate.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    public String getSDcardDirectoryPath() {
        return System.getenv("SECONDARY_STORAGE");
    }


    public static boolean isGPSEnabled(Context context) {
        boolean enabled = false;

        LocationManager mlocManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (mlocManager != null) {
            enabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        }
        return enabled;
    }


    public static void showGPSDisabledAlertToUser(final Context context, String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle("Improve your location accuracy?");
        alertDialogBuilder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent callGPSSettingIntent = new Intent(
                                        Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                context.startActivity(callGPSSettingIntent);
                                // turnGpsOn(context);

                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ((Activity) context).finish();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    @SuppressWarnings("deprecation")
    private static boolean turnGpsOn(Context context) {
        boolean enabled = false;
        String beforeEnable = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        String newSet = String.format("%s,%s", beforeEnable, LocationManager.GPS_PROVIDER);
        try {
            enabled = Settings.Secure.putString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED, newSet);
        } catch (Exception e) {
            enabled = false;
            Log.i("turnGpsOn", "" + e);
        }
        return enabled;
    }
/*
    public static void closeApplicationAlert(final Context context, String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(context, LoginActivity.class);
                                context.startActivity(intent);
                                ((Activity) context).finish();
                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }*/

    public static boolean EmptySpinner(Context context, String[] errMszArr, String... strArr) {
        int i = 0;
        for (String st : strArr) {

            if (st == null || st.isEmpty()) {
                UtilFunctions.displaySnackbar(context, "Please select value for " + errMszArr[i]);
                return true;
            }

            i++;
        }
        return false;
    }

    public static String getAppVersion(Context context) {
        String appVersion = "";
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            String verCode = pInfo.versionName;
            // appVersion="Ver P: "+String.valueOf(verCode);
            appVersion = verCode;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "Version : "+appVersion;
    }


    public static int getAppVersionCode(Context context) {
        int appVersionCode = 0;
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            appVersionCode = pInfo.versionCode;
            // appVersion="Ver P: "+String.valueOf(verCode);


        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return appVersionCode;
    }

    public static int getDiffYears(Date dob, Date current) {

        int diff = current.getYear() - dob.getYear();

        if ((dob.getMonth() > current.getMonth()) || (dob.getMonth() == current.getMonth() && dob.getDay() > current.getDay())) {
            diff--;
        }
        return diff;
    }

    public static boolean isApplicationInForeground(Context context) {
        boolean isActivityFound = false;
        try {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> services = activityManager
                    .getRunningTasks(Integer.MAX_VALUE);

            if (services.get(0).topActivity.getPackageName().toString()
                    .equalsIgnoreCase(context.getPackageName().toString())) {
                isActivityFound = true;
            }
        } catch (Exception e) {
            isActivityFound = false;
        }

        return isActivityFound;
    }


    public static boolean isNavDrawerOpen(DrawerLayout drawerLayout) {
        return drawerLayout != null && drawerLayout.isDrawerOpen(GravityCompat.START);
    }

    public static void closeNavDrawer(DrawerLayout drawerLayout) {
        if (drawerLayout != null) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }



    public static String getDatabasePath(Context aContext, String filename) {
        String path = "data/data" + aContext.getPackageName() + "/" + filename;
        return path;
    }

    public static String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    public static String milliSecondToDate(String milliSecond) {

        DateFormat formatter = new SimpleDateFormat("dd MMM yyyy");

        long milliSeconds = Long.parseLong(milliSecond);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }




    public static int getScreenWidth(Context c) {
        int screenWidth = 0; // this is part of the class not the method
        if (screenWidth == 0) {
            WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            screenWidth = size.x;
        }

        return screenWidth;
    }

    public static void rateUsOnPlayStore(Context context) {
        final String appPackageName = context.getPackageName();
        String appLink = "https://play.google.com/store/apps/details?id=" + appPackageName;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(appLink));
        context.startActivity(intent);
    }

    public static void shareAppWithOthers(Context context) {
        final String appPackageName = context.getPackageName();
        String appLink = "Download GST INdia : \n" + "https://play.google.com/store/apps/details?id=" + appPackageName;

        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, appLink);
        context.startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }


    public static void shareContent(Context context, String content) {
        Intent sharingIntent = new Intent(
                android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, content);
        context.startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    public static void openFeedback(Context paramContext) {
        Intent localIntent = new Intent(Intent.ACTION_SEND);
        localIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"himanshu.vasudeva009@gmail.com"});
        //  localIntent.putExtra(Intent.EXTRA_CC, "");
        String str = null;
        try {
            str = paramContext.getPackageManager().getPackageInfo(paramContext.getPackageName(), 0).versionName;
            localIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback for Bzzngnow");
            localIntent.putExtra(Intent.EXTRA_TEXT, "\n\n----------------------------------\n Device OS: Android " +
                    "\n Device OS version: " +
                    Build.VERSION.RELEASE + "\n App Version: " + str + "\n Device Brand: " + Build.BRAND +
                    "\n Device Model: " + Build.MODEL + "\n Device Manufacturer: " + Build.MANUFACTURER);
            localIntent.setType("message/rfc822");
            paramContext.startActivity(Intent.createChooser(localIntent, "Choose an Email client :"));
        } catch (Exception e) {
            Log.d("OpenFeedback", e.getMessage());
        }
    }

    public static void hideKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);

    }


    // Converting timestamp into x ago format
    public static CharSequence convertTimestamp(String dateInMilli) {
        CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
                Long.parseLong(dateInMilli),
                System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
        return timeAgo;
    }


    public static void shareImage(Context context, ImageView imageview) {
        // Get access to bitmap image from view

        // Get access to the URI for the bitmap
        Uri bmpUri = getLocalBitmapUri(context, imageview);
        if (bmpUri != null) {
            // Construct a ShareIntent with link to image
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
            shareIntent.setType("image/*");
            // Launch sharing dialog for image
            context.startActivity(Intent.createChooser(shareIntent, "Share Image"));
        } else {
            // ...sharing failed, handle error
        }
    }

    // Returns the URI path to the Bitmap displayed in specified ImageView
    public static Uri getLocalBitmapUri(Context context, ImageView imageView) {
        // Extract Bitmap from ImageView drawable
        Drawable drawable = imageView.getDrawable();
        Bitmap bmp = null;
        if (drawable instanceof BitmapDrawable) {
            bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        } else {
            return null;
        }
        // Store image to default external storage directory
        Uri bmpUri = null;
        try {
            // Use methods on Context to access package-specific directories on external storage.
            // This way, you don't need to request external read/write permission.
            // See https://youtu.be/5xVh-7ywKpE?t=25m25s
            File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }

   /* public static void setToolBar(Context context, String title, final ActionBar abar) {
        View viewActionBar = LayoutInflater.from(context).inflate(R.layout.center_title_actionbar, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.LEFT|Gravity.CENTER_VERTICAL);
        TextView textviewTitle = (TextView) viewActionBar.findViewById(R.id.actionbar_textview);
        abar.setLogo(null);
        abar.setHomeAsUpIndicator(R.drawable.ic_backarrow);
        textviewTitle.setText(title);
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
         abar.setDisplayHomeAsUpEnabled(true);
        abar.setHomeButtonEnabled(true);
    }


    public static void setToolBarWithoutNavBar(Context context, String title, final ActionBar abar) {
        View viewActionBar = LayoutInflater.from(context).inflate(R.layout.center_title_actionbar, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.LEFT|Gravity.CENTER_VERTICAL);
        TextView textviewTitle = (TextView) viewActionBar.findViewById(R.id.actionbar_textview);

        abar.setHomeAsUpIndicator(R.drawable.simpler_loan_logo);
        textviewTitle.setText(title);
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
        abar.setDisplayHomeAsUpEnabled(true);
       // abar.setHomeButtonEnabled(true);
    }
*/


    public static boolean isMobileVAlid(String s) {
        return s.matches("[789]\\d{9}");
    }

    public static boolean isServixTAxNo(String trim) {

        return Pattern.matches("[A-Za-z]{5}\\d{4}[A-Za-z]{1}(ST|SD|st|sd)[0-9]{3}", trim);
    }

    public static boolean isTINNo(String tin) {
        //return Pattern.matches("[0-9]{11}", trim) || Pattern.matches("[0-9]{11}[VC]{1}", trim);
        return Pattern.matches("[0-9]{11}", tin);
    }

    public static boolean isValidPAN(String PANValidation) {
        return PANValidation.matches("[A-Za-z]{5}[0-9]{4}[A-Za-z]{1}");
    }

    public static String numberToRuppeFormat(long amount) {
        DecimalFormat formatter = new DecimalFormat("#,##,###");
        String rupees= formatter.format(amount);

        return rupees;
    }

    public static void displaySnackbarSuccess(Context context, String message) {

        final Snackbar snackbar=Snackbar.make(((Activity) context).findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT);

        snackbar .show();

    }

    public static String[] parseResponse(String response) {
        JSONObject jsonObject = null;

        String status = null;
        String message = null;
        try {
            jsonObject = new JSONObject(response);

            status = jsonObject.optString("status");
            message = jsonObject.optString("message");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new String[]{status, message};
    }

    public static String getDateFrom_ddMMyyyy_To_ddMMyyyy(String stringDate) {

        SimpleDateFormat fromUser = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat myFormat = new SimpleDateFormat("dd-MM-yyyy");
        String reformattedStr = null;
        try {

            reformattedStr = myFormat.format(fromUser.parse(stringDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return reformattedStr;
    }



    public static void showApplicationUpdateAlertbox(final Context context) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        alertDialogBuilder.setMessage("New version of GST India is available. Update now??")
                .setCancelable(false)
                .setPositiveButton("Update",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                                final String appPackageName = context.getPackageName(); // getPackageName() from Context or Activity object
                                try {
                                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));

                                } catch (android.content.ActivityNotFoundException anfe) {
                                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));

                                }

                            }
                        });
        alertDialogBuilder.setNegativeButton("Later",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }
}
