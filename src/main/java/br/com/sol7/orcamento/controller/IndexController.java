package br.com.sol7.orcamento.controller;

import br.com.sol7.orcamento.model.*;
import br.com.sol7.orcamento.service.*;
import br.com.sol7.orcamento.util.DateUtil;
import br.com.sol7.orcamento.util.JSFUtil;
import br.com.sol7.orcamento.util.ObjectUtil;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.util.Date;

@Component
@Scope("request")
public class IndexController {

    @Autowired
    private SignInSystemService signInSystemService;

    private ScheduleModel diarizacaoEventModel;

    private ScheduleModel budgetEventModel;

    private User user;

    @PostConstruct
    public void init() {
        try {
            user = JSFUtil.getUsuarioLogado().getUser();
            diarizacaoEventModel = new DefaultScheduleModel();
            budgetEventModel = new DefaultScheduleModel();
        }catch (Exception e){
            user = null;
        }
    }

    public String lastAcess(){
        if(!ObjectUtil.nullOrEmpty(user)){
            Date date = signInSystemService.getLastLogin(user);
            return DateUtil.getDateAsFormattedString(date)+" "+DateUtil.transformarDateEmHora(date);
        }else{
            return "";
        }
    }

    public ScheduleModel getDiarizacaoEventModel() {
        return diarizacaoEventModel;
    }

    public void setDiarizacaoEventModel(ScheduleModel diarizacaoEventModel) {
        this.diarizacaoEventModel = diarizacaoEventModel;
    }

    public ScheduleModel getBudgetEventModel() {
        return budgetEventModel;
    }

    public void setBudgetEventModel(ScheduleModel budgetEventModel) {
        this.budgetEventModel = budgetEventModel;
    }
}
