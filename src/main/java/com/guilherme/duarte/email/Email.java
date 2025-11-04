package com.guilherme.duarte.email;

public class Email {

    private IServicoEmail servicoEmail;

    public Email(IServicoEmail servicoEmail){
        this.servicoEmail = servicoEmail;
    }

    public void enviarEmail(String destinatario, String mensagem) {
        servicoEmail.enviar(destinatario, mensagem);
    }
}
