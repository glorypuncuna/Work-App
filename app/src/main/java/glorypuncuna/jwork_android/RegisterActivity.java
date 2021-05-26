package glorypuncuna.jwork_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        EditText edtNameR = findViewById(R.id.edt_name_r);
        EditText edtEmailR = findViewById(R.id.edt_email_r);
        EditText edtPasswordR = findViewById(R.id.edt_password_r);
        Button btnRegisterR = findViewById(R.id.btn_register_r);

        btnRegisterR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameR = edtNameR.getText().toString();
                String emailR = edtEmailR.getText().toString();
                String passwordR = edtPasswordR.getText().toString();
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject != null){
                                Toast.makeText(RegisterActivity.this, "Register Successful", Toast.LENGTH_LONG).show();
                            }
                        }catch (JSONException e){
                            Toast.makeText(RegisterActivity.this, "Register Failed", Toast.LENGTH_LONG).show();
                        }
                    }
                };
                RegisterRequest registerRequest = new RegisterRequest(nameR, emailR, passwordR, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
            }
        });


    }
}