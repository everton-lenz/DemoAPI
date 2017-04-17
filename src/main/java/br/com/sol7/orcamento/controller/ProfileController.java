package br.com.sol7.orcamento.controller;

import br.com.sol7.orcamento.model.Module;
import br.com.sol7.orcamento.model.Profile;
import br.com.sol7.orcamento.service.BaseService;
import br.com.sol7.orcamento.service.ProfileService;
import br.com.sol7.orcamento.util.MessageUtil;
import org.lindbergframework.spring.scope.AccessScoped;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Controller
@AccessScoped
public class ProfileController extends BaseController<Profile, Integer> implements Serializable{

    @Autowired
    private ProfileService profileService;

    private List<Profile> profileList = new ArrayList<Profile>();

    private List<Module> modules = new ArrayList<Module>();

    private List<Profile> filteredProfile;

    public ProfileController() {
        super(Profile.class);
    }

    @Override
    public String save() {
        try{
            profileService.save(getEntity());
            getMessageUtil().sendMessageToUser(MessageUtil.MessageUtilType.INFO, "global.info", "global.insert.sucess", null, "Perfil");
            init();
            return getListPath();
        }catch (Exception e){
            getMessageUtil().sendMessageToUser(MessageUtil.MessageUtilType.ERROR, "global.error", "global.insert.error", null, "Perfil");
            throw new RuntimeException();
        }
    }

    public List<Profile> listAllProfile(){
        return profileService.findAll();
    }

    public void loadAllProfileModules(){
        modules = profileService.getProfileRepository().findAllModuleWithUrl();
    }

    public String prepareNewEntity(){
        setEntity(new Profile());
        return getFormPath();
    }

    @PostConstruct
    public void init(){
        profileList = profileService.findAll();
        loadAllProfileModules();
    }

    public void refreshView(){
        init();
        filteredProfile = null;
    }

    @Override
    public void reset() throws Exception {
        super.reset();
    }

    @Override
    public String getFormPath(){
        return "/view/profile/formProfile.xhtml?faces-redirect=true";
    }

    @Override
    public String getListPath() {
        return "/view/profile/listProfile.xhtml?faces-redirect=true";
    }

    @Override
    public BaseService<Profile, Integer> getBaseService() {
        return profileService;
    }

    @Override
    public void delete() {
        super.delete();
        init();
    }

    @Override
    public void setEntity(Profile entity) {
        filteredProfile =null;
        super.setEntity(entity);
    }

    public List<Profile> getProfileList() {
        return profileList;
    }

    public void setProfileList(List<Profile> profileList) {
        this.profileList = profileList;
    }

    public List<Profile> getFilteredProfile() {
        return filteredProfile;
    }

    public void setFilteredProfile(List<Profile> filteredProfile) {
        this.filteredProfile = filteredProfile;
    }

    public List<Module> getModules() {
        return modules;
    }

}
