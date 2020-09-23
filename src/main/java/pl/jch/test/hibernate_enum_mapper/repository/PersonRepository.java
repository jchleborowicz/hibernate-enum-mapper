package pl.jch.test.hibernate_enum_mapper.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.jch.test.hibernate_enum_mapper.model.Person;
import pl.jch.test.hibernate_enum_mapper.model.enums.PersonalityType;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    List<Person> findByPersonality(PersonalityType personalityType);
}
