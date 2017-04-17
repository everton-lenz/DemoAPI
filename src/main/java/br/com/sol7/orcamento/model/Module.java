package br.com.sol7.orcamento.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Everton on 23/02/2015.
 */
@Table(name = "module")
@Entity
public class Module extends Model implements Comparable<Module> {

    private String name;

    private String description;

    private String icon;

    private String url;

    @Column(name = "order_menu")
    private Integer order;

    private Boolean submenu;

    @ManyToOne
    @JoinColumn(name = "id_module_parent")
    private Module moduleParent;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "moduleParent")
    @OrderBy("order")
    private List<Module> modules = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    @Override
    public int compareTo(Module o) {
        return this.order.compareTo(o.getOrder());
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getSubmenu() {
        return submenu;
    }

    public void setSubmenu(Boolean submenu) {
        this.submenu = submenu;
    }

    public Module getModuleParent() {
        return moduleParent;
    }

    public void setModuleParent(Module moduleParent) {
        this.moduleParent = moduleParent;
    }

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }
}
