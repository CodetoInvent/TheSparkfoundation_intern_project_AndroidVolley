package malik.tsfintern2019;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static android.app.Activity.RESULT_CANCELED;

import static malik.tsfintern2019.Displayactivity.PERC_CERTI;
import static malik.tsfintern2019.Displayactivity.PERC_PIC_personal;
import static malik.tsfintern2019.MainActivity.EDUCATION_;
import static malik.tsfintern2019.MainActivity.EDU_ID;
import static malik.tsfintern2019.MainActivity.PERSONAL_;

import static malik.tsfintern2019.MainActivity.isInteger;
import static malik.tsfintern2019.MainActivity.user_id;

public class Personal_Fragment extends Fragment {
    final int GALLERY = 1;

    View view;
    TextView personal_detail_tv, Link_Update;
    Button Update_personal_btn, Upload_profile_btn, NEXT_btn, reg_next;
    EditText reg_email, reg_full_name, reg_mobile_no, reg_location, reg_skills, reg_links;
    ImageView profile_imageView;
    String reg_mobile_no_s, reg_link_s, reg_skills_s, reg_full_name_s, reg_location_s, reg_email_s;
    RequestQueue queue;
    String  edit_skills,edit_mobile,edit_name,edit_link,edit_location;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.personal_detail_fragment, container, false);
        personal_detail_tv = view.findViewById(R.id.personal_detail_tv);
        Update_personal_btn = view.findViewById(R.id.Update_personal_btn);

        PERC_PIC_personal = 1;
        PERC_CERTI=0;
        Log.v("pers_frag personal="+PERC_PIC_personal,"Pers_frag  Edu value"+PERC_CERTI);

        Update_personal_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UpdatePersonalDetail();

            }
        });
        LoadPersonalDetail();
        return view;
    }

    @SuppressLint("SetTextI18n")
    private void UpdatePersonalDetail() {


        PERC_PIC_personal = 1;
        PERC_CERTI=0;
        Log.v("pers_frg updtemeth per="+PERC_PIC_personal,"Pers_frag  Edu value"+PERC_CERTI);

        AlertDialog.Builder dialog = new AlertDialog.Builder(Objects.requireNonNull(getContext()), R.style.CustomAlertDialog_personal);
        dialog.setCancelable(false);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.personal_detail, null);
        dialog.setView(dialogView);
        final AlertDialog alert = dialog.create();
        alert.show();
        queue = Volley.newRequestQueue(getContext());
        reg_full_name = dialogView.findViewById(R.id.reg_full_name);
        reg_full_name.setText(edit_name);
        reg_mobile_no = dialogView.findViewById(R.id.reg_mobile_no);
        reg_mobile_no.setText(edit_mobile);
        reg_location = dialogView.findViewById(R.id.reg_location);
        reg_location.setText(edit_location);
        reg_skills = dialogView.findViewById(R.id.reg_skills);
        reg_skills.setText(edit_skills);
        reg_links = dialogView.findViewById(R.id.reg_links);
        reg_links.setText(edit_link);
        reg_email = dialogView.findViewById(R.id.reg_email);
        reg_email.setVisibility(View.GONE);
        profile_imageView = dialogView.findViewById(R.id.Profile_imageview);
        profile_imageView.setVisibility(View.GONE);
        Upload_profile_btn = dialogView.findViewById(R.id.Upload_Profile_btn);
        Upload_profile_btn.setVisibility(View.GONE);

        reg_next = dialogView.findViewById(R.id.reg_next);
        reg_next.setText("SAVE");

        reg_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String mobile = reg_mobile_no.getText().toString().trim();

                if (!isInteger(mobile)  || reg_full_name.getText().toString().trim().isEmpty() || reg_mobile_no.getText().toString().trim().isEmpty() || reg_location.getText().toString().trim().isEmpty() || reg_skills.getText().toString().trim().isEmpty() || reg_links.getText().toString().trim().isEmpty()) {

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

                } else {


                    reg_mobile_no_s = reg_mobile_no.getText().toString();
                    reg_link_s = reg_links.getText().toString();
                    reg_skills_s = reg_skills.getText().toString();
                    reg_full_name_s = reg_full_name.getText().toString();
                    reg_location_s = reg_location.getText().toString();
                    reg_email_s = reg_email.getText().toString();
                    Map<String, String> param2 = new HashMap<>();
                    param2.put("skills", reg_skills_s);
                    param2.put("mobile_no", reg_mobile_no_s);
                    param2.put("name", reg_full_name_s);
                    param2.put("links", reg_link_s);
                    param2.put("location", reg_location_s);
                   // param2.put("email", reg_email_s);


                    JSONObject reqParams2 = new JSONObject(param2);
                    JsonObjectRequest request2 = new JsonObjectRequest(Request.Method.PUT, Constants.BASE_URL + "/user/personaldetail/" + EDU_ID, reqParams2, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {


                            Log.e("Profile Updated..UID." + EDU_ID, response.toString());

                           // UpdateImages();
                            LoadPersonalDetail();


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("VErr updating pro"+EDU_ID, error.toString());

                        }
                    });
                    MySingeltonClass.getInstance(getContext()).addtoRequestQueue(request2);


                   // uploadImage(bitmap);






                    alert.cancel();


                }


            }

        });

    }




    private void LoadPersonalDetail() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Constants.BASE_URL + "/user/personaldetail/" + EDU_ID, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {


                    JSONObject data = response.getJSONObject("data");
                    String skills = data.getString("skills");
                    String mobile_number = data.getString("mobile_no");
                    String name = data.getString("name");
                    String links = data.getString("links");
                    String locations = data.getString("location");
                    edit_skills=skills;
                    edit_mobile=mobile_number;
                    edit_name=name;
                    edit_link=links;
                    edit_location=locations;

                        personal_detail_tv.setText("\n" + "    Name         :  " + name + "    " + "\n\n" + "    Mobile No :  " + mobile_number + "    " + "\n\n" + "    Location    :  " + locations + "    " + "\n\n" + "    Skills          :  " + skills + "    " + "\n\n"+ "    Links          :  " + links + "    " + "\n");




                    Log.v("SUcces set person detai"+EDU_ID, response.toString());
                    Displayactivity displayactivity=(Displayactivity) getActivity();
                    Objects.requireNonNull(displayactivity).LoadNameandEmail();



                } catch (JSONException e) {
                    Log.v("nnnnnnnnnnn", e.toString());
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VErr setting person det"+EDU_ID, error.toString());

            }
        });

        MySingeltonClass.getInstance(getContext()).addtoRequestQueue(request);


    }



}
