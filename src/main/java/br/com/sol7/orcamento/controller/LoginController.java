package br.com.sol7.orcamento.controller;


import br.com.sol7.orcamento.auth.LoggedUser;
import br.com.sol7.orcamento.auth.LoggedUserImpl;
import br.com.sol7.orcamento.exceptions.ConnectionException;
import br.com.sol7.orcamento.exceptions.LoginInvalidoException;
import br.com.sol7.orcamento.exceptions.UserDoesNotExistException;
import br.com.sol7.orcamento.model.Layout;
import br.com.sol7.orcamento.model.SignInSystem;
import br.com.sol7.orcamento.model.User;
import br.com.sol7.orcamento.service.ConfigService;
import br.com.sol7.orcamento.service.LayoutService;
import br.com.sol7.orcamento.service.SignInSystemService;
import br.com.sol7.orcamento.service.UserService;
import br.com.sol7.orcamento.spring.SpringApplicationContextSupport;
import br.com.sol7.orcamento.util.JSFUtil;
import br.com.sol7.orcamento.util.MessageUtil;
import br.com.sol7.orcamento.util.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Controller
@Scope("request")
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private SignInSystemService signInSystemService;

    @Autowired
    private ConfigService configService;

    @Autowired
    private LayoutService layoutService;

    private String login;

    private String password;

    private MessageUtil messageUtil;

    private User user;

    private Layout layout;

    public LoginController() {

    }

    @PostConstruct
    public void init(){
        layout = layoutService.getLayout();
    }

    public String logar() {
        messageUtil = SpringApplicationContextSupport.getInstance().getBean(MessageUtil.class);
        try {
            user = userService.login(login, password);
            if (!ObjectUtil.nullOrEmpty(user) && !user.isState()){
                messageUtil.sendMessageToUser(MessageUtil.MessageUtilType.ERROR, "label.empty", "state.invalido", null);
                return null;
            }
        }catch (LoginInvalidoException e) {
            messageUtil.sendMessageToUser(MessageUtil.MessageUtilType.ERROR, "label.empty", "login.invalido", null);
            return null;
        } catch (ConnectionException connection) {
            messageUtil.sendMessageToUser(MessageUtil.MessageUtilType.ERROR, "label.empty", "connection.failed", null);
            return null;
        } catch (UserDoesNotExistException notExist) {
            messageUtil.sendMessageToUser(MessageUtil.MessageUtilType.ERROR, "label.empty", "label.empty", notExist.getMessage());
            return null;
        }
        LoggedUser loggedUser = new LoggedUserImpl(user, null, null, user.getProfile().getModules());
        JSFUtil.setUsuarioLogado(loggedUser);
        //Registrar entrada de usuário
        signInSystemService.save(new SignInSystem(user));
        if (!user.isChangedPassword()){
            return "/view/changePassword/changePassword.xhtml?faces-redirect=true";
        }

        return "/default.xhtml?faces-redirect=true";
    }

    public static <T> HttpEntity<T> jsonEntity(T entity) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return new HttpEntity<T>(entity, headers);
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getUser() {
        try {
            return JSFUtil.getUsuarioLogado().getUser();
        }catch (Exception e){
            return new User();
        }
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String logo(){
        if(ObjectUtil.nullOrEmpty(layout)){
            return "/resources/images/logo.png";
        }else{
            return "/app-resources/layout/"+layout.getLogo();
        }
    }

    public String background(){
        if(ObjectUtil.nullOrEmpty(layout)){
            return "url(\"/resources/images/login-background-image.jpg\")";
        }else{
            return "url(\"/app-resources/layout/"+layout.getBackground()+"\")";
        }
    }

    public String icon(){
        if(ObjectUtil.nullOrEmpty(layout)){
            return "/resources/images/iconsol7.ico";
        }else{
            return "/app-resources/layout/"+layout.getIcon();
        }
    }

    public String positionLogin(){
        if(!ObjectUtil.nullOrEmpty(layout)){
            switch (layout.getLoginPosition()){
                case 'c':
                    return "loginBoxCenter";
                case 'r':
                    return "loginBoxRight";
                case 'l':
                    return "loginBoxLeft";
                default:
                    return "loginBoxCenter";
            }
        }else{
            return "loginBoxCenter";
        }
    }

    public String color(){
        if(ObjectUtil.nullOrEmpty(layout)){
            return "#0777AC";
        }else{
            return "#"+layout.getColor();
        }
    }


    public Layout getLayout() {
        return layout;
    }

    public void setLayout(Layout layout) {
        this.layout = layout;
    }
}
