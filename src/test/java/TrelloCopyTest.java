
import less5.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.annotation.ElementType;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


public class TrelloCopyTest {

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



    @BeforeTest

    public void beforeSeleniumCopyList() throws IOException {
        RetrofitBuilder retrofitBuilder = new RetrofitBuilder();
        Board board = new Board();

        Board createBoard = retrofitBuilder.getTrelloapi().createBoard(board, copyBoardName).execute().body();
        seleniumBoardId = createBoard.getId();

        TrelloList trelloList = new TrelloList();
        String name = "Kris to do";

        TrelloList createdList = retrofitBuilder.getTrelloapi().createList(trelloList, name, seleniumBoardId).execute().body();
        listId = createdList.getId();

        TrelloCard trelloCard = new TrelloCard();
        String cardName = "I can create tests";

        retrofitBuilder.getTrelloapi().createCard(trelloCard, cardName, listId).execute().body();


        TrelloCard trelloCard2 = new TrelloCard();
        String cardName2 = "I can create a lot of tests";

        retrofitBuilder.getTrelloapi().createCard(trelloCard, cardName2, listId).execute().body();



        System.setProperty("webdriver.chrome.driver", "./src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();

        wait = new WebDriverWait(driver, 10);



    }




    @Test(priority = 2)
    public void copyList () {
        driver.get(homePage);
        driver.findElement(By.xpath("//a[@class=\"btn btn-sm btn-link text-white\"]")).click();
        System.out.println(wait);
        WebElement loginInput = wait.until(ExpectedConditions.elementToBeClickable(By.name("user")));
        loginInput.sendKeys(email);
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.id("login")).click();
        WebElement closePopUp = wait.until(ExpectedConditions.elementToBeClickable(By.name("close")));
        closePopUp.click();

        driver.findElement(By.xpath("//div[@title=\""+copyBoardName+"\"]")).click();

        WebElement exstras = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h2[text()=\"Kris to do\"]//following-sibling::div")));
        exstras.click();

        WebElement copy = wait.until(ExpectedConditions.elementToBeClickable(By.className("js-copy-list")));
        copy.click();

        driver.findElement(By.className("js-autofocus")).sendKeys("New Copy List");
        driver.findElement(By.className("js-submit")).click();

        boolean isCopyListExist = !driver.findElements(By.xpath("//h2[text()=\"New Copy List\"]")).isEmpty();
        Assert.assertEquals(isCopyListExist, true);



    }



    @AfterTest
    public void afterSeleniumCopyList() throws IOException {

        driver.close();
        RetrofitBuilder retrofitBuilder = new RetrofitBuilder();
        Board board = new Board();


        retrofitBuilder.getTrelloapi().deleteBoard(seleniumBoardId, board.getKey(), board.getToken()).execute().code();

    }

}

