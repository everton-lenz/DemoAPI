package br.com.sol7.orcamento.util;

import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Classe com responsabilidade de criptograr determinada String.
 * Algoritmo de criptografia: SHA-256
 *
 * @author Marco Noronha
 *
 */
public class PwUtil {

	/**
	 * Criptografa em SHA-256
	 * @param pass String que deseja criptografar
	 * @return String criptografa
	 * @throws java.io.UnsupportedEncodingException Encoding não aceito
	 * @throws java.security.NoSuchAlgorithmException Algoritmo de criptografia não encontrado
	 */
	public static String encryptPassword(String pass) throws UnsupportedEncodingException, NoSuchAlgorithmException {

		MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
		byte messageDigest[] = algorithm.digest(pass.getBytes("UTF-8"));

		StringBuilder passwd = new StringBuilder();
		for (byte b : messageDigest) {
			passwd.append(String.format("%02X", 0xFF & b));
		}

		return passwd.toString();
	}

	/**
	 * Gera um HASH de determinado arquivo
	 * @param file Arquivo desejado
	 * @return HASH do arquivo
	 * @throws java.security.NoSuchAlgorithmException Algoritmo de criptografia não encontrado
	 * @throws java.io.IOException Erro de Entrada / Saída referente ao Arquivo
	 */
	public static String hashFile(File file) throws NoSuchAlgorithmException, IOException {
		MessageDigest digest = MessageDigest.getInstance("MD5");
		InputStream is = new FileInputStream(file);

		byte[] buffer = new byte[8192];
		int read = 0;
		String output = null;

		while( (read = is.read(buffer)) > 0) {
			digest.update(buffer, 0, read);
		}

		byte[] md5sum = digest.digest();
		BigInteger bigInt = new BigInteger(1, md5sum);
		output = bigInt.toString(16);

		is.close();

		return output;
	}

	/**
	 * Gera um HASH de determinado InputStream
	 * @param file Arquivo desejado
	 * @return HASH do arquivo
	 * @throws java.security.NoSuchAlgorithmException Algoritmo de criptografia não encontrado
	 * @throws java.io.IOException Erro de Entrada / Saída referente ao Arquivo
	 */
	public static String hashFile(InputStream file) throws NoSuchAlgorithmException, IOException {
		MessageDigest digest = MessageDigest.getInstance("MD5");
		InputStream is = file;
		
		byte[] buffer = new byte[8192];
		int read = 0;
		String output = null;

		while( (read = is.read(buffer)) > 0) {
			digest.update(buffer, 0, read);
		}		
		
		byte[] md5sum = digest.digest();
		BigInteger bigInt = new BigInteger(1, md5sum);
		output = bigInt.toString(16);
			
		is.close();
		
		return output;	
	}
}