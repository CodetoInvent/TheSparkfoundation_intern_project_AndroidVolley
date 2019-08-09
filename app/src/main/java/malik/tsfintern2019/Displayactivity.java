package malik.tsfintern2019;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

import static java.security.AccessController.getContext;
import static malik.tsfintern2019.MainActivity.EDU_ID;

public class Displayactivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewpager;
    AppBarLayout appBarLayout;
    public static  int PERC_CERTI=0;
    public static int PERC_PIC_personal = 0;
     CircleImageView profile_pic_header;
   public static TextView profile_name_header,profile__email;
    public static String PROFILE_NAME,EDU_COLLEGE;
    static RequestQueue queue;
    public int you=1;
    public static  int XYZ=1;
   final int GALLERY=1;
   Button btnClose,cancel__,delete__,logout__;
    public static int DELETE_EDU_ID,DELETE_PROF_ID;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        tabLayout=findViewById(R.id.tabs);
        viewpager=findViewById(R.id.viewpager);
        appBarLayout=findViewById(R.id.appbarlayout);
        profile_pic_header=findViewById(R.id.profile_pic_header);
        profile_name_header=findViewById(R.id.profile_name_header);
        profile__email=findViewById(R.id.profile_edu_header);
        btnClose=findViewById(R.id.btnClose);


        ViewPageadapter viewPageadapter=new ViewPageadapter(getSupportFragmentManager());
        //adding fragments
        viewPageadapter.Addfragment(new Personal_Fragment(),"Personal");
        viewPageadapter.Addfragment(new Education_Fragment(),"Education");
        viewPageadapter.Addfragment(new Professional_Fragment(),"Professional");


        viewpager.setAdapter(viewPageadapter);
        tabLayout.setupWithViewPager(viewpager);
        //You tab icons
        int[] icons = {
                R.drawable.person_tab_pic,
                R.drawable.edu_tab,
                R.drawable.prof_tab

        };

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            Objects.requireNonNull(tabLayout.getTabAt(i)).setIcon(icons[i]);
        }

    btnClose.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {


        DialogAlert();
    }
});
        //set Prof name and email
        LoadNameandEmail();

    }

    private void DialogAlert() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this,R.style.CustomAlertDialog_personal);
        dialog.setCancelable(false);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.setting_alert, null);
        dialog.setView(dialogView);
        final AlertDialog alert = dialog.create();

        alert.show();

        cancel__= dialogView.findViewById(R.id.cancel__);
        delete__= dialogView.findViewById(R.id.delete__);
        delete__.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteDATA();
            }
        });
        logout__= dialogView.findViewById(R.id.logout__);
        cancel__.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.cancel();
            }
        });
        logout__.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Displayactivity.this, " Successfully Logout  " , Toast.LENGTH_LONG).show();
                Intent intent=new Intent(Displayactivity.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intent);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                finish();


            }
        });


    }

    private void DeleteDATA() {
        DeleteEducation();
    }

    private void DeleteEducation() {
        StringRequest deleteRequest = new StringRequest(Request.Method.DELETE, Constants.BASE_URL + "/user/educationdetail/"+DELETE_EDU_ID,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        Log.e("Success deleted Edu: "+DELETE_EDU_ID, response);
                        Log.e("deleted EDUID: "+EDU_ID, response);
                        DeleteProfessnal();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error deleting EDU "+DELETE_EDU_ID, error.toString());
                        Log.e("Error  EDUIU "+EDU_ID, error.toString());
                    }
                }
        );

        MySingeltonClass.getInstance(Displayactivity.this).addtoRequestQueue(deleteRequest);



    }

    private void DeleteProfessnal() {


        StringRequest deleteRequest = new StringRequest(Request.Method.DELETE,  Constants.BASE_URL + "/user/professionaldetail/"+DELETE_PROF_ID,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        Log.e("Success deleted Prof: "+DELETE_PROF_ID, response);

                        Log.e(" deleted Prof UID: "+EDU_ID, response);
                        Intent intent=new Intent(Displayactivity.this,MainActivity.class);
                        startActivity(intent);
                        Toast.makeText(Displayactivity.this, " Successfully Deleted  " , Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error deleting Prof"+DELETE_PROF_ID, error.toString());
                        Log.e("Error  Prof UID"+EDU_ID, error.toString());

                    }
                }
        );

        MySingeltonClass.getInstance(Displayactivity.this).addtoRequestQueue(deleteRequest);

    }






    public  void LoadProfilePic(){
       /*ImageRequest imageRequest=new ImageRequest(Constants.BASE_URL + "/user/personaldetail/profilepic/" + EDU_ID,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        profile_pic_header.setImageBitmap(response);
                    }

                }, 0, 0, ImageView.ScaleType.CENTER_CROP, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("Pic Load Error "+EDU_ID,error.toString());
            }
        });
        MySingeltonClass.getInstance(Displayactivity.this).addtoRequestQueue(imageRequest);*/
        //Loading Image from URL
       Picasso.get()
                .load(Constants.BASE_URL +"/user/personaldetail/profilepic/"+EDU_ID)
                .placeholder(R.drawable.default_profile)
                .error(R.drawable.error_occur)
                .fit()
                .into(profile_pic_header);
        LoadOrganization();

    }

  public  void LoadNameandEmail() {
      queue= Volley.newRequestQueue(this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Constants.BASE_URL + "/user/personaldetail/"+MainActivity.EDU_ID, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {


                    JSONObject data = response.getJSONObject("data");
                    String name = data.getString("name");
                    profile_name_header.setText(name);
                   // profile__email.setText(MainActivity.reg_email_s);
                    Log.v("SUcces set prof name"+EDU_ID,response.toString());
                   if(you==1) {
                       LoadProfilePic();
                       you=0;
                   }



                } catch (JSONException e) {
                    Log.v("nnnnnnnnnnn",e.toString());
                 }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VErr setting prof name"+EDU_ID, error.toString());

            }
        });

      MySingeltonClass.getInstance(Displayactivity.this).addtoRequestQueue(request);


    }
   public void LoadOrganization(){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Constants.BASE_URL + "/user/professionaldetail/"+ EDU_ID, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {


                    JSONObject data = response.getJSONObject("data");
                    String org = data.getString("organisation");
                    String desig = data.getString("designation");

                    profile__email.setText(desig+" | "+org);
                    Log.v("SUcces set prof name"+EDU_ID,response.toString());
                    if(you==1) {
                        LoadProfilePic();
                        you=0;
                    }



                } catch (JSONException e) {
                    Log.v("nnnnnnnnnnn",e.toString());
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VErr setting prof name"+EDU_ID, error.toString());

            }
        });

        MySingeltonClass.getInstance(Displayactivity.this).addtoRequestQueue(request);





    }

    @Override
    public void onBackPressed() {
        DialogAlert();
    }
}
