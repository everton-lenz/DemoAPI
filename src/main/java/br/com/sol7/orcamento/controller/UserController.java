package br.com.sol7.orcamento.controller;

import br.com.sol7.orcamento.model.Profile;
import br.com.sol7.orcamento.model.User;
import br.com.sol7.orcamento.service.*;
import br.com.sol7.orcamento.util.*;
import org.lindbergframework.spring.scope.AccessScoped;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.*;

@Controller
@AccessScoped
public class UserController extends BaseController<User, Integer> implements Serializable{

    @Autowired
    private UserService userService;

    @Autowired
    private ConfigService configService;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private EmailUtil emailUtil;

    @Autowired
    private SignInSystemService signInSystemService;

    private List<User> userList = new ArrayList<User>();

    private List<User> filteredUser;

    private Boolean userBlock;

    private Boolean sendNotification = false;

    private Map<User, String> lastLogin = new HashMap<>();

    public UserController() {
        super(User.class);
    }

    @Override
    public void setEntity(User entity) {
        filteredUser= null;
        super.setEntity(entity);
    }

    @Override
    public String save() {
        try{
            if(ObjectUtil.nullOrEmpty(getEntity().getId())){
                if(!ObjectUtil.nullOrEmpty(userService.getUserRepository().findByEmail(getEntity().getEmail()))){
                    getMessageUtil().sendSimpleMessage("Já existe um usuário com o endereço de email informado.", true);
                    return null;
                }
                if (ObjectUtil.nullOrEmpty(getEntity().getId()) && sendNotification){
                    sendEmailNotification(getEntity());
                }
                String pwd = PwUtil.encryptPassword(getEntity().getPassword());
                getEntity().setPassword(pwd);
            }
            userService.save(getEntity());
            getMessageUtil().sendMessageToUser(MessageUtil.MessageUtilType.INFO, "global.info", "global.insert.sucess", null, "Usuário");
            init();
            return getListPath();
        }catch (Exception e){
            getMessageUtil().sendMessageToUser(MessageUtil.MessageUtilType.ERROR, "global.error", "global.insert.error", null, "Usuário");
            throw new RuntimeException();
        }
    }

    public void changePassword(){
        try{
            UUID uuid = UUID.randomUUID();
            String pwd = uuid.toString().substring(0,6);
            String pwdEncrypt = PwUtil.encryptPassword(pwd);
            getEntity().setPassword(pwdEncrypt);
            userService.save(getEntity());
            emailUtil.sendEmailWithLayout(getEntity().getEmail(), "Alteração de Senha", "Houve uma solicitação para alterar a sua senha na aplicação de orçamento, sua nova senha agora é: " + pwd);
            getMessageUtil().sendSimpleMessage("Foi enviado um email com a nova senha", false);
        }catch (Exception e){
            getMessageUtil().sendSimpleMessage("Ocorreu um erro para redefinir a senha",true);
        }
    }

    public List<Profile> listAllProfile(){
        return profileService.findAll();
    }

    public String prepareNewEntity(){
        setEntity(new User());
        return getFormPath();
    }

    @PostConstruct
    public void init(){
        lastLogin = new HashMap<>();
        userList = userService.findAll();
        sendNotification = false;
    }

    public void refreshView(){
        init();
        filteredUser = null;
    }

    public void sendEmailNotification(User user){
        setEntity(user);
        try{
            emailUtil.sendEmail(user.getEmail(),"Convite", "mensagem");
            getMessageUtil().sendSimpleMessage("Convite enviado com sucesso!", false);
        }catch (Exception e){
            getMessageUtil().sendSimpleMessage("Erro ao enviar convite!", true);
        }
    }

    @Override
    public void reset() throws Exception {
        super.reset();
    }

    @Override
    public String getFormPath(){
        return "/view/user/formUser.xhtml?faces-redirect=true";
    }

    @Override
    public String getListPath() {
        return "/view/user/listUser.xhtml?faces-redirect=true";
    }

    @Override
    public BaseService<User, Integer> getBaseService() {
        return userService;
    }

    @Override
    public void delete() {
        super.delete();
        init();
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public List<User> getFilteredUser() {
        return filteredUser;
    }

    public void setFilteredUser(List<User> filteredUser) {
        this.filteredUser = filteredUser;
    }

    public String getLastLogin(User user) {
        if(ObjectUtil.nullOrEmpty(lastLogin.get(user))){
            if(!user.isState()){
                lastLogin.put(user, "Bloqueado.");
            }else{
                Date date = signInSystemService.getLastLogin(user);
                if(!ObjectUtil.nullOrEmpty(date)){
                    lastLogin.put(user, DateUtil.getDateAsFormattedString(date)+" "+DateUtil.transformarDateEmHora(date));
                }else{
                    lastLogin.put(user, "Sem acesso.");
                }
            }
        }
        return lastLogin.get(user);
    }

    public String getIconUser(User user) {
        if(getLastLogin(user).equals("Bloqueado.")){
            return "fa fa-ban color-red";
        }else if(getLastLogin(user).equals("Sem acesso.")){
            return "fa fa-circle-o color-yellow";
        }else{
            return "fa fa-check-circle-o color-green";
        }
    }

    public Boolean getUserBlock() {
        return !getEntity().isState();
    }

    public void setUserBlock(Boolean userBlock) {
        this.userBlock = userBlock;
        getEntity().setState(!userBlock);
    }

    public Boolean getSendNotification() {
        return sendNotification;
    }

    public void setSendNotification(Boolean sendNotification) {
        this.sendNotification = sendNotification;
    }
}
