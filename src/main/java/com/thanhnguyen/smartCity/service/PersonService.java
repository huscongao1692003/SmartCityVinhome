package com.thanhnguyen.smartCity.service;

import com.thanhnguyen.smartCity.constants.SmartCityConstants;
import com.thanhnguyen.smartCity.model.Person;
import com.thanhnguyen.smartCity.model.Roles;
import com.thanhnguyen.smartCity.repository.PersonRepository;
import com.thanhnguyen.smartCity.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean createNewPerson(Person person){
        boolean isSaved = false;
        Roles role = rolesRepository.getByRoleName(SmartCityConstants.USER_ROLE);
        person.setRoles(role);
        person.setPwd(passwordEncoder.encode(person.getPwd()));
        person = personRepository.save(person);
        if (null != person && person.getPersonId() > 0)
        {
            isSaved = true;
        }
        return isSaved;
    }
}
