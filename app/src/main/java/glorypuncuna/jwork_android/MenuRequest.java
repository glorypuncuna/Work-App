package glorypuncuna.jwork_android;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class MenuRequest extends StringRequest {

    private static final String URL = "http://192.168.43.5:8080/job";

    public MenuRequest(Response.Listener<String> listener){
        super(Method.GET, URL, listener, null);

    }

}