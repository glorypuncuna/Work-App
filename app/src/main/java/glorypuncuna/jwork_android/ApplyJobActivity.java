package glorypuncuna.jwork_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ApplyJobActivity extends AppCompatActivity {
    private int jobseekerId, jobId, bonus;
    private String jobName, jobCategory, selectedPayment, referralCode;
    private double jobFee, totalFee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_job);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        TextView tvJobName = findViewById(R.id.job_name);
        TextView tvJobCategory = findViewById(R.id.job_category);
        TextView tvJobFee = findViewById(R.id.job_fee);
        TextView tvTotalFee = findViewById(R.id.total_fee);
        TextView textCode = findViewById(R.id.textCode);
        EditText edtReferralCode = findViewById(R.id.referral_code);
        Button btnApply = findViewById(R.id.btnApply);
        Button btnHitung = findViewById(R.id.hitung);
        RadioButton rdEwallet = findViewById(R.id.ewallet);
        RadioButton rdBank = findViewById(R.id.bank);
        RadioGroup rgPayment = findViewById(R.id.rg_payment);

        btnApply.setVisibility(View.INVISIBLE);
        textCode.setVisibility(View.INVISIBLE);
        edtReferralCode.setVisibility(View.INVISIBLE);

        jobId = getIntent().getIntExtra("jobId", 0);
        jobName = getIntent().getStringExtra("jobName");
        jobCategory = getIntent().getStringExtra("jobCategory");
        jobFee = getIntent().getDoubleExtra("jobFee", 0);
        jobseekerId = getIntent().getIntExtra("jobseekerId", 0);

        tvTotalFee.setText("0");
        tvJobName.setText(jobName);
        tvJobCategory.setText(jobCategory);
        tvJobFee.setText(Double.toString(jobFee));

        rgPayment.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.ewallet:
                    textCode.setVisibility(View.VISIBLE);
                    edtReferralCode.setVisibility(View.VISIBLE);
                    selectedPayment = "E-Wallet";
                    break;
                case R.id.bank:
                    textCode.setVisibility(View.INVISIBLE);
                    edtReferralCode.setVisibility(View.INVISIBLE);
                    selectedPayment = "Bank";
                    break;
            }
        });

        btnHitung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedPayment.equals("Bank")){
                    totalFee = jobFee;
                    tvTotalFee.setText(Double.toString(totalFee));
                    btnApply.setVisibility(View.VISIBLE);
                    btnHitung.setVisibility(View.INVISIBLE);
                } else if (selectedPayment.equals("E-Wallet")){
                    referralCode = edtReferralCode.getText().toString();
                    btnApply.setVisibility(View.VISIBLE);
                    btnHitung.setVisibility(View.INVISIBLE);
                    if(referralCode!=null){
                        Response.Listener<String> responseListener = response -> {
                            try{
                                JSONObject jsonObject = new JSONObject(response);
                                if(jsonObject != null){
                                    if(jsonObject.getString("referralCode") != null && jsonObject.getBoolean("active") &&  jobFee > jsonObject.getInt("minTotalFee")){
                                        totalFee = jobFee + jsonObject.getInt("extraFee");
                                        tvTotalFee.setText(Double.toString(totalFee));
                                    }else{
                                        totalFee = jobFee;
                                        tvTotalFee.setText(Double.toString(totalFee));
                                    }
                                }
                            }catch(JSONException e){
                                referralCode = "";
                                Toast.makeText(ApplyJobActivity.this,"No Bonus!", Toast.LENGTH_SHORT).show();
                            }};
                        BonusRequest bonusRequest = new BonusRequest(referralCode, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(ApplyJobActivity.this);
                        queue.add(bonusRequest);
                    }else{
                        totalFee = jobFee;
                        tvTotalFee.setText(Double.toString(totalFee));
                    }
                }else{
                    Toast.makeText(ApplyJobActivity.this,"Method is failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Integer> jobIdList = new ArrayList<>();
                jobIdList.add(jobId);
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject != null){
                                Toast.makeText(ApplyJobActivity.this,"Success to Apply Job", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(ApplyJobActivity.this, SelesaiJobActivity.class);
                                intent.putExtra("jobseekerId", jobseekerId);
                                intent.putExtra("jobName", jobName);
                                intent.putExtra("fee", jobFee);
                                intent.putExtra("totalFee", totalFee);
                                startActivity(intent);
                            }
                        }catch(JSONException e){
                            Toast.makeText(ApplyJobActivity.this, "Fail to Apply Job", Toast.LENGTH_LONG).show();
                        }
                    }
                };
                if(selectedPayment.equals("E-Wallet")){
                    ApplyJobRequest applyJobRequest = new ApplyJobRequest(jobIdList, jobseekerId, referralCode, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(ApplyJobActivity.this);
                    queue.add(applyJobRequest);
                }else if(selectedPayment.equals("Bank")){
                    ApplyJobRequest applyJobRequest = new ApplyJobRequest(jobIdList, jobseekerId, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(ApplyJobActivity.this);
                    queue.add(applyJobRequest);
                }
                }
        });


    }
}