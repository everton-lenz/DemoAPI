package br.com.sol7.orcamento.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Everton on 23/02/2015.
 */
@Table(name = "profile")
@Entity
public class Profile extends Model{

    private String name;

    @OneToMany(fetch = FetchType.EAGER)
    @OrderBy("order")
    @JoinTable(name = "profile_module", joinColumns = @JoinColumn(name = "id_profile"), inverseJoinColumns = @JoinColumn(name = "id_module"))
    private Set<Module> modules = new LinkedHashSet<Module>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Module> getModules() {
        return new ArrayList<Module>(modules);
    }

    public void setModules(List<Module> modules) {
        this.modules = new LinkedHashSet<Module>(modules);
    }

    public boolean containsModuleUrl(String s) {
        try {
            for (Module module : this.modules) {
                if (module.getUrl().equals(s))
                    return true;
            }
            return false;
        }catch (Exception e){
            return false;
        }
    }
}
