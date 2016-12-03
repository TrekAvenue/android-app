package nikhilg.dev.trekavenue.Interfaces;

/**
 * Created by nik on 4/12/16.
 */
public interface NetworkRequestCallback {
    void onSuccess(int requestType, String response);
    void onFailure(int requestType, String error);
}
