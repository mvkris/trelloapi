package less5;

import retrofit2.Call;
import retrofit2.http.*;

public interface Trelloapi {

    @POST("/1/boards/")
    Call<Board> createBoard(@Body Board board, @Query("name") String name);



    @POST("/1/lists/")
    Call<TrelloList> createList(@Body TrelloList trelloList, @Query("name") String name, @Query("idBoard") String idBoard);

    @POST("/1/cards/")
    Call<TrelloCard> createCard(@Body TrelloCard trelloCard, @Query("name") String name, @Query("idList") String idList);

    @POST("/1/checklists/")
    Call<CheckList> createChecklist(@Body CheckList checklist, @Query("name") String name,  @Query("idCard") String idCard );



    @DELETE ("/1/boards/{id}/")
    Call<TrelloCard> deleteBoard(@Path("id") String id, @Query("key") String key, @Query("token") String token);



}
