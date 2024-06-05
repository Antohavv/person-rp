package telran.java52.person.service;

import telran.java52.person.dto.AddressDto;
import telran.java52.person.dto.CityPopulationDto;
import telran.java52.person.dto.PersonDto;

public interface PersonService {
	Boolean addPerson(PersonDto personDto);
	
	PersonDto findPersonById(Integer id);
	
	Iterable<PersonDto> findByCity(String city);
	
	Iterable<PersonDto> findByAges(int ageFrom, int ageTo);
	
	PersonDto updateName(String name, int id);
	
	Iterable<PersonDto> findByName(String name);
	
	PersonDto updateAdress(int id, AddressDto addressDto);
	
	PersonDto deletePerson(int id);
	
	Iterable<CityPopulationDto> getCitiesPopulation();
	
	

}
