package com.br.people.service;

import com.br.people.dto.request.PersonRequestDTO;
import com.br.people.dto.response.PersonResponseDTO;
import com.br.people.entity.Person;
import com.br.people.repository.PersonRepository;
import com.br.people.util.PersonMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Primary
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    private final PersonMapper personMapper;
    @Override
    public PersonResponseDTO findById(Long id) {
        return personMapper.toPersonDTO(returnPerson(id));
    }

    @Override
    public List<PersonResponseDTO> findAll() {
        return personMapper.toPeopleDTO(personRepository.findAll());
    }

    @Override
    public PersonResponseDTO register(PersonRequestDTO personDTO) {

        Person person = personMapper.toPerson(personDTO);

        return personMapper.toPersonDTO(personRepository.save(person));
    }

    @Override
    public PersonResponseDTO update(Long id, PersonRequestDTO personDTO) {

        Person person = returnPerson(id);

        personMapper.updatePersonData(person,personDTO);

        return personMapper.toPersonDTO(personRepository.save(person));
    }

    @Override
    public String delete(Long id) {

        Person person = returnPerson(id);

        personRepository.deleteById(person.getId());

        return "Person id: "+id+" deleted";
    }

    private Person returnPerson(Long id) {
        return personRepository.findById(id).orElseThrow(() -> new RuntimeException("User wasn't found on database"));
    }
}
