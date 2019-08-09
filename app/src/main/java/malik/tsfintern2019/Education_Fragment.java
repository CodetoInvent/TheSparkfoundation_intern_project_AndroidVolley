package malik.tsfintern2019;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static android.app.Activity.RESULT_CANCELED;
import static malik.tsfintern2019.Displayactivity.DELETE_EDU_ID;
import static malik.tsfintern2019.Displayactivity.PERC_CERTI;
import static malik.tsfintern2019.Displayactivity.PERC_PIC_personal;
import static malik.tsfintern2019.Displayactivity.XYZ;
import static malik.tsfintern2019.MainActivity.EDU_ID;
import static malik.tsfintern2019.MainActivity.isInteger;
import static malik.tsfintern2019.MainActivity.user_id;


public class Education_Fragment extends Fragment {
    View view;


    ImageView edu_detail_imview;
    TextView edu_detail_tv;
    Button Update_edu_btn;
    public  EditText college_name,college_course,college_location,college_start_year,college_end_year;
    public static String college_name_s,college_course_s,college_location_s,college_start_year_s,college_end_year_s;
    public   Button Upload_Cert_btn,NEXT_btn;
    public  ImageView Cert_imageview;
    RequestQueue queue;
    Bitmap bitmapedu;
    int ABCD;
    String edit_degree,edit_orgga,edit_enddate,edit_startdate,edit_location;
    CardView cardView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.education_detail_fragment,container,false);
        edu_detail_imview=view.findViewById(R.id.edu_detail_imview);
        edu_detail_tv=view.findViewById(R.id.edu_detail_tv);
        Update_edu_btn=view.findViewById(R.id.Update_edu_btn);
        cardView=view.findViewById(R.id.edu_card);
            ABCD=1;
        Log.v("Edu_frag education="+PERC_CERTI,"Edu_frag  personal value"+PERC_PIC_personal);


        Update_edu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                UpdateEduDetail();
            }
        });


        LoadEducationDetail();


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edu_detail_imview=view.findViewById(R.id.edu_detail_imview);
        edu_detail_tv=view.findViewById(R.id.edu_detail_tv);
        Update_edu_btn=view.findViewById(R.id.Update_edu_btn);
        ABCD=1;
        Log.v("Edu_frag education="+PERC_CERTI,"Edu_frag  personal value"+PERC_PIC_personal);


        Update_edu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                UpdateEduDetail();
            }
        });


        LoadEducationDetail();

    }

    private void UpdateEduDetail() {
        PERC_CERTI=1;
        PERC_PIC_personal=0;
        Log.v("Edu_fra Updatemeth Educ"+PERC_CERTI,"Edu_frag  personal value"+PERC_PIC_personal);


        AlertDialog.Builder dialog = new AlertDialog.Builder(Objects.requireNonNull(getContext()),R.style.CustomAlertDialog_personal);
        dialog.setCancelable(false);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.education_form, null);
        dialog.setView(dialogView);
        final AlertDialog alert = dialog.create();

        alert.show();


        college_name= dialogView.findViewById(R.id.college_name);
        college_name.setText(edit_orgga);
        college_course= dialogView.findViewById(R.id.college_course);
        college_course.setText(edit_degree);
        college_location = dialogView.findViewById(R.id.college_location);
        college_location.setText(edit_location);
        college_start_year = dialogView.findViewById(R.id.college_start_year);
        college_start_year.setText(edit_startdate);
        college_end_year = dialogView.findViewById(R.id.college_end_year);
        college_end_year.setText(edit_enddate);
        Upload_Cert_btn = dialogView.findViewById(R.id.Upload_Cert_btn);
        Upload_Cert_btn.setVisibility(View.GONE);
        Cert_imageview = dialogView.findViewById(R.id.Cert_imageview);
        Cert_imageview.setVisibility(View.GONE);
        NEXT_btn =dialogView.findViewById(R.id.NEXT_btn);

         NEXT_btn.setText("SAVE");

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
                    JsonObjectRequest request2 = new JsonObjectRequest(Request.Method.PUT, Constants.BASE_URL + "/user/educationdetail/"+EDU_ID, reqParams2, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {


                            Log.e("Education detail update"+EDU_ID,response.toString());
                           // UpdateImages();
                            LoadEducationDetail();




                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("VErr education detail "+EDU_ID, error.toString());

                        }
                    });
                    MySingeltonClass.getInstance(getContext()).addtoRequestQueue(request2);


                   // uploadImage(bitmapedu);

                    alert.cancel();






                }






            }

        });

    }


    private void LoadEducationDetail() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Constants.BASE_URL + "/user/educationdetail/" + EDU_ID, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {


                    JSONObject data = response.getJSONObject("data");
                    String enddate= data.getString("end_year");
                    String organisation= data.getString("organisation");
                    String startdate= data.getString("start_year");
                    String degree=data.getString("degree");

                    String locations = data.getString("location");
                    int idd=data.getInt("id");
                    DELETE_EDU_ID=idd;
                    edit_degree=degree;
                    edit_orgga=organisation;
                    edit_enddate=enddate;
                    edit_startdate=startdate;
                    edit_location=locations;




                    edu_detail_tv.setText("\n" + "    College     :  " + organisation + "    " + "\n\n" + "    Degree      :  " + degree + "    " + "\n\n" + "    Location   :  " + locations + "    " + "\n\n" + "    Start Year :  " + startdate + "    " + "\n\n"+ "    End year   :  " + enddate + "    " + "\n");


                    Log.v("SUcces set EDU detai", response.toString());
                    LoadEDUCERT();



                } catch (JSONException e) {
                    Log.v("nnnnnnnnnnn", e.toString());
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VErr setting EDU det"+EDU_ID, error.toString());

            }
        });

        MySingeltonClass.getInstance(getContext()).addtoRequestQueue(request);

    }

    public  void LoadEDUCERT(){
       /* ImageRequest imageRequest=new ImageRequest(Constants.BASE_URL + "/user/educationdetail/certificate/" + EDU_ID,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        edu_detail_imview.setImageBitmap(response);
                    }

                }, 0, 0, ImageView.ScaleType.CENTER_CROP, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("Pic load Error"+EDU_ID,error.toString());
            }
        });
        MySingeltonClass.getInstance(getContext()).addtoRequestQueue(imageRequest);*/
        //Loading Image from URL
        Picasso.get()
                .load(Constants.BASE_URL +"/user/educationdetail/certificate/"+EDU_ID)
                .placeholder(R.drawable.default_profile)
                .error(R.drawable.error_occur)
                .fit()
                .into(edu_detail_imview);
    }






}
