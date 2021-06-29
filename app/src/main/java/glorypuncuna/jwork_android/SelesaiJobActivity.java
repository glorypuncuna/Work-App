package glorypuncuna.jwork_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SelesaiJobActivity extends AppCompatActivity {

    private ConstraintLayout clSelesai;
    int jobseekerId;
    double  fee, totalFee;
    String jobName;

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
        fee = getIntent().getDoubleExtra("fee", 0);
        totalFee = getIntent().getDoubleExtra("totalFee", 0);
        jobseekerId = getIntent().getIntExtra("jobseekerId", 0);
        fetchJob();
    }

    public void fetchJob(){
        Response.Listener<String> responseListener = response -> {

            Toast.makeText(SelesaiJobActivity.this, Integer.toString(jobseekerId), Toast.LENGTH_LONG).show();
            try{
                clSelesai = findViewById(R.id.cl_selesai);
                clSelesai.setVisibility(View.VISIBLE);
                JSONArray jsonResponse = new JSONArray(response);
                if(jsonResponse != null){
                    for(int i = 0; i<jsonResponse.length(); i++){

                        Toast.makeText(SelesaiJobActivity.this, jobName, Toast.LENGTH_LONG).show();
                        Toast.makeText(SelesaiJobActivity.this, Double.toString(fee), Toast.LENGTH_LONG).show();
                        TextView tvJobseekerName = findViewById(R.id.jobseeker_name);
                        TextView tvInvoiceDate = findViewById(R.id.invoice_date);
                        TextView tvPaymentType = findViewById(R.id.payment_type);
                        TextView tvInvoiceStatus = findViewById(R.id.invoice_status);
                        TextView tvReferralCode = findViewById(R.id.referral_code1);
                        TextView tvJobName = findViewById(R.id.job_name1);
                        TextView tvFee = findViewById(R.id.fee);
                        TextView tvTotalFee = findViewById(R.id.total_fee1);
                        tvJobName.setText(jobName);
                        tvFee.setText(Double.toString(fee));
                        tvTotalFee.setText(Double.toString(totalFee));

                        Toast.makeText(SelesaiJobActivity.this, jobName, Toast.LENGTH_LONG).show();
                        Toast.makeText(SelesaiJobActivity.this, Double.toString(fee), Toast.LENGTH_LONG).show();

                        JSONObject invoice = jsonResponse.getJSONObject(i);
                        JSONObject jobseeker = invoice.getJSONObject("jobseeker");
                        //JSONObject bonus = invoice.getJSONObject("bonus");
                        tvJobseekerName.setText(jobseeker.getString("name"));
                        tvInvoiceDate.setText(invoice.getString("date"));
                        tvPaymentType.setText(invoice.getString("paymentType"));
                        tvInvoiceStatus.setText(invoice.getString("invoiceStatus"));
                        //if(!bonus.getString("referralCode").isEmpty()){
                        //tvReferralCode.setText(bonus.getString("referralCode"));}
                        //else{
                        //    tvReferralCode.setText("No Bonus");
                        //}

                    }
                }
            } catch (JSONException e){
                Toast.makeText(SelesaiJobActivity.this, "Load Invoice Failed", Toast.LENGTH_LONG).show();
            }
        };
        JobFetchRequest jobFetchRequest = new JobFetchRequest(jobseekerId, responseListener);
        RequestQueue queue = Volley.newRequestQueue(SelesaiJobActivity.this);
        queue.add(jobFetchRequest);
    }
}