package api;

import com.example.cineapp.Event;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface TicketaccssAppService {

    @FormUrlEncoded
    @POST("api/login")
    Call<ResponseBody> login(@Field("email") String email, @Field("password") String password);

    @GET("api/events")
    Call<List<Event>> getEvents(@Header("Authorization") String token);

}
