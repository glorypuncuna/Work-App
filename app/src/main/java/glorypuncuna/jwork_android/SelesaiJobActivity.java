package glorypuncuna.jwork_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SelesaiJobActivity extends AppCompatActivity {

    private ConstraintLayout clSelesai;
    private int jobseekerId, invoiceId;
    private double  fee, totalFee;
    private String jobName, referralCode, dateS;
    private TextView tvInvoiceStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selesai_job);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        clSelesai = findViewById(R.id.cl_selesai);
        clSelesai.setVisibility(View.INVISIBLE);
        jobName = getIntent().getStringExtra("jobName");
        referralCode = getIntent().getStringExtra("referralCode");
        fee = getIntent().getDoubleExtra("fee", 0);
        totalFee = getIntent().getDoubleExtra("totalFee", 0);
        jobseekerId = getIntent().getIntExtra("jobseekerId", 0);
        tvInvoiceStatus = findViewById(R.id.invoice_status);

        Button btnCancel = findViewById(R.id.btnCancel);
        Button btnFinish = findViewById(R.id.btnFinish);
        Button btnMenu = findViewById(R.id.btnMenu);
        fetchJob();

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelesaiJobActivity.this, MainActivity.class);
                intent.putExtra("jobseekerId",jobseekerId);
                startActivity(intent);
            }});

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject != null){
                                Toast.makeText(SelesaiJobActivity.this, "Invoice Cancelled", Toast.LENGTH_SHORT).show();
                                tvInvoiceStatus.setText("Canceled");
                            }
                        }catch(JSONException e){
                            Toast.makeText(SelesaiJobActivity.this, "Fail to cancel the invoice", Toast.LENGTH_SHORT).show();
                        }
                    }
                };

                JobBatalRequest jobBatalRequest = new JobBatalRequest(invoiceId, responseListener);
                RequestQueue queue = Volley.newRequestQueue(SelesaiJobActivity.this);
                queue.add(jobBatalRequest);
            }
        });

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject != null){
                                Toast.makeText(SelesaiJobActivity.this, "Invoice Finished", Toast.LENGTH_SHORT).show();
                                tvInvoiceStatus.setText("Finished");
                            }
                        }catch(JSONException e){
                            Toast.makeText(SelesaiJobActivity.this, "Fail to finish the invoice", Toast.LENGTH_SHORT).show();
                        }
                    }
                };

                JobSelesaiRequest jobSelesaiRequest = new JobSelesaiRequest(invoiceId, responseListener);
                RequestQueue queue = Volley.newRequestQueue(SelesaiJobActivity.this);
                queue.add(jobSelesaiRequest);
            }
        });

    }

    public void fetchJob(){
        Response.Listener<String> responseListener = response -> {
           try{
                clSelesai = findViewById(R.id.cl_selesai);
                clSelesai.setVisibility(View.VISIBLE);
                JSONArray jsonResponse = new JSONArray(response);
                if(jsonResponse != null){
                    for(int i = 0; i<jsonResponse.length(); i++){
                        TextView tvInvoiceId = findViewById(R.id.invoice);
                        TextView tvJobseekerName = findViewById(R.id.jobseeker_name);
                        TextView tvInvoiceDate = findViewById(R.id.invoice_date);
                        TextView tvPaymentType = findViewById(R.id.payment_type);
                        TextView tvReferralCode = findViewById(R.id.referral_code1);
                        TextView tvJobName = findViewById(R.id.job_name1);
                        TextView tvFee = findViewById(R.id.fee);
                        TextView tvTotalFee = findViewById(R.id.total_fee1);
                        TextView tvStaticReferralCode = findViewById(R.id.staticReferralCode1);

                        tvJobName.setText(jobName);
                        tvFee.setText(Double.toString(fee));
                        tvTotalFee.setText(Double.toString(totalFee));

                        JSONObject invoice = jsonResponse.getJSONObject(i);
                        JSONObject jobseeker = invoice.getJSONObject("jobseeker");
                        invoiceId = invoice.getInt("id");
                        tvInvoiceId.setText("Invoice ID: " + invoiceId);
                        tvJobseekerName.setText(jobseeker.getString("name"));

                        dateS = invoice.getString("date");
                        Date date=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").parse(dateS);
                        dateS = new SimpleDateFormat("E, MMM dd yyyy").format(date);

                        tvInvoiceDate.setText(dateS);
                        tvInvoiceStatus.setText(invoice.getString("invoiceStatus"));
                        if(invoice.getString("paymentType").equals("EwalletPayment")){
                            tvStaticReferralCode.setVisibility(View.VISIBLE);
                            tvReferralCode.setVisibility(View.VISIBLE);
                            tvPaymentType.setText("E-Wallet");
                            if(referralCode.equals("")){
                                tvReferralCode.setText("No Bonus");}
                            else{
                                tvReferralCode.setText(referralCode);
                            }
                        }else if (invoice.getString("paymentType").equals("BankPayment")){
                            tvStaticReferralCode.setVisibility(View.GONE);
                            tvReferralCode.setVisibility(View.GONE);
                            tvPaymentType.setText("Bank");
                        }
                    }
                }
            } catch (JSONException e){
                Toast.makeText(SelesaiJobActivity.this, "Load Invoice Failed", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(SelesaiJobActivity.this, MainActivity.class);
                intent.putExtra("jobseekerId",jobseekerId);
                startActivity(intent);
            } catch (ParseException e) {
               Toast.makeText(SelesaiJobActivity.this, dateS, Toast.LENGTH_LONG).show();
           }
        };
        JobFetchRequest jobFetchRequest = new JobFetchRequest(jobseekerId, responseListener);
        RequestQueue queue = Volley.newRequestQueue(SelesaiJobActivity.this);
        queue.add(jobFetchRequest);
    }
}