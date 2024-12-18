package api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface TicketaccssAppService {

    @FormUrlEncoded
    @POST("api/login")
    Call<ResponseBody> login(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("api/password/reset-link")
    Call<ResponseBody> resetpassword(@Field("email") String email);

}
