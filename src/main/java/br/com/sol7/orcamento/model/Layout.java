package br.com.sol7.orcamento.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Everton on 16/03/2015.
 */
@Table(name = "layout")
@Entity
public class Layout extends Model{

    private String color = "#0777AC";

    private String background;

    private String logo;

    private String icon;

    @Column(name = "login_position")
    private char loginPosition = 'c';

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public char getLoginPosition() {
        return loginPosition;
    }

    public void setLoginPosition(char loginPosition) {
        this.loginPosition = loginPosition;
    }
}
