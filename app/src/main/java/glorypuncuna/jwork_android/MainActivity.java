package glorypuncuna.jwork_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    MainListAdapter listAdapter;
    ExpandableListView expListView;
    private ArrayList<Recruiter> listRecruiter = new ArrayList<>();
    private ArrayList<Job> jobIdList = new ArrayList<>();
    private HashMap<Recruiter, ArrayList<Job>> childMapping = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        // preparing list data
        refreshList();

        listAdapter = new MainListAdapter(this, listRecruiter, childMapping);

        expListView.setAdapter(listAdapter);
    }

    protected void refreshList(){
        Response.Listener<String> responseListener = new Response.Listener<String>(){
            @Override
            public void onResponse(String response){
                try{
                    JSONArray jsonResponse = new JSONArray(response);
                    if(jsonResponse != null){
                        for(int i = 0; i<jsonResponse.length(); i++){
                            JSONObject job = jsonResponse.getJSONObject(i);
                            JSONObject recruiter = job.getJSONObject("recruiter");
                            JSONObject location = recruiter.getJSONObject("location");

                            Location l = new Location(location.getJSONObject("province").toString(), location.getJSONObject("city").toString(), location.getJSONObject("description").toString());
                            Recruiter r = new Recruiter(Integer.valueOf(recruiter.getJSONObject("id").toString()), recruiter.getJSONObject("name").toString(), recruiter.getJSONObject("email").toString(), recruiter.getJSONObject("phoneNumber").toString(), l);
                            listRecruiter.add(r);
                            Job j = new Job(Integer.valueOf(job.getJSONObject("id").toString()), job.getJSONObject("name").toString(), r, Integer.valueOf(job.getJSONObject("fee").toString()), job.getJSONObject("category").toString());
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
                } catch (JSONException e){
                    Toast.makeText(MainActivity.this, "Load Menu Failed", Toast.LENGTH_LONG).show();
                }

            }
        };
        MenuRequest MenuRequest = new MenuRequest(responseListener);
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(MenuRequest);
    }


}