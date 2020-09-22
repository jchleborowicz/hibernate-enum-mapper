package pl.jch.test.hibernate_enum_mapper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import pl.jch.test.hibernate_enum_mapper.model.Person;
import pl.jch.test.hibernate_enum_mapper.model.enums.PersonalityType;
import pl.jch.test.hibernate_enum_mapper.repository.PersonRepository;

@SpringBootApplication
public class HibernateEnumMapperApplication {

    public static void main(String[] args) {
        try (ConfigurableApplicationContext applicationContext = SpringApplication
                .run(HibernateEnumMapperApplication.class, args)) {
            runTest(applicationContext);
        }
    }

    private static void runTest(ConfigurableApplicationContext applicationContext) {
        final PersonRepository repository = applicationContext.getBean(PersonRepository.class);

        printPersons(repository);

        repository.save(Person.builder()
                .id(4L)
                .firstName("Clark")
                .lastName("Kent")
                .personality(PersonalityType.OBSESIVE)
                .build());

        printPersons(repository);
    }

    private static void printPersons(PersonRepository repository) {
        System.out.println("*********** Persons: ");
        repository.findAll()
                .stream()
                .map(p -> p.getFirstName() + " " + p.getLastName() + ": " + p.getPersonality())
                .forEach(System.out::println);
        System.out.println("***********");
    }
}
