package pl.jch.test.hibernate_enum_mapper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.jch.test.hibernate_enum_mapper.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
}
