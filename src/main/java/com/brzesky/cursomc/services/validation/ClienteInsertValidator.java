package com.brzesky.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.brzesky.cursomc.domain.enums.TipoCliente;
import com.brzesky.cursomc.dto.ClienteNewDTO;
import com.brzesky.cursomc.resources.exception.FieldMessage;
import com.brzesky.cursomc.services.validation.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> 
{
	@Override
	public void initialize(ClienteInsert ann){}

	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context)//Verifica se o objeto é válido.
	{
		List<FieldMessage> list = new ArrayList<>();
		
// inclua os testes aqui, inserindo erros na lista
		if(objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !BR.isValidCPF(objDto.getCpfOuCnpj()))
		{
			list.add(new FieldMessage("cpfOuCnpj", "CPF Inválido!"));
		}
		
		if(objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !BR.isValidCNPJ(objDto.getCpfOuCnpj()))
		{
			list.add(new FieldMessage("cpfOuCnpj", "CNPJ Inválido!"));
		}
		
		for (FieldMessage e : list) 
		{
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName()).addConstraintViolation();
		}
		
		return list.isEmpty();
	}
}
