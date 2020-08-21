package com.brzesky.cursomc.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.brzesky.cursomc.domain.PagamentoComBoleto;

@Service
public class BoletoService 
{
	public void preencherPagamentoComBoleto(PagamentoComBoleto pagto, Date instanteDoPedido)
	{//Aqui entraria a chamada de um webservice gerador de boletos, já com as datas de vencimento e prestações e etc.
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, 7);
		pagto.setDataVencimento(cal.getTime());
	}
}
