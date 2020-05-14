
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


public class TrelloTest {

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
    public void beforeSelenium() throws IOException {
        RetrofitBuilder retrofitBuilder = new RetrofitBuilder();
        Board board = new Board();

        Board createBoard = retrofitBuilder.getTrelloapi().createBoard(board, boardName).execute().body();
        seleniumBoardId = createBoard.getId();

        System.setProperty("webdriver.chrome.driver", "./src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();

        wait = new WebDriverWait(driver, 10);


    }


    @Test(priority = 1)
    public void deleteBoardSelenium() {
        driver.get(homePage);
        driver.findElement(By.xpath("//a[@class=\"btn btn-sm btn-link text-white\"]")).click();
        System.out.println(wait);
        WebElement loginInput = wait.until(ExpectedConditions.elementToBeClickable(By.name("user")));
        loginInput.sendKeys(email);
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.id("login")).click();
        WebElement closePopUp = wait.until(ExpectedConditions.elementToBeClickable(By.name("close")));
        closePopUp.click();

        driver.findElement(By.xpath("//div[@title=\"" + boardName + "\"]")).click();
        WebElement more = wait.until(ExpectedConditions.elementToBeClickable(By.className("js-open-more")));
        more.click();

        WebElement closeBoard = wait.until(ExpectedConditions.elementToBeClickable(By.className("js-close-board")));
        closeBoard.click();

        WebElement closeConfirm = wait.until(ExpectedConditions.elementToBeClickable(By.className("js-confirm")));
        closeConfirm.click();

        WebElement deleteBoard = wait.until(ExpectedConditions.elementToBeClickable(By.className("js-delete")));
        deleteBoard.click();

        WebElement deleteBoardConfirm = wait.until(ExpectedConditions.elementToBeClickable(By.className("js-confirm")));
        deleteBoardConfirm.click();

        WebElement result = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("h1")));

        driver.findElement(By.xpath("//div[@id=\"header\"]/a")).click();

        WebElement boards = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("board-tile")));
        boolean isBoardExist = !driver.findElements(By.xpath("//div[@title=\"" + boardName + "\"]")).isEmpty();


        Assert.assertEquals(isBoardExist, false);

    }

    @AfterTest
    public void afterSelenium() {
        driver.close();
    }


}
