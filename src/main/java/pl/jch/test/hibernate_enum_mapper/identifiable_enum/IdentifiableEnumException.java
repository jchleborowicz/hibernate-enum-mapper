package pl.jch.test.hibernate_enum_mapper.identifiable_enum;

public class IdentifiableEnumException extends RuntimeException {
    public IdentifiableEnumException(String message) {
        super(message);
    }

    public IdentifiableEnumException(String message, Throwable cause) {
        super(message, cause);
    }

    public IdentifiableEnumException(Throwable cause) {
        super(cause);
    }
}
