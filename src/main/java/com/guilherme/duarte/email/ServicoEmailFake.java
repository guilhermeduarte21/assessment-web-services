package com.guilherme.duarte.email;

import java.util.ArrayList;
import java.util.List;

public class ServicoEmailFake implements IServicoEmail {

    private List<EmailCapturado> emailsEnviados;

    public ServicoEmailFake() {
        this.emailsEnviados = new ArrayList<>();
    }

    @Override
    public void enviar(String destinatario, String mensagem) {
        emailsEnviados.add(new EmailCapturado(destinatario, mensagem));
    }

    public int getQuantidadeEmails() {
        return emailsEnviados.size();
    }

    public boolean foiEnviadoPara(String destinatario) {
        for (EmailCapturado email : emailsEnviados) {
            if (email.getDestinatario().equals(destinatario)) {
                return true;
            }
        }
        return false;
    }

    public EmailCapturado getUltimoEmail() {
        if (emailsEnviados.isEmpty()) {
            return null;
        }
        return emailsEnviados.get(emailsEnviados.size() - 1);
    }

    public static class EmailCapturado {
        private String destinatario;
        private String mensagem;

        public EmailCapturado(String destinatario, String mensagem) {
            this.destinatario = destinatario;
            this.mensagem = mensagem;
        }

        public String getDestinatario() {
            return destinatario;
        }

        public String getMensagem() {
            return mensagem;
        }
    }
}
