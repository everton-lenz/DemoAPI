package br.com.sol7.orcamento.controller;

import br.com.sol7.orcamento.model.User;
import br.com.sol7.orcamento.service.UserService;
import br.com.sol7.orcamento.util.JSFUtil;
import br.com.sol7.orcamento.util.MessageUtil;
import br.com.sol7.orcamento.util.ObjectUtil;
import br.com.sol7.orcamento.util.PwUtil;
import org.lindbergframework.spring.scope.AccessScoped;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

@Component
@AccessScoped
public class ChangePasswordController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private UserService userService;

	@Autowired
	private MessageUtil messageUtil;

	private String password;

    private String confirmPassword;

    private String oldPassword;

    public String getFormPath(){
        return "/view/changePassword/changePassword.xhtml";
    }

	public void saveOrUpdate() {
        try{
            if(!password.equals(confirmPassword)){
                messageUtil.sendSimpleMessage("As senhas devem ser iguais", true);
                return;
            }
            User user = JSFUtil.getUsuarioLogado().getUser();

            if (ObjectUtil.nullOrEmpty(password)) {
                messageUtil.sendSimpleMessage("Digite uma senha",true);
                return;
            }

            if(!password.matches("(?=.{6}).*([A-Za-z][0-9]|[0-9][A-Za-z]).*")){
                messageUtil.sendSimpleMessage("A senha deve possuir 6 caracteres contendo letras e números.", true);
                return;
            }

            if (!ObjectUtil.nullOrEmpty(password)) {
                String pwd = null;
                try {
                    pwd = PwUtil.encryptPassword(password);
                } catch (UnsupportedEncodingException e) {
                    throw new IllegalStateException("Problemas com encoding!");
                } catch (NoSuchAlgorithmException e) {
                    throw new IllegalStateException(
                            "Problema com algoritmo de criptografia!");
                }

                user.setPassword(pwd);
                user.setChangedPassword(true);
                userService.save(user);
                password = new String();
                oldPassword = new String();
                confirmPassword = new String();
                getFormPath();
            }
            messageUtil.sendSimpleMessage("Senha alterada com sucesso", false);
        }catch (Exception e){
            messageUtil.sendSimpleMessage("Erro ao alterar senha.", true);
        }
	}

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
}
