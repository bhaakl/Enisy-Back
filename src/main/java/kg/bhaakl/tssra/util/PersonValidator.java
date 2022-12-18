package kg.bhaakl.tssra.util;

import kg.bhaakl.tssra.models.User;
import kg.bhaakl.tssra.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {

    private final PersonService personService;

    @Autowired
    public PersonValidator(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

        if(personService.getUserByLogin(user.getUsername()).isPresent())
            errors.rejectValue("username", "", "Человек с таким именем пользователя уже существует");
    }
}
