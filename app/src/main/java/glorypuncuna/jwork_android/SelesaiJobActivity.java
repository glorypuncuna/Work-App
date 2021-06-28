package glorypuncuna.jwork_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class SelesaiJobActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selesai_job);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        ConstraintLayout clSelesai = findViewById(R.id.cl_selesai);
        clSelesai.setVisibility(View.INVISIBLE);
        
        fetchJob();
    }

    private void fetchJob() {
        Response.Listener<String> responseListener = response -> {
            try{
                JSONObject jsonObject = new JSONObject(response);
                if(jsonObject != null){

                }
            }catch (JSONException e){
            }
        };
        MenuRequest menuRequest = new MenuRequest(responseListener);
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(menuRequest);
    }
}