import less5.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import java.io.IOException;


public class APITest {

    private String boardId;
    private String listId;
    private String cardId;
    private String cardName = "My Card";
    private String checkListId;
    private String seleniumBoardId;
    private WebDriver driver;
    private String homePage = "https://trello.com/";
    private String email = "kristina.masiuk@ascensusint.com";
    private String password = "Testkris123!";
    private WebDriverWait wait;
    private String boardName = "Selenium Test Board";
    private String copyBoardName = "Selenium Copy List";


    @Test(priority = 1)
    public void checkCreateBoard() throws IOException {
        RetrofitBuilder retrofitBuilder = new RetrofitBuilder();
        Board board = new Board();
        String name = "New board Kris";

        Board createBoard = retrofitBuilder.getTrelloapi().createBoard(board, name).execute().body();
        boardId = createBoard.getId();

        Assert.assertEquals(createBoard.getName(), name);
    }


    @Test(priority = 2)
    public void createList() throws IOException {
        RetrofitBuilder retrofitBuilder = new RetrofitBuilder();
        TrelloList trelloList = new TrelloList();
        String name = "Kris to do";

        TrelloList createdList = retrofitBuilder.getTrelloapi().createList(trelloList, name, boardId).execute().body();
        listId = createdList.getId();
        Assert.assertEquals(createdList.getName(), name);


    }

    @Test(priority = 3)
    public void createCard() throws IOException {
        RetrofitBuilder retrofitBuilder = new RetrofitBuilder();
        TrelloCard trelloCard = new TrelloCard();
        String name = "I can create tests";

        TrelloCard createdCard = retrofitBuilder.getTrelloapi().createCard(trelloCard, name, listId).execute().body();
        cardId = createdCard.getId();
        Assert.assertEquals(createdCard.getName(), name);
    }

    @Test(priority = 4)
    public void createChecklist() throws IOException {
        RetrofitBuilder retrofitBuilder = new RetrofitBuilder();
        CheckList checkList = new CheckList();
        String name = "Kris CheckList";

        CheckList createChecklist = retrofitBuilder.getTrelloapi().createChecklist(checkList, name, cardId).execute().body();
        checkListId = createChecklist.getId();

        Assert.assertEquals(createChecklist.getName(), name);


    }

    @AfterTest
    public void apiBoard() throws IOException {


        RetrofitBuilder retrofitBuilder = new RetrofitBuilder();
        Board board = new Board();


        retrofitBuilder.getTrelloapi().deleteBoard(boardId, board.getKey(), board.getToken()).execute();

    }
}