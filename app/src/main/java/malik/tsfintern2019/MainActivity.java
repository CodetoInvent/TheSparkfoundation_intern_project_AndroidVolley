package malik.tsfintern2019;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.SyncStateContract;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {
    String URL_1;
    ArrayList<HashMap<String, String>> arraylist;
   public static int user_id,FLAG=0,SIGNUP=0,EDU_SIGNUP=0,EDU_ID,ED_DETAILS=0,PERSONAL_=0,EDUCATION_=0;
    final int GALLERY = 1;
    Button reg_next;
   public static String PROFILE_EMAIL,reg_username_s,reg_password_s,reg_full_name_s,reg_mobile_no_s,reg_location_s, reg_link_s,reg_skills_s,password_s,email_s,reg_email_s;
    EditText username, Password, reg_username, reg_password,reg_email,
            reg_full_name, reg_mobile_no, reg_location, reg_skills,reg_links;
    Button login, signUp, reg_register;
    TextInputLayout txtInLayoutUsername, txtInLayoutPassword, txtInLayoutRegPassword;
    public RequestQueue queue,queue1;
    RequestQueue queue2,queue3;
    public Button Upload_profile_btn;
    public ImageView profile_imageView;
    public  Bitmap bitmap,bitmapedu;
    //Education detail declaration
    public  EditText college_name,college_course,college_location,college_start_year,college_end_year;
    public static String college_name_s,college_course_s,college_location_s,college_start_year_s,college_end_year_s;
    public   Button Upload_Cert_btn,NEXT_btn,exit_yes_btn,exit_no_btn;
      public  ImageView Cert_imageview;

      //Professional detail declaration
    private  EditText organisation_p,designation_p,start_date_p,end_date_p;
    public static String organisation_p_s,designation_p_s,start_date_p_s,end_date_p_s;
    public Button FINISH_btn;
    static  String ImageName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.username);
        Password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        signUp = findViewById(R.id.signUp);
        txtInLayoutUsername = findViewById(R.id.txtInLayoutUsername);
        txtInLayoutPassword = findViewById(R.id.txtInLayoutPassword);
        //reg_register=findViewById(R.id.reg_register);
        //profile_imageView= findViewById(R.id.Profile_imageview);
       // Cert_imageview= findViewById(R.id.Cert_imageview);

        queue = Volley.newRequestQueue(this);
        queue1 = Volley.newRequestQueue(this);
        queue2=Volley.newRequestQueue(this);
        queue3=Volley.newRequestQueue(this);
        requestMultiplePermissions();
        ClickLogin();


        //SignUp's Button for showing registration page
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClickSignUp();
            }
        });



    }

    //This is method for doing operation of check login
    private void ClickLogin() {

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar snackbar = Snackbar.make(view, "Please fill out these fields",
                        Snackbar.LENGTH_LONG);
                View snackbarView = snackbar.getView();
                snackbarView.setBackgroundColor(getResources().getColor(R.color.red));

                if (username.getText().toString().trim().isEmpty() || Password.getText().toString().trim().isEmpty()) {


                    if (username.getText().toString().trim().isEmpty()) {

                        snackbar.show();
                        txtInLayoutUsername.setError("Username should not be empty");
                    } else{
                        txtInLayoutUsername.setError("");

                    }
                    if (Password.getText().toString().trim().isEmpty()) {
                        snackbar.show();
                        txtInLayoutPassword.setError("Password should not be empty");
                    }
                    else{
                        txtInLayoutPassword.setError("");
                    }
                }

                else{
                    txtInLayoutUsername.setError("");
                    txtInLayoutPassword.setError("");

                    Login_when();
                }


            }

        });

    }
    void Login_when(){


             email_s = username.getText().toString();
             password_s =Password.getText().toString();
            if(email_s != null && password_s != null) {
                Map<String, String> param = new HashMap<>();
                param.put("email", email_s);
                param.put("password", password_s);
                JSONObject reqParams = new JSONObject(param);

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, Constants.BASE_URL + "/user/login", reqParams, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject data = response.getJSONObject("data");
                            int id = data.getInt("id");
                            String recdemail = data.getString("email");
                            EDU_ID=id;
                            FLAG=101;
                            Toast.makeText(MainActivity.this, "Login Successful with id " + id + " and email " + recdemail,
                                    Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(MainActivity.this,Displayactivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                            finish();


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Login Unsuccessful", Toast.LENGTH_LONG).show();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VErr", error.toString());

                    }
                });
                MySingeltonClass.getInstance(MainActivity.this).addtoRequestQueue(request);


            }

        }


    //The method for opening the registration page and another processes or checks for registering
    private void ClickSignUp() {
        PERSONAL_=1;
        EDUCATION_=0;

          AlertDialog.Builder dialog = new AlertDialog.Builder(this,R.style.CustomAlertDialog);
         dialog.setCancelable(false);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.register, null);
        dialog.setView(dialogView);
        final AlertDialog alert = dialog.create();
        alert.show();


        reg_username = dialogView.findViewById(R.id.reg_username);
        reg_password = dialogView.findViewById(R.id.reg_password);
        reg_register=dialogView.findViewById(R.id.reg_register);
           txtInLayoutRegPassword = dialogView.findViewById(R.id.txtInLayoutRegPassword);





        reg_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(reg_username.getText().toString().trim().isEmpty()||reg_password.getText().toString().trim().isEmpty()) {
                    if (reg_username.getText().toString().trim().isEmpty()) {

                        reg_username.setError("Please fill out this field");
                    }
                    if (reg_password.getText().toString().trim().isEmpty()) {
                        txtInLayoutRegPassword.setPasswordVisibilityToggleEnabled(false);
                        reg_password.setError("Please fill out this field");
                    }
                   }
                else{



                         reg_username_s=reg_username.getText().toString();
                    reg_password_s=reg_password.getText().toString();
                       Map<String, String> param1 = new HashMap<>();
                    param1.put("email", reg_username_s);
                    param1.put("password", reg_password_s);

                        JSONObject reqParams1 = new JSONObject(param1);



                        JsonObjectRequest request1 = new JsonObjectRequest(Request.Method.POST, Constants.BASE_URL + "/user/signup", reqParams1, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                try {
                                    JSONObject data = response.getJSONObject("data");
                                    int id = data.getInt("id");
                                    user_id=id;
                                    Personal_details();

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                Toast.makeText(MainActivity.this, "SignUP Successful with user id: "+user_id,
                                        Toast.LENGTH_LONG).show();


                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("VErr", error.toString());


                            }
                        });
                    MySingeltonClass.getInstance(MainActivity.this).addtoRequestQueue(request1);





                    alert.cancel();



                }






            }

        });





    }

    void Personal_details(){
        SIGNUP=1;
        EDU_SIGNUP=0;
        PERSONAL_=1;
        EDUCATION_=0;
        AlertDialog.Builder dialog = new AlertDialog.Builder(this,R.style.CustomAlertDialog_personal);
        dialog.setCancelable(false);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.personal_detail, null);
        dialog.setView(dialogView);
        final AlertDialog alert = dialog.create();

        alert.show();
        reg_full_name = dialogView.findViewById(R.id.reg_full_name);
        reg_mobile_no = dialogView.findViewById(R.id.reg_mobile_no);
        reg_location = dialogView.findViewById(R.id.reg_location);
        reg_skills = dialogView.findViewById(R.id.reg_skills);
        reg_links = dialogView.findViewById(R.id.reg_links);
        reg_email=dialogView.findViewById(R.id.reg_email);
        profile_imageView=dialogView.findViewById(R.id.Profile_imageview);
        Upload_profile_btn=dialogView.findViewById(R.id.Upload_Profile_btn);
        NEXT_btn =dialogView.findViewById(R.id.NEXT_btn);
        Upload_profile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(galleryIntent, GALLERY);
            }
        });
        reg_next=dialogView.findViewById(R.id.reg_next);
        reg_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String mobile=reg_mobile_no.getText().toString().trim();

                if(!isInteger(mobile)||reg_email.getText().toString().trim().isEmpty()||reg_full_name.getText().toString().trim().isEmpty()||reg_mobile_no.getText().toString().trim().isEmpty()||reg_location.getText().toString().trim().isEmpty()||reg_skills.getText().toString().trim().isEmpty()||reg_links.getText().toString().trim().isEmpty()) {

                    if (reg_full_name.getText().toString().trim().isEmpty()) {

                        reg_full_name.setError("Please fill out this field");
                    }
                    if (reg_mobile_no.getText().toString().trim().isEmpty()) {

                        reg_mobile_no.setError("Please fill out this field");

                    }
                    if (!isInteger(mobile)) {

                        reg_mobile_no.setError("Mobile No. should contain only digits");

                    }

                    if (reg_location.getText().toString().trim().isEmpty()) {

                        reg_location.setError("Please fill out this field");
                    }
                    if (reg_skills.getText().toString().trim().isEmpty()) {

                        reg_skills.setError("Please fill out this field");
                    }
                    if (reg_links.getText().toString().trim().isEmpty()) {

                        reg_links.setError("Please fill out this field");
                    }
                    if (reg_email.getText().toString().trim().isEmpty()) {

                        reg_email.setError("Please fill out this field");
                    }
                }
                else{



                    reg_mobile_no_s=reg_mobile_no.getText().toString();
                    reg_link_s=reg_links.getText().toString();
                    reg_skills_s=reg_skills.getText().toString();
                    reg_full_name_s=reg_full_name.getText().toString();
                    reg_location_s=reg_location.getText().toString();
                    reg_email_s=reg_email.getText().toString();
                      Map<String, String> param2 = new HashMap<>();
                    param2.put("skills", reg_skills_s);
                    param2.put("mobile_no", reg_mobile_no_s);
                    param2.put("name", reg_full_name_s);
                    param2.put("links", reg_link_s);
                    param2.put("location", reg_location_s);
                    //param2.put("email", reg_email_s);


                    JSONObject reqParams2 = new JSONObject(param2);
                    JsonObjectRequest request2 = new JsonObjectRequest(Request.Method.POST, Constants.BASE_URL + "/user/personaldetail/"+user_id, reqParams2, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {


                            Log.e("Profile Saved...UID."+user_id,response.toString());
                            UploadImages1();






                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("VErr", error.toString());

                        }
                    });
                    MySingeltonClass.getInstance(MainActivity.this).addtoRequestQueue(request2);


                    alert.cancel();



                }







            }

        });




    }



    void Education_detail(){
        EDU_SIGNUP=1;
        SIGNUP=0;
        EDUCATION_=1;
        PERSONAL_=0;

        AlertDialog.Builder dialog = new AlertDialog.Builder(this,R.style.CustomAlertDialog_personal);
        dialog.setCancelable(false);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.education_form, null);
        dialog.setView(dialogView);
        final AlertDialog alert = dialog.create();

        alert.show();

        college_name= dialogView.findViewById(R.id.college_name);
        college_course= dialogView.findViewById(R.id.college_course);
        college_location = dialogView.findViewById(R.id.college_location);
        college_start_year = dialogView.findViewById(R.id.college_start_year);
        college_end_year = dialogView.findViewById(R.id.college_end_year);
        Upload_Cert_btn = dialogView.findViewById(R.id.Upload_Cert_btn);
        Cert_imageview = dialogView.findViewById(R.id.Cert_imageview);
         NEXT_btn =dialogView.findViewById(R.id.NEXT_btn);
          Upload_Cert_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(galleryIntent, GALLERY);

            }
        });





        NEXT_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String syear=college_start_year.getText().toString().trim();
                String eyear=college_end_year.getText().toString().trim();

                if(!isInteger(eyear)||!isInteger(syear)||college_start_year.getText().toString().trim().isEmpty()||college_end_year.getText().toString().trim().isEmpty()||college_name.getText().toString().trim().isEmpty()||college_course.getText().toString().trim().isEmpty()||college_location.getText().toString().trim().isEmpty()) {
                    if (college_location.getText().toString().trim().isEmpty()) {

                        college_location.setError("Please fill out this field");
                    }
                    if (college_course.getText().toString().trim().isEmpty()) {

                        college_course.setError("Please fill out this field");
                    }
                    if (college_name.getText().toString().trim().isEmpty()) {

                        college_name.setError("Please fill out this field");

                    }
                    if (!isInteger(syear)) {

                        college_start_year.setError("Year should contain only digits");

                    }
                    if (!isInteger(eyear)) {

                        college_end_year.setError("Year should contain only digits");

                    }

                    if (college_start_year.getText().toString().trim().isEmpty()) {

                        college_start_year.setError("Please fill out this field");
                    }
                    if (college_end_year.getText().toString().trim().isEmpty()) {

                        college_end_year.setError("Please fill out this field");
                    }

                }
                else{


                    college_name_s=college_name.getText().toString();
                    college_course_s=college_course.getText().toString();
                    college_location_s=college_location.getText().toString();
                    college_start_year_s=college_start_year.getText().toString();
                    college_end_year_s=college_end_year.getText().toString();


                    Map<String, String> param2 = new HashMap<>();



                    param2.put("degree", college_course_s);
                    param2.put("organisation", college_name_s);
                    param2.put("location", college_location_s);
                    param2.put("start_year", college_start_year_s);
                    param2.put("end_year", college_end_year_s);




                    JSONObject reqParams2 = new JSONObject(param2);
                    JsonObjectRequest request2 = new JsonObjectRequest(Request.Method.POST, Constants.BASE_URL + "/user/educationdetail/"+user_id, reqParams2, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {


                            Log.e("Education detail sent..",response.toString());

                            UploadImages2();


                            ED_DETAILS=10;
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("VErr education detail", error.toString());

                        }
                    });
                    MySingeltonClass.getInstance(MainActivity.this).addtoRequestQueue(request2);



                    alert.cancel();






                }






            }

        });







    }







    public static boolean isInteger(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        if (length == 0) {
            return false;
        }
        int i = 0;
        if (str.charAt(0) == '-') {
            if (length == 1) {
                return false;
            }
            i = 1;
        }
        for (; i < length; i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                Uri contentURI2=data.getData();

                try {
                     bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    bitmapedu = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI2);


                if(PERSONAL_==1&&EDUCATION_==0) {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    Log.v("Personal=1.....",bitmap.toString());

                    ImageName="file_avatar";
                     profile_imageView.setImageBitmap(bitmap);


                 }

                    if(EDUCATION_==1&&PERSONAL_==0) {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                        Log.v("education=1..........",bitmap.toString());
                        ImageName="cert_avatar";
                         Cert_imageview.setImageBitmap(bitmap);

                     }







                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }








    private void  requestMultiplePermissions(){
        Dexter.withActivity(this)
                .withPermissions(

                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                           // Toast.makeText(getApplicationContext(), "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings

                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Some Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }











    void Professional_detail(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this,R.style.CustomAlertDialog_personal);
        dialog.setCancelable(false);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.prof_detail, null);
        dialog.setView(dialogView);
        final AlertDialog alert = dialog.create();

        alert.show();

        organisation_p= dialogView.findViewById(R.id.organisation_p);
          designation_p= dialogView.findViewById(R.id.designation_p);
        start_date_p = dialogView.findViewById(R.id.start_date_p);
        end_date_p = dialogView.findViewById(R.id.end_date_p);
        FINISH_btn = dialogView.findViewById(R.id.FINISH_btn);






        FINISH_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String syear=start_date_p.getText().toString().trim();


                if(!isInteger(syear)||start_date_p.getText().toString().trim().isEmpty()||end_date_p.getText().toString().trim().isEmpty()||organisation_p.getText().toString().trim().isEmpty()||designation_p.getText().toString().trim().isEmpty()) {
                    if (organisation_p.getText().toString().trim().isEmpty()) {

                        organisation_p.setError("Please fill out this field");
                    }
                    if (end_date_p.getText().toString().trim().isEmpty()) {

                        end_date_p.setError("Please fill out this field");
                    }
                    if (designation_p.getText().toString().trim().isEmpty()) {

                       designation_p.setError("Please fill out this field");

                    }
                    if (!isInteger(syear)) {

                        start_date_p.setError("Year should contain only digits");

                    }


                    if (start_date_p.getText().toString().trim().isEmpty()) {

                        start_date_p.setError("Please fill out this field");
                    }


                }
                else{


                    organisation_p_s=organisation_p.getText().toString();
                    designation_p_s=designation_p.getText().toString();
                    start_date_p_s=start_date_p.getText().toString();
                    end_date_p_s=end_date_p.getText().toString();

                    Map<String, String> param = new HashMap<>();



                    param.put("organisation", organisation_p_s);
                    param.put("designation", designation_p_s);
                    param.put("start_date", start_date_p_s);
                    param.put("end_date", end_date_p_s);

                    JSONObject reqParams = new JSONObject(param);



                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, Constants.BASE_URL + "/user/professionaldetail/"+user_id, reqParams, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {


                            Log.e("Prof detail successID: "+user_id, response.toString());
                            Toast.makeText(MainActivity.this, "DETAIL SUCCESSFULLY UPLOADED ",
                                    Toast.LENGTH_LONG).show();


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("VErr", error.toString());


                        }
                    });
                    MySingeltonClass.getInstance(MainActivity.this).addtoRequestQueue(request);



                    alert.cancel();



                }






            }

        });







    }


    private void UploadImages1() {
        EDUCATION_=0;
        PERSONAL_=1;
        //profile_imageView.setImageBitmap(bitmap);

        VolleyMultipartRequest1 multipartRequest = new VolleyMultipartRequest1(Request.Method.POST, Constants.BASE_URL+"/user/personaldetail/profilepic", new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                EDUCATION_=1;
                PERSONAL_=0;
                Education_detail();

                 Log.i("success upload pic"+user_id, response.toString());


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i("Error upload pic"+user_id, error.toString());
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("uid", String.valueOf(user_id));
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                // file name could found file base or direct access from real path
                // for now just get bitmap data from ImageView


                params.put("photo", new DataPart(ImageName+".jpg", AppHelper.getFileDataFromDrawable(getBaseContext(), profile_imageView.getDrawable()), "image/jpeg"));
                Log.v("Image Name",ImageName);
                return params;
            }
        };

        VolleySingleton.getInstance(getBaseContext()).addToRequestQueue(multipartRequest);
    }
    private void UploadImages2() {
        EDUCATION_=1;
        PERSONAL_=0;
       // Cert_imageview.setImageBitmap(bitmap);

        VolleyMultipartRequest1 multipartRequest = new VolleyMultipartRequest1(Request.Method.POST, Constants.BASE_URL+"/user/educationdetail/certificate", new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                EDUCATION_=0;
                PERSONAL_=1;
                Professional_detail();
                Log.i("success upload cert "+user_id, response.toString());


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i("Error upload cert "+user_id, error.toString());
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("uid", String.valueOf(user_id));
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                // file name could found file base or direct access from real path
                // for now just get bitmap data from ImageView


                params.put("photo", new DataPart(ImageName+".jpg", AppHelper.getFileDataFromDrawable(getBaseContext(), Cert_imageview.getDrawable()), "image/jpeg"));
                Log.v("Image Name",ImageName);
                return params;
            }
        };

        VolleySingleton.getInstance(getBaseContext()).addToRequestQueue(multipartRequest);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this,R.style.CustomAlertDialog);
        dialog.setCancelable(false);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.exit_app_alert, null);
        dialog.setView(dialogView);
        final AlertDialog alert = dialog.create();
        alert.show();
        exit_no_btn=dialogView.findViewById(R.id.exit_no_btn);
        exit_yes_btn=dialogView.findViewById(R.id.exit_yes_btn);
        exit_no_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.cancel();
            }
        });
exit_yes_btn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        finish();
    }
});
    }

}





