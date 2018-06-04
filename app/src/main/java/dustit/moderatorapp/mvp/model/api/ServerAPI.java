package dustit.moderatorapp.mvp.model.api;

import dustit.moderatorapp.mvp.model.entities.CategoriesIdEntity;
import dustit.moderatorapp.mvp.model.entities.CategoryEntity;
import dustit.moderatorapp.mvp.model.entities.CreateCategoryEntity;
import dustit.moderatorapp.mvp.model.entities.LoginUserEntity;
import dustit.moderatorapp.mvp.model.entities.MemIdEntity;
import dustit.moderatorapp.mvp.model.entities.ResponseCode;
import dustit.moderatorapp.mvp.model.entities.TokenEntity;
import dustit.moderatorapp.mvp.model.entities.UserEntity;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by shevc on 15.09.2017.
 * Let's GO!
 */

public interface ServerAPI {

    String auth = "Authorization";

    @POST("/account/register/")
    Observable<TokenEntity> registerUser(@Body UserEntity userEntity);

    @POST("/account/login/")
    Observable<TokenEntity> loginUser(@Body LoginUserEntity loginUserEntity);

    @GET("/config/categories")
    Observable<CategoryEntity> getCategories(@Header(auth) String token);

    @POST("/moderator/mem")
    Observable<ResponseCode> postMem(@Header(auth) String token, @Query("id") String id, @Body CategoriesIdEntity categoriesIdEntity);

    @GET("/moderator/newMem")
    Observable<MemIdEntity> getNewMem(@Header(auth) String token);

    @POST("/moderator/discardMem")
    Observable<ResponseCode> discardMem(@Header(auth) String token, @Query("id") String id);

    @POST("/moderator/logout")
    Observable<ResponseCode> logout(@Header(auth) String token);

    @POST("/moderator/createCategory")
    Observable<ResponseCode> createCategory(@Header(auth) String token, @Body CreateCategoryEntity createCategoryEntity);

    @DELETE("moderator/category")
    Observable<ResponseCode> deleteCategory(@Header(auth) String token, @Query("id") String id);


}
