package glorypuncuna.jwork_android;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

public class JobFetchRequest extends StringRequest {
    private static final String URL = "http://192.168.43.5:8080/invoice/Jobseeker/";

    public JobFetchRequest(int jobseekerId, Response.Listener<String> listener){
        super(Request.Method.GET, URL + jobseekerId, listener, null);
    }
}
