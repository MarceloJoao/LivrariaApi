package io.github.MarceloJoao.libraryapi.e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Padrão Page Object Model para a tela de Livros da interface web (Livros.html).
 */
public class LivroPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Locators mapeados do formulário HTML de Livros.html
    private final By campoTitulo    = By.id("titulo");
    private final By campoIsbn      = By.id("isbn");
    private final By campoDataPub   = By.id("dataPublicacao");
    private final By selectGenero   = By.id("genero");
    private final By campoPreco     = By.id("preco");
    private final By selectAutor    = By.id("autorId");
    private final By botaoSalvar    = By.cssSelector("button[type='submit']");
    private final By divMensagem    = By.id("mensagem");

    public LivroPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void abrir(String url) {
        driver.get(url);
        wait.until(ExpectedConditions.presenceOfElementLocated(campoTitulo));
    }

    public void preencherTitulo(String titulo) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(campoTitulo));
        el.clear();
        el.sendKeys(titulo);
    }

    public void preencherIsbn(String isbn) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(campoIsbn));
        el.clear();
        el.sendKeys(isbn);
    }

    public void preencherDataPublicacao(String dataIso) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(campoDataPub));
        el.clear();
        el.sendKeys(dataIso);
    }

    public void selecionarGenero(String valorGenero) {
        Select dropdown = new Select(driver.findElement(selectGenero));
        dropdown.selectByValue(valorGenero);
    }

    public void preencherPreco(String preco) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(campoPreco));
        el.clear();
        el.sendKeys(preco);
    }

    public void selecionarPrimeiroAutorSeDisponivel() {
        try {
            Select dropdown = new Select(driver.findElement(selectAutor));
            if (dropdown.getOptions().size() > 1) {
                dropdown.selectByIndex(1);
            }
        } catch (Exception ignored) {
            // Caso a API ainda não esteja rodando com autores pré-cadastrados
        }
    }

    public void clicarSalvar() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(botaoSalvar));
        btn.click();
    }

    public String obterMensagemRetorno() {
        WebElement msg = wait.until(ExpectedConditions.visibilityOfElementLocated(divMensagem));
        return msg.getText();
    }
}
