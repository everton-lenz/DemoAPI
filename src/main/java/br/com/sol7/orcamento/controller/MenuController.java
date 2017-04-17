package br.com.sol7.orcamento.controller;


import br.com.sol7.orcamento.auth.LoggedUser;
import br.com.sol7.orcamento.model.*;
import br.com.sol7.orcamento.service.ConfigService;
import br.com.sol7.orcamento.util.JSFUtil;
import br.com.sol7.orcamento.util.ObjectUtil;
import org.lindbergframework.spring.scope.AccessScoped;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import java.util.*;

@Controller
@AccessScoped
public class MenuController {

    @Autowired
    private ConfigService configService;

    private String urlApp = null;

    private Layout layout;

    private List<Module> modules = new ArrayList<Module>();

    private Map<Module, List<Module>> mapMenus = new HashMap<>();

    public MenuController() {
    }

    @PostConstruct
    public void init(){
        if(ObjectUtil.nullOrEmpty(getUser())){
            listModules();
        }
    }

    public void listModules(){
        try {
            mapMenus = new HashMap<>();
            Set<Module> menus = new LinkedHashSet<>();
            for(Module m: getUser().getProfile().getModules()){
                if(!ObjectUtil.nullOrEmpty(m.getModuleParent())){
                    List<Module> submenus = mapMenus.get(m.getModuleParent());
                    if(ObjectUtil.nullOrEmpty(submenus)){
                        submenus = new ArrayList<Module>();
                        submenus.add(m);
                        mapMenus.put(m.getModuleParent(), submenus);
                    }else{
                        submenus.add(m);
                        mapMenus.put(m.getModuleParent(), submenus);
                    }
                    menus.add(m.getModuleParent());
                }
            }
            modules = new ArrayList<>(menus);
            Collections.sort(modules);
        }catch (Exception e){
            modules = new ArrayList<Module>();
        }
    }

    //Listar todos os módulos que o usuário possui acesso
    public List<Module> listSubMenusPermission(Module m){
        return mapMenus.get(m);
    }

    public User getUser() {
        try {
            return getUsuarioLogado().getUser();
        }catch (Exception e){
            return new User();
        }
    }

    private LoggedUser getUsuarioLogado() {
        return JSFUtil.getUsuarioLogado();
    }

    public String loginBimachine() {
        if(keyIsValid()){
            return "http://"+getUrlApp()+"/?appToken="+getKey();
        }else{
            return "http://"+getUrlApp()+"/";
        }
    }

    public String getUrlApp(){
        if(ObjectUtil.nullOrEmpty(urlApp)){
            urlApp = configService.searchForKey("URL_LOGIN_APPLICATION").getValue();
        }
        return urlApp;
    }

    public String getKey(){
        return getUsuarioLogado().getToken();
    }

    public String analysis(){
        return "http:"+getUrlApp()+"/publisher/analysis.spr?content=%2Forcamento-apresentacao%2FDRE%2FDREMatrizModeloGerencial.analysis&chart=false&applyFrame=true&frameBorderColor=%23CCCCCC&borderType=SIMPLE&showUpdateDate=true&showTitle=true&appToken="+getKey();
    }


    public boolean keyIsValid(){
        return !ObjectUtil.nullOrEmpty(getUsuarioLogado().getToken());
    }


    public String getLinkWithToken(String link, String token){
        try {
            //int index = link.indexOf("></iframe>");
            //String begin = link.substring(0, index - 1);
            //String end = link.substring(index, link.length());
            //String complete = begin + "&appToken=" + token + end;
            return link+"&appToken=" + token;
        }catch (Exception e){
            return null;
        }
    }

    public String color(){
        if(ObjectUtil.nullOrEmpty(layout)){
            return "#3e464c";
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


    public String logoff() {
        try {
            JSFUtil.setUsuarioLogado(null);
            JSFUtil.invalidateSession();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return "/view/login/login?faces-redirect=true";
    }

    public String myAccount(){
        return "/view/changePassword/changePassword.xhtml?faces-redirect=true";
    }

    public List<Module> getModules() {
        if(!ObjectUtil.nullOrEmpty(getUser()) && ObjectUtil.nullOrEmpty(modules)){
            listModules();
        }
        if(getUser().getProfile().getModules().size() < 10){
            RequestContext.getCurrentInstance().execute("$(function(){$('.checkbox-menu').click();});");
        }
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }
}
