package br.com.sol7.orcamento.service;

import br.com.sol7.orcamento.exceptions.ConnectionException;
import br.com.sol7.orcamento.exceptions.LoginInvalidoException;
import br.com.sol7.orcamento.exceptions.UserDoesNotExistException;
import br.com.sol7.orcamento.model.Profile;
import br.com.sol7.orcamento.model.User;
import br.com.sol7.orcamento.repository.UserRepository;
import br.com.sol7.orcamento.util.ObjectUtil;
import br.com.sol7.orcamento.util.PwUtil;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;
import org.primefaces.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;


@Service
@Transactional
public class UserService extends BaseService<User, Integer> {

	@Autowired
	public UserService(UserRepository userRepository) {
		super(userRepository);
	}

	@Autowired
	public ConfigService configService;

	public User login(String login, String password) throws LoginInvalidoException,  ConnectionException, UserDoesNotExistException {
		if(!ObjectUtil.nullOrEmpty(findByLoginAndPassword(login, password))){
			return findByLoginAndPassword(login, password);
		}
		throw new LoginInvalidoException("Senha Inválida");
	}

	private BufferedReader getBufferedReader(String strAuth, BufferedReader bufferedReader, URL urlToRequest) throws IOException, ConnectionException {
		HttpURLConnection urlConnection;
		urlConnection = (HttpURLConnection) urlToRequest.openConnection();
		urlConnection.setConnectTimeout(10000);
		urlConnection.setReadTimeout(10000);
		try {
			urlConnection.setRequestProperty("Authorization", "Basic "+ strAuth);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// urlConnection.setRequestMethod(method);

		int statusCode = urlConnection.getResponseCode();
		if(statusCode != 200) {
			throw new ConnectionException("");
		}

		bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"UTF-8"));
		return bufferedReader;
	}

	public User findByLoginAndPassword(String login, String password){
		try {
			String pwdEncrypt = PwUtil.encryptPassword(password);
			return getUserRepository().findByLoginAndPassword(login, pwdEncrypt);
		}catch (Exception e){
			return null;
		}
	}

	public List<User> listAllManager(){
		return getUserRepository().listAllProfile(new Long(3));
	}

	public List<User> listAllSupervisor(){
		return getUserRepository().listAllProfile(new Long(4));
	}

	public UserRepository getUserRepository(){
		return (UserRepository) super.getBaseRepository();
	}
}
