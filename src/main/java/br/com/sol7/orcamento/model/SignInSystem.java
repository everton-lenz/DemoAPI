package br.com.sol7.orcamento.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "sign_in_system")
public class SignInSystem extends Model{

	@ManyToOne
	@JoinColumn(name = "id_user_account")
	private User user;

	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	public SignInSystem(User user) {
		this.user = user;
		date = new Date();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
