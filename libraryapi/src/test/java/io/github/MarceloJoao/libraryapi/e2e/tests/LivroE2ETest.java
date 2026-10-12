package io.github.MarceloJoao.libraryapi.e2e.tests;

import io.github.MarceloJoao.libraryapi.e2e.pages.LivroPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DisplayName("Testes Automatizados E2E com Selenium - Tela de Livros")
public class LivroE2ETest {

    private WebDriver driver;
    private LivroPage livroPage;

    @BeforeEach
    public void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--headless=new"); // Executa headless para agilidade em testes de build

        driver = new ChromeDriver(options);
        livroPage = new LivroPage(driver);
    }

    @Test
    @DisplayName("Deve inicializar o driver e navegar com sucesso")
    public void deveInicializarDriverComSucesso() {
        assertDoesNotThrow(() -> {
            livroPage.abrir("https://the-internet.herokuapp.com/login");
        });
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
