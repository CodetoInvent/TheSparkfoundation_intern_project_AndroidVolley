package malik.tsfintern2019;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static malik.tsfintern2019.MainActivity.EDU_ID;
import static malik.tsfintern2019.MainActivity.isInteger;
import static malik.tsfintern2019.Displayactivity.DELETE_PROF_ID;

public class Professional_Fragment extends Fragment {
    View view;
    TextView profes_detail;
    Button Update_profes_btn;
    //Professional detail declaration
    private EditText organisation_p,designation_p,start_date_p,end_date_p;
    public  String organisation_p_s,designation_p_s,start_date_p_s,end_date_p_s;
    public Button FINISH_btn;
    RequestQueue queue;
    String edit_desig,edit_orgga,edit_enddate,edit_startdate;

    public  Professional_Fragment(){

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.professional_detail_fragment,container,false);
        profes_detail=view.findViewById(R.id.profes_detail_tv);
        Update_profes_btn=view.findViewById(R.id.Update_profes_btn);

        Update_profes_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateProfesDetail();

            }
        });
        LoadNameandEmail();

        return view;
    }

    private void UpdateProfesDetail() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(Objects.requireNonNull(getContext()),R.style.CustomAlertDialog_personal);
        dialog.setCancelable(false);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.prof_detail, null);
        dialog.setView(dialogView);
        final AlertDialog alert = dialog.create();

        alert.show();
        queue = Volley.newRequestQueue(getContext());

        organisation_p= dialogView.findViewById(R.id.organisation_p);
        organisation_p.setText(edit_orgga);
        designation_p= dialogView.findViewById(R.id.designation_p);
        designation_p.setText(edit_desig);
        start_date_p = dialogView.findViewById(R.id.start_date_p);
        start_date_p.setText(edit_startdate);
        end_date_p = dialogView.findViewById(R.id.end_date_p);
        end_date_p.setText(edit_enddate);
        FINISH_btn = dialogView.findViewById(R.id.FINISH_btn);




       FINISH_btn.setText("SAVE");

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



                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, Constants.BASE_URL + "/user/professionaldetail/"+EDU_ID, reqParams, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {



                            Log.e("PUT Prof detail success"+EDU_ID, response.toString());
                            Toast.makeText(getContext(), "DETAIL SUCCESSFULLY UPDATED ",
                                    Toast.LENGTH_LONG).show();
                            LoadNameandEmail();


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("VErr putting profes det"+EDU_ID, error.toString());


                        }
                    });
                    MySingeltonClass.getInstance(getContext()).addtoRequestQueue(request);



                    alert.cancel();



                }






            }

        });


    }

    void LoadNameandEmail() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Constants.BASE_URL + "/user/professionaldetail/"+ EDU_ID, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {


                    JSONObject data = response.getJSONObject("data");
                    String enddate= data.getString("end_date");
                    String organisation= data.getString("organisation");
                    String startdate= data.getString("start_date");
                    String designation= data.getString("designation");
                    int idd=data.getInt("id");
                    DELETE_PROF_ID=idd;
                    edit_enddate=enddate;
                    edit_startdate=startdate;
                    edit_orgga=organisation;
                    edit_desig=designation;

                    if(!isInteger(enddate)){
                        enddate="Currently Working";
                        edit_enddate=enddate;

                    }



                    profes_detail.setText("\n"+"    Organisation :  "+organisation+"    "+"\n\n"+"    Designation  :  "+designation+"    "+"\n\n"+"    Start Year      :  "+startdate+"    "+"\n\n"+"    End Year        :  "+enddate+"    "+"\n");
                    Log.v("SUcces set profes detai"+EDU_ID,response.toString());
                    Displayactivity displayactivity=(Displayactivity) getActivity();
                    Objects.requireNonNull(displayactivity).LoadOrganization();




                } catch (JSONException e) {
                    Log.v("nnnnnnnnnnn",e.toString());
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VErr setting profes det"+EDU_ID, error.toString());

            }
        });

        MySingeltonClass.getInstance(getContext()).addtoRequestQueue(request);


    }






}
