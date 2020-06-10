package com.donor.needyturtle.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.io.File;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class Commons {

    public static final String TAG = "RBA";

    public static final int MAX_RETRIES = 3;
    public static final int TIMEOUT = 30 * 1000;

    public static final int CROP_WIDTH = 500;
    public static final int CROP_HEIGHT = 400;


    public static SimpleDateFormat sdf_show =
            new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());

    public static String getCacheDirectory(Context context){
        final String dir = context.getCacheDir() + "";
        return dir;
    }

    public static String getFolderDirectoryImage(){
        final String dir = android.os.Environment.getExternalStorageDirectory() + "/RBAImage";
        return dir;
    }

    public static void deleteDirectoryImages(){
        File dir = new File(getFolderDirectoryImage());
        if (dir.isDirectory() && dir.exists()) {
            String[] children = dir.list();
            if (children != null){
                for (int i = 0; i < children.length; i++) {
                    new File(dir, children[i]).delete();
                }
            }

        }
    }

    public static void hideSoftKeyboard(Activity activity) {
        if (activity != null) {
            InputMethodManager inputManager = (InputMethodManager)
                    activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (activity.getCurrentFocus() != null && inputManager != null) {
                inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
                inputManager.hideSoftInputFromInputMethod(activity.getCurrentFocus().getWindowToken(), 0);
            }
        }

        if (activity != null) {
            View view = activity.findViewById(android.R.id.content);
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }

    }


    public static Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        return resizedBitmap;
    }


    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    public static String convertDate1(String dates){
        String date = "";
        try {
            SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            Date newDate = spf.parse(dates);

            spf = new SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.ENGLISH);
            date = spf.format(newDate);

        }catch (Exception e){
            e.printStackTrace();
        }
        return date;
    }

    public static String convertToTime(String dates){
        String date = "";
        try {
            SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            Date newDate = spf.parse(dates);

            spf= new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
            date = spf.format(newDate);

        }catch (Exception e){
            e.printStackTrace();
        }
        return date;
    }

    public static String convertDateFormatBill(String dates){
        String date = "";
        try {
            SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            Date newDate = spf.parse(dates);

            spf= new SimpleDateFormat("dd-MMM-yyyy hh:mm a", Locale.ENGLISH);
            date = spf.format(newDate);

        }catch (Exception e){
            e.printStackTrace();
        }
        return date;
    }


    public static SSLContext getSslContext() {

        TrustManager[] byPassTrustManagers = new TrustManager[] { new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }

            public void checkClientTrusted(X509Certificate[] chain, String authType) {
            }

            public void checkServerTrusted(X509Certificate[] chain, String authType) {
            }
        } };

        SSLContext sslContext=null;

        try {
            sslContext = SSLContext.getInstance("TLS");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            sslContext.init(null, byPassTrustManagers, new SecureRandom());
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }

        return sslContext;
    }




}
