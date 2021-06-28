package glorypuncuna.jwork_android;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class JobSelesaiRequest extends StringRequest {
    private static final String URL = "http://192.168.43.5:8080/invoice/invoiceStatus/";
    private Map<String, String> params;

    public JobSelesaiRequest(int id, Response.Listener<String> listener){
        super(Method.PUT, URL + id, listener, null);
        params = new HashMap<>();
        params.put("invoiceStatus", "Finished");
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params;
    }
}