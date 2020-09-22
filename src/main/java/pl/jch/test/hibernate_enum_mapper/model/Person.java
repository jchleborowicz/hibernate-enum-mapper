package pl.jch.test.hibernate_enum_mapper.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import pl.jch.test.hibernate_enum_mapper.model.enums.PersonalityType;

@Entity
@Table(name = "person")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    @Id
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "personality")
    @Type(type = "identifiable_enum_type")
    private PersonalityType personality;

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof Person)) {
            return false;
        }

        return this.id != null && this.id.equals(((Person) other).id);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
