package telran.java52.person.service;

import java.time.LocalDate;

import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import telran.java52.person.dao.PersonRepository;
import telran.java52.person.dto.AddressDto;
import telran.java52.person.dto.ChildDto;
import telran.java52.person.dto.CityPopulationDto;
import telran.java52.person.dto.EmployeeDto;
import telran.java52.person.dto.PersonDto;
import telran.java52.person.dto.exceptions.PersonNotFoundException;
import telran.java52.person.model.Address;
import telran.java52.person.model.Child;
import telran.java52.person.model.Employee;
import telran.java52.person.model.Person;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService, CommandLineRunner {
	final PersonRepository personRepository;
	final ModelMapper modelMapper;
	final PersonModelDtoMapper mapper;

	@Transactional
	@Override
	public Boolean addPerson(PersonDto personDto) {
		if (personRepository.existsById(personDto.getId())) {
			return false;
		}		
		personRepository.save(mapper.mapToModel(personDto));
		return true;
	}

	@Transactional
	@Override
	public PersonDto findPersonById(Integer id) {
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);

		return mapper.mapToDto(person);
	}

	@Transactional(readOnly = true)
	@Override
	public Iterable<PersonDto> findByCity(String city) {
		return personRepository.findPersonByAddress_City(city).map(p -> mapper.mapToDto(p)).toList();
	}

	@Transactional(readOnly = true)
	@Override
	public Iterable<PersonDto> findByAges(int ageFrom, int ageTo) {
		LocalDate currentDate = LocalDate.now();
		int from = currentDate.minusYears(ageTo).getYear();
		int to = currentDate.minusYears(ageFrom).getYear();

		return personRepository.findPersonsByBirthDateBetween(LocalDate.of(from, 1, 1), LocalDate.of(to, 1, 1))
				.map(p -> mapper.mapToDto(p)).toList();
	}

	@Transactional
	@Override
	public PersonDto updateName(String name, int id) {
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		if (name != null) {
			person.setName(name);

		}

		return mapper.mapToDto(person);
	}

	@Transactional(readOnly = true)
	@Override
	public Iterable<PersonDto> findByName(String name) {
		return personRepository.findPersonByName(name).map(p -> mapper.mapToDto(p)).toList();
	}

	@Transactional
	@Override
	public PersonDto updateAdress(int id, AddressDto addressDto) {
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		if (addressDto != null) {
			Address address = new Address(addressDto.getCity(), addressDto.getStreet(), addressDto.getBuilding());
			person.setAddress(address);
		}
		return mapper.mapToDto(person);
	}

	@Transactional
	@Override
	public PersonDto deletePerson(int id) {
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		personRepository.deleteById(id);
		return mapper.mapToDto(person);
	}

	@Transactional(readOnly = true)
	@Override
	public Iterable<CityPopulationDto> getCitiesPopulation() {

		return personRepository.getCitiesPopulation();
	}

	@Transactional
	@Override
	public void run(String... args) throws Exception {
		if (personRepository.count() == 0) {
			Person person = new Person(1000, "John", LocalDate.of(1985, 03, 11),
					new Address("Tel Aviv", "Ben Gvirol", 81));
			Child child = new Child(2000, "Moshe", LocalDate.of(2018, 7, 5), new Address("Ramat Gan", "Bialik", 78),
					"Shalom");
			Employee employee = new Employee(3000, "Pavel", LocalDate.of(2001, 03, 05),
					new Address("Holon", "abotinski", 19), "Wasabi", 20_000);
			personRepository.save(person);
			personRepository.save(child);
			personRepository.save(employee);
		}

	}

}
