package br.com.sol7.orcamento.controller;

import br.com.sol7.orcamento.model.Config;
import br.com.sol7.orcamento.model.Layout;
import br.com.sol7.orcamento.service.BaseService;
import br.com.sol7.orcamento.service.ConfigService;
import br.com.sol7.orcamento.service.LayoutService;
import br.com.sol7.orcamento.util.MessageUtil;
import br.com.sol7.orcamento.util.ObjectUtil;
import org.lindbergframework.spring.scope.AccessScoped;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Controller
@AccessScoped
public class ConfigController extends BaseController<Config, Integer> implements Serializable{

    @Autowired
    private ConfigService configService;

    private List<Config> configList = new ArrayList<Config>();

    private List<Config> config = new ArrayList<Config>();

    private List<Config> filteredConfig;

    public ConfigController() {
        super(Config.class);
    }

    @Override
    public String save() {
        try{
            configService.save(getEntity());
            getMessageUtil().sendMessageToUser(MessageUtil.MessageUtilType.INFO, "global.info", "global.insert.sucess", null, "Configuração");
            init();
            return getListPath();
        }catch (Exception e){
            getMessageUtil().sendMessageToUser(MessageUtil.MessageUtilType.ERROR, "global.error", "global.insert.error", null, "Configuração");
            throw new RuntimeException();
        }
    }

    public List<Config> listAllConfig(){
        return configService.findAll();
    }

    public void loadAllConfigConfig(){
        //config = configService.getConfigRepository().findAllConfig();
        config = configService.findAll();
    }

    public String prepareNewEntity(){
        setEntity(new Config());
        return getFormPath();
    }

    @PostConstruct
    public void init(){
        configList = configService.findAll();
        loadAllConfigConfig();
    }

    public void refreshView(){
        init();
        filteredConfig = null;
    }

    @Override
    public void reset() throws Exception {
        super.reset();
    }

    @Override
    public String getFormPath(){
        return "/view/config/formConfig.xhtml?faces-redirect=true";
    }

    @Override
    public String getListPath() {
        return "/view/config/listConfig.xhtml?faces-redirect=true";
    }

    @Override
    public BaseService<Config, Integer> getBaseService() {
        return configService;
    }

    @Override
    public void delete() {
        super.delete();
        init();
    }

    public List<Config> getConfigList() {
        return configList;
    }

    public void setConfigList(List<Config> configList) {
        this.configList = configList;
    }

    public List<Config> getFilteredConfig() {
        return filteredConfig;
    }

    public void setFilteredConfig(List<Config> filteredConfig) {
        this.filteredConfig = filteredConfig;
    }

    public List<Config> getConfig() {
        return config;
    }

}
