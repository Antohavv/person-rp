package telran.java52.person.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.util.Streamable;

import telran.java52.person.model.Person;

public interface PersonRepository extends JpaRepository<Person, Integer> {
	Streamable<Person> findPersonByName(String name);
	Streamable<Person> findPersonByAddress_City(String city);
	Streamable<Person> findPersonsByBirthDateBetween(LocalDate from, LocalDate to);
	
}
