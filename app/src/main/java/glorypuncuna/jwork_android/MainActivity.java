package glorypuncuna.jwork_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    protected ArrayList<Job> jobIdList = new ArrayList<>();
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    int jobseekerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        jobseekerId = getIntent().getIntExtra("jobseekerId", 0);
        refreshList();
    }

    public void refreshList(){
        HashMap<Recruiter, ArrayList<Job>> childMapping = new HashMap<>();
        ArrayList<Recruiter> listRecruiter = new ArrayList<>();
        Response.Listener<String> responseListener = response -> {
            try{
                JSONArray jsonResponse = new JSONArray(response);
                if(jsonResponse != null){
                    for(int i = 0; i<jsonResponse.length(); i++){
                        Gson gson = new Gson();
                        JSONObject job = jsonResponse.getJSONObject(i);
                        JSONObject recruiter = job.getJSONObject("recruiter");
                        JSONObject location = recruiter.getJSONObject("location");

                        Recruiter r = new Recruiter(recruiter.getInt("id"),
                                recruiter.getString("name"),
                                recruiter.getString("email"),
                                recruiter.getString("phoneNumber"),
                                gson.fromJson(location.toString(), Location.class));

                        boolean recDoub = false;
                        for(Recruiter rec: listRecruiter){
                            if(recruiter.getInt("id") == rec.getId()){
                                recDoub = true;
                                break;
                            }
                        }
                        if(recDoub == false){
                            listRecruiter.add(r);
                        }

                        Job j = new Job(job.getInt("id"),
                                job.getString("name"),
                                gson.fromJson(recruiter.toString(), Recruiter.class),
                                job.getInt("fee"),
                                job.getString("category"));

                        jobIdList.add(j);
                    }
                    for(Recruiter rec: listRecruiter){
                        ArrayList<Job> temp = new ArrayList<>();
                        for(Job job2 : jobIdList){
                            if(job2.getRecruiter().getName().equals(rec.getName()) ||
                                    job2.getRecruiter().getEmail().equals(rec.getEmail()) ||
                                    job2.getRecruiter().getPhoneNumber().equals(rec.getPhoneNumber())){
                                temp.add(job2);
                            }
                        }
                        childMapping.put(rec,temp);
                    }
                }
                adapter(listRecruiter, childMapping);
            } catch (JSONException e){
                Toast.makeText(MainActivity.this, "Load Menu Failed", Toast.LENGTH_LONG).show();
            }
        };
        MenuRequest menuRequest = new MenuRequest(responseListener);
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(menuRequest);
    }

    private void adapter(ArrayList<Recruiter> listRecruiter, HashMap<Recruiter, ArrayList<Job>> childMapping){
        expListView = (ExpandableListView)  findViewById(R.id.lvExp);
        listAdapter = new MainListAdapter(this, listRecruiter, childMapping);
        expListView.setAdapter(listAdapter);

        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Job selectedJob = childMapping.get(listRecruiter.get(groupPosition)).get(childPosition);

                Intent intentToApply = new Intent(MainActivity.this, ApplyJobActivity.class);
                intentToApply.putExtra("jobId", selectedJob.getId());
                intentToApply.putExtra("jobName", selectedJob.getName());
                intentToApply.putExtra("jobCategory", selectedJob.getCategory());
                intentToApply.putExtra("jobFee", (double) selectedJob.getFee());
                intentToApply.putExtra("jobseekerId", jobseekerId);
                startActivity(intentToApply);

                return false;
            }
        });
    }
}