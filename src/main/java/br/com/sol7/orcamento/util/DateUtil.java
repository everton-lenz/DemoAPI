package br.com.sol7.orcamento.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 
 * Classe utilitária para realizar operações básicas com datas (Date)
 *
 */
public class DateUtil {

	/**
	 * Auxilia nos cálculos
	 */
	private static Calendar calendar = Calendar.getInstance();
	private static SimpleDateFormat formatLog = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private static SimpleDateFormat dfmt = new SimpleDateFormat("d 'de' MMMM 'de' yyyy", new Locale("pt", "BR"));

    /**
	 * Transforma a data em String em formado dd/MM/yyyy
	 * @param date Data
	 * @return Data em String
	 */
	public static String getDateAsFormattedString(Date date){
		return sdf.format(date);
	}

    public static String getDateAsFormattedText(Date date){
        return dfmt.format(date);
    }

	public static String getDateAsFormattedLog(Date date){
		return formatLog.format(date);
	}

	public static Date getDate(String date, String format) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.parse(date);
	}
	
	/**
	 * Adicionar 'tempo' em uma determinada data.
	 * 
	 * @param data Data
	 * @param hora Quantidade de horas 
	 * @param minuto Quantidade de minutos.
	 * @return Data com horario modificado
	 */	
	public static Date somarHoraMinutos(Date data, Integer hora, Integer minuto){
		calendar.setTime(data);
		
		calendar.add(Calendar.HOUR_OF_DAY, hora);
		calendar.add(Calendar.MINUTE, minuto);
		
	    return calendar.getTime();	
	}

	/**
	 * Modifica Hora e Minutos de determinada data.
	 * 
	 * @param data Data 
	 * @param hora Hora 
	 * @param minuto Minuto
	 * @return Data com o horario desejado
	 */
	public static Date modificarHorario(Date data, Integer hora, Integer minuto){
		calendar.setTime(data);
		
	    calendar.set(Calendar.HOUR_OF_DAY, hora); 
	    calendar.set(Calendar.MINUTE, minuto);  
	    
	    return calendar.getTime();
	}
	
	/**
	 * Soma quantidade de dias em data
	 * @param data 
	 * @param dias Quantidade de dias
	 * @return Data somado ao numero de dias
	 */
	public static Date somarDias(Date data, int dias){
		calendar.setTime(data);
		
		calendar.add(Calendar.DATE, dias);
		
		return calendar.getTime();
	}
	
	/**
	 * Primeiro dia do mês
	 * @param date Data que contém o mês/ano desejado.
	 * @return Primeiro dia do mês.
	 */
	public static Date getPrimeiroDiaDoMes(Date date) {  
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		
		return calendar.getTime();
	}  
	  
	/**
	 * Ultimo dia do mês
	 * @param date Data que contém o mês/ano desejado
	 * @return Ultimo dia do mês
	 */
	public static Date getUltimoDiaDoMes(Date date) {  
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		
		return calendar.getTime();
	}
	 
	/**
	 * Converte uma String em uma Data com formato dd/MM/yyyy
	 * @param String data
	 * @return Date
	 */
	public static Date converterStringEmData(String data) throws ParseException{
		Date date = new SimpleDateFormat("dd/MM/yyyy").parse(data);
		return date;
	}

	/** 
     * Calcula a diferença de duas datas em dias 
     * <br> 
     * <b>Importante:</b> Quando realiza a diferença em dias entre duas datas, este método considera as horas restantes e as converte em fração de dias. 
     * @param dataInicial 
     * @param dataFinal 
     * @return quantidade de dias existentes entre a dataInicial e dataFinal. 
     */  
	 public static Double diferencaEmDias(Date dataInicial, Date dataFinal){
	        double result = 0;  
	        long diferenca = dataFinal.getTime() - dataInicial.getTime();  
	        double diferencaEmDias = (diferenca /1000) / 60 / 60 /24; //resultado é diferença entre as datas em dias  
	        long horasRestantes = (diferenca /1000) / 60 / 60 %24; //calcula as horas restantes  
	        result = diferencaEmDias + (horasRestantes /24d); //transforma as horas restantes em fração de dias  
	      
	        return result;  
	 }  
	      
    /** 
     * Calcula a diferença de duas datas em horas 
     * <br> 
     * <b>Importante:</b> Quando realiza a diferença em horas entre duas datas, este método considera os minutos restantes e os converte em fração de horas. 
     * @param dataInicial 
     * @param dataFinal 
     * @return quantidade de horas existentes entre a dataInicial e dataFinal. 
     */  
    public static double diferencaEmHoras(Date dataInicial, Date dataFinal){  
        double result = 0;  
        long diferenca = dataFinal.getTime() - dataInicial.getTime();  
        long diferencaEmHoras = (diferenca /1000) / 60 / 60;  
        long minutosRestantes = (diferenca / 1000)/60 %60;  
        double horasRestantes = minutosRestantes / 60d;  
        result = diferencaEmHoras + (horasRestantes);  
          
        return result;  
    }  
      
    /** 
     * Calcula a diferença de duas datas em minutos 
     * <br> 
     * <b>Importante:</b> Quando realiza a diferença em minutos entre duas datas, este método considera os segundos restantes e os converte em fração de minutos. 
     * @param dataInicial 
     * @param dataFinal 
     * @return quantidade de minutos existentes entre a dataInicial e dataFinal. 
     */  
    public static double diferencaEmMinutos(Date dataInicial, Date dataFinal){  
        double result = 0;  
        long diferenca = dataFinal.getTime() - dataInicial.getTime();  
        double diferencaEmMinutos = (diferenca /1000) / 60; //resultado é diferença entre as datas em minutos  
        long segundosRestantes = (diferenca / 1000)%60; //calcula os segundos restantes  
        result = diferencaEmMinutos + (segundosRestantes /60d); //transforma os segundos restantes em minutos  
      
        return result;  
    }

    /**
     * Calcula a Idade conforme uma Data de nascimento
     * @param dataNascimento
     * @return Idade atual 
     */
    public static Integer getIdade(Date dataNascimento){
    	int anoNiver, anoAtual;
    	
    	calendar.setTime(new Date());
    	anoAtual = calendar.get(Calendar.YEAR);
    	
    	calendar.setTime(dataNascimento);
    	anoNiver = calendar.get(Calendar.YEAR);

    	return anoAtual - anoNiver;
    }
    
    /**
     * Transforma uma Hora(String) em uma data
     * @param dataReferencia
     * @param hora
     * @return Dia informado com hora informada no parametro
     */
	public static Date transformarHoraEmDate(Date dataReferencia, String hora){		
		String[] inicio= hora.split(":");
		int horaI = Integer.parseInt(inicio[0]);
		int minutoI = Integer.parseInt(inicio[1]);
		
		Date data  = DateUtil.modificarHorario(dataReferencia, horaI, minutoI);

		return data;
	}
	
    /**
     * Retorna a Hora e minuto de uma data
     * @param dataReferencia
     * @return Hora do tipo Date informado
     */
	public static String transformarDateEmHora(Date dataReferencia){ 
		calendar.setTime(dataReferencia);
		int hora = calendar.get(Calendar.HOUR_OF_DAY);
		int minuto =calendar.get(Calendar.MINUTE);
		return String.format("%02d", hora)+":"+String.format("%02d", minuto);
	}

	/**
	 * Retorna o mês de uma data
	 * @param dataReferencia
	 * @return Mês
	 */
	public static Integer obterMes(Date dataReferencia){
		calendar.setTime(dataReferencia);
		return calendar.get(Calendar.MONTH)+1;
	}
	
}
