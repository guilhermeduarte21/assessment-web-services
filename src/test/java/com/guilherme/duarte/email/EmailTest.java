package com.guilherme.duarte.email;

import com.guilherme.duarte.email.ServicoEmailFake.EmailCapturado;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class EmailTest {

    private ServicoEmailFake servicoEmailFake;
    private Email email;

    @Before
    public void setUp() {
        servicoEmailFake = new ServicoEmailFake();
        email = new Email(servicoEmailFake);
    }

    @Test
    public void testEnviarEmail() {
        String destinatario = "fulano@email.com";
        String mensagem = "Mensagem de teste";

        email.enviarEmail(destinatario, mensagem);

        assertEquals(1, servicoEmailFake.getQuantidadeEmails());
        assertTrue(servicoEmailFake.foiEnviadoPara(destinatario));
    }

    @Test
    public void testConteudoDaMensagem() {
        String destinatario = "fulano@email.com";
        String mensagem = "Ola Ciclano";

        email.enviarEmail(destinatario, mensagem);

        EmailCapturado emailEnviado = servicoEmailFake.getUltimoEmail();
        assertNotNull(emailEnviado);
        assertEquals(destinatario, emailEnviado.getDestinatario());
        assertEquals(mensagem, emailEnviado.getMensagem());
    }
}
