package io.github.MarceloJoao.libraryapi.repository;


import io.github.MarceloJoao.libraryapi.service.TransacaoService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

@SpringBootTest
public class TransacoesTest {

    @Autowired
    AutorRepository autorRepository;
    @Autowired
    private TransacaoService transacaoService;

    /**
     * Commit -> confirmar as alterações
     * Rollback -> desfazer as alterações
     */
    @Test
    @Transactional
    @Commit
    void transacaoTest(){
       transacaoService.executar();
    }

    @Test
    void transacaoTest2(){
        transacaoService.atualizacaoSemAtualizar();
    }
}
