package glorypuncuna.jwork_android;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ApplyJobRequest extends StringRequest {
    private static final String URL_EWALLET = "http://192.168.43.5:8080/invoice/createEWalletPayment";
    private static final String URL_BANK = "http://192.168.43.5:8080/invoice/createBankPayment";
    private Map<String, String> params;

    public ApplyJobRequest(ArrayList<Integer> jobIdList, int jobseekerId,
                           String referralCode, Response.Listener<String> listener){
        super(Method.POST, URL_EWALLET, listener, null);
        params = new HashMap<>();

        StringBuilder sb = new StringBuilder();
        for (int s : jobIdList)
        {
            sb.append(s);
        }

        params.put("jobs", sb.toString());
        params.put("jobseekerId", Integer.toString(jobseekerId));
        params.put("referralCode", referralCode);
    }

    public ApplyJobRequest(ArrayList<Integer> jobIdList, int jobseekerId,
                           Response.Listener<String> listener){
        super(Method.POST, URL_BANK, listener, null);
        params = new HashMap<>();


        StringBuilder sb = new StringBuilder();
        for (int s : jobIdList)
        {
            sb.append(s);
        }
        params.put("jobs", sb.toString());
        params.put("jobseekerId", Integer.toString(jobseekerId));
        params.put("adminFee", "0");
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params;
    }
}
