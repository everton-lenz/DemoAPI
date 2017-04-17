package br.com.sol7.orcamento.model;

import javax.persistence.*;

/**
 * Created by Everton on 23/02/2015.
 */
@Table(name = "user_account")
@Entity
public class User extends Model{

    private String name;

    private String login;

    private String password;

    private boolean state = true;

    @Column(name = "changed_password")
    private boolean changedPassword = false;

    @ManyToOne
    @JoinColumn(name = "id_profile")
    private Profile profile;

    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email.trim();
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public boolean isChangedPassword() {
        return changedPassword;
    }

    public void setChangedPassword(boolean changedPassword) {
        this.changedPassword = changedPassword;
    }
}
