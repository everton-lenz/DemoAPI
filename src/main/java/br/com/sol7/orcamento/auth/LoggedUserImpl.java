package br.com.sol7.orcamento.auth;

import br.com.sol7.orcamento.model.Module;
import br.com.sol7.orcamento.model.User;

import java.util.List;

public final class LoggedUserImpl implements LoggedUser {

    private final User userLoggedIn;

    private final String token;

    private final Integer idBI;

    private List<Module> modules;

    public LoggedUserImpl(User userLoggedIn, String token, Integer idBI, List<Module> modules) {
        this.userLoggedIn = userLoggedIn;
        this.token = token;
        this.idBI = idBI;
        this.modules = modules;
    }

    protected User getUserLoggedIn() {
        return userLoggedIn;
    }

    @Override
    public String getName() {
        return userLoggedIn.getName();
    }

    @Override
    public Integer getIdBI() {
        return idBI;
    }

    @Override
    public User getUser(){
        return userLoggedIn;
    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public Boolean haveAccess(String outcome){
        String outcome2 = outcome.replace("form", "list");
        for(Module m: modules) {
            if(m.getUrl().contains(outcome) || m.getUrl().contains(outcome2)){
               return true;
            }else if(outcome.contains("diarizacao") && m.getUrl().contains("diarizacao")){
                return true;
            }else if(outcome.contains("insertForm") && m.getUrl().contains("listForm")){
                return true;
            }else if(outcome.contains("formConnection") && m.getUrl().contains("listConfig")){
                return true;
            }
        }
        return false;
    }
}
