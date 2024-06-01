package telran.java52.person.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.Delegate;
import lombok.RequiredArgsConstructor;
import telran.java52.person.dto.AddressDto;
import telran.java52.person.dto.PersonDto;
import telran.java52.person.service.PersonService;


@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersonController {
	final PersonService personService;
	
	
	@PostMapping
	public Boolean addPerson(@RequestBody PersonDto personDto) {
		return personService.addPerson(personDto);
	}
	
	@GetMapping("/{id}")
	public PersonDto findPersonById(@PathVariable Integer id) {
		return personService.findPersonById(id);
	}
	
	@GetMapping("/city/{city}")
	public Iterable<PersonDto> findPersonsByCity(@PathVariable String city) {
		return personService.findByCity(city);
	}
	
	@PutMapping("/{id}/name/{name}")
	public PersonDto updateName(@PathVariable int id, @PathVariable String name) {
		return personService.updateName(name, id);
	}
	
	@PutMapping("/{id}/address")
	public PersonDto updateAddress(@PathVariable int id, @RequestBody AddressDto addressDto) {
		return personService.updateAdress(id, addressDto);
	}
	
	@DeleteMapping("/{id}")
	public PersonDto deletePerson(@PathVariable int id) {
		return personService.deletePerson(id);
	}
	
	@GetMapping("/name/{name}")
	public Iterable<PersonDto> findByName(@PathVariable String name){
		return personService.findByName(name);
	}
	
	@GetMapping("/ages/{ageFrom}/{ageTo}")
	public Iterable<PersonDto> findByAge(@PathVariable int ageFrom, @PathVariable int ageTo){
		return personService.findByAges(ageFrom, ageTo);
	}
}
