package com.derick.server.services.validation;

import com.derick.server.controllers.exceptions.FieldMessage;
import com.derick.server.domain.dto.NewClientDTO;
import com.derick.server.domain.entities.Client;
import com.derick.server.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class UserInsertValidator implements ConstraintValidator<UserInsert, NewClientDTO> {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public void initialize(UserInsert ann) {
	}

	@Override
	public boolean isValid(NewClientDTO objDto, ConstraintValidatorContext context) {
		
		List<FieldMessage> list = new ArrayList<>();

		Client aux = userRepository.findByEmail(objDto.getEmail());
		if (aux != null) {
			list.add(new FieldMessage("email", "E-mail already exists"));
		}
		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}
