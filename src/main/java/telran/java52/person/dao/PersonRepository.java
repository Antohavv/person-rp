package telran.java52.person.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.util.Streamable;

import telran.java52.person.dto.CityPopulationDto;
import telran.java52.person.model.Person;

public interface PersonRepository extends JpaRepository<Person, Integer> {

	@Query("select p from Citizen p where p.name=?1")
	Streamable<Person> findPersonByName(String name);

	@Query("select p from Citizen p where p.address.city=:cityName")
	Streamable<Person> findPersonByAddress_City(@Param("cityName")String city);

	Streamable<Person> findPersonsByBirthDateBetween(LocalDate from, LocalDate to);
	
	@Query("select new telran.java52.person.dto.CityPopulationDto(p.address.city, count(p)) from Citizen p group by p.address.city order by count(p) desc")
	List<CityPopulationDto> getCitiesPopulation();

}
