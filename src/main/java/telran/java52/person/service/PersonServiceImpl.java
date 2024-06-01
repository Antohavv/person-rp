package telran.java52.person.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import telran.java52.person.dao.PersonRepository;
import telran.java52.person.dto.AddressDto;
import telran.java52.person.dto.PersonDto;
import telran.java52.person.dto.exceptions.PersonNotFoundException;
import telran.java52.person.model.Address;
import telran.java52.person.model.Person;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {
	final PersonRepository personRepository;
	final ModelMapper modelMapper;

	@Transactional
	@Override
	public Boolean addPerson(PersonDto personDto) {
		if (personRepository.existsById(personDto.getId())) {
			return false;
		}
		personRepository.save(modelMapper.map(personDto, Person.class));
		return true;
	}

	@Override
	public PersonDto findPersonById(Integer id) {
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		return modelMapper.map(person, PersonDto.class);
	}

	@Override
	public Iterable<PersonDto> findByCity(String city) {
		return personRepository.findPersonByAddress_City(city).map(p -> modelMapper.map(p, PersonDto.class)).toList();
	}

	@Override
	public Iterable<PersonDto> findByAges(int ageFrom, int ageTo) {
		LocalDate currentDate = LocalDate.now();
		int from = currentDate.minusYears(ageTo).getYear();		
		int to = currentDate.minusYears(ageFrom).getYear();
		

		return personRepository.findPersonsByBirthDateBetween(LocalDate.of(from, 1, 1), LocalDate.of(to, 1, 1))
				.map(p -> (modelMapper.map(p, PersonDto.class))).toList();
	}

	@Override
	public PersonDto updateName(String name, int id) {
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		if (name != null) {
			person.setName(name);
			personRepository.save(person);
		}

		return modelMapper.map(person, PersonDto.class);
	}

	@Override
	public Iterable<PersonDto> findByName(String name) {
		return personRepository.findPersonByName(name).map(p -> modelMapper.map(p, PersonDto.class)).toList();
	}

	@Override
	public PersonDto updateAdress(int id, AddressDto addressDto) {
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		if (addressDto != null) {
			Address address = new Address(addressDto.getCity(), addressDto.getStreet(), addressDto.getBuilding());
			person.setAddress(address);
			personRepository.save(person);
		}
		return modelMapper.map(person, PersonDto.class);
	}

	@Override
	public PersonDto deletePerson(int id) {
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		personRepository.deleteById(id);
		return modelMapper.map(person, PersonDto.class);
	}

}
