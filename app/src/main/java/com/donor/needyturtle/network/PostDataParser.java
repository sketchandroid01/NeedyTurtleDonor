package com.donor.needyturtle.network;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.donor.needyturtle.utils.GlobalClass;
import com.donor.needyturtle.utils.Util;
import com.sdsmdg.tastytoast.TastyToast;

import org.json.JSONObject;

import java.util.Map;


public class PostDataParser {

    private static final int MAX_RETRIES = 3;
    private static final int TIMEOUT = 10 * 1000;

    String TAG = "DayPos";

    private ProgressDialog progressDialog;

    private void showpDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    private void hidepDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    public PostDataParser(final Context context, String url,
                          final Map<String, String> params,
                          final boolean flag, final OnGetResponseListner listner) {

        if (!Util.isConnected(context)) {

            TastyToast.makeText(context, "No internet connections.",
                    TastyToast.LENGTH_SHORT, TastyToast.ERROR);

            listner.onGetResponse(null);
            return;
        }

        Log.d(TAG, "Url = "+url);
        Log.d(TAG, "Param = "+params);

        if (flag) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setMessage("Please wait...");
            showpDialog();
        }
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Util util = new Util();
                            JSONObject jobj = util.getjsonobject(response);

                            Log.d(TAG, "onResponse = "+response);

                            listner.onGetResponse(jobj);
                        } catch (Exception e) {
                            listner.onGetResponse(null);
                            e.printStackTrace();
                        }
                        if (flag)
                            hidepDialog();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (flag)
                    hidepDialog();
                listner.onGetResponse(null);
                VolleyLog.d("Error: " + error.getMessage());
                VolleyLog.d("Error: " + error.getCause());

                TastyToast.makeText(context, "Server error",
                        TastyToast.LENGTH_SHORT, TastyToast.ERROR);

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }

        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(TIMEOUT, MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        GlobalClass.getInstance().addToRequestQueue(postRequest);
    }


    public PostDataParser(final Context context, String url,
                          final Map<String, String> params,
                          final boolean flag, final View view,
                          final OnGetResponseListner listner) {
        if (!Util.isConnected(context)) {

            TastyToast.makeText(context, "No internet connections.",
                    TastyToast.LENGTH_SHORT, TastyToast.ERROR);


            listner.onGetResponse(null);
            return;
        }
        if (flag) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Please wait...");
            showpDialog();
        }
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Util util = new Util();
                            JSONObject jobj = util.getjsonobject(response);
                            listner.onGetResponse(jobj);
                        } catch (Exception e) {
                            listner.onGetResponse(null);
                            e.printStackTrace();
                        }
                        if (flag)
                            hidepDialog();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (flag)
                    hidepDialog();
                listner.onGetResponse(null);
                VolleyLog.d("Error: " + error.getMessage());

                TastyToast.makeText(context, "Server error",
                        TastyToast.LENGTH_SHORT, TastyToast.ERROR);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }

        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(TIMEOUT, MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        GlobalClass.getInstance().addToRequestQueue(postRequest);
    }

    public interface OnGetResponseListner {
        void onGetResponse(JSONObject response);
    }


}
