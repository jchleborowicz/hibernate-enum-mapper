package pl.jch.test.hibernate_enum_mapper.model.enums;

import pl.jch.test.hibernate_enum_mapper.identifiable_enum.IdentifiableEnum;

public enum PersonalityType implements IdentifiableEnum<String> {

    MOODY("M"),
    DESTRUCTIVE("D"),
    CHEERFUL("C"),
    OBSESIVE("O");

    private final String id;

    PersonalityType(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }
}
