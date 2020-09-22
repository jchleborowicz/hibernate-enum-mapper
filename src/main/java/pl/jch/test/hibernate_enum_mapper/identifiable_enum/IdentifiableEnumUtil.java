package pl.jch.test.hibernate_enum_mapper.identifiable_enum;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public class IdentifiableEnumUtil {

    // todo jch clean up and write javadoc
    private static final Map<Class<? extends IdentifiableEnum<?>>, Map<?, ?>> enumMappings = new ConcurrentHashMap<>();

    @SuppressWarnings("unchecked")
    public static <T, S extends IdentifiableEnum<T>> Class<T> getIdentifiableEnumIdType(
            Class<S> identifiableEnumClass) {
        final Type[] genericInterfaces = identifiableEnumClass.getGenericInterfaces();
        for (Type genericInterface : genericInterfaces) {
            if (genericInterface instanceof ParameterizedType
                    && ((ParameterizedType) genericInterface).getRawType() == IdentifiableEnum.class) {
                final Type[] genericTypes = ((ParameterizedType) genericInterface).getActualTypeArguments();
                if (genericTypes.length != 1) {
                    throw new IdentifiableEnumException(
                            "Unexpected number of parameterized types for interface IdentifiableEnum in class " +
                                    identifiableEnumClass);
                }
                return (Class<T>) genericTypes[0];
            }
        }
        throw new IdentifiableEnumException(identifiableEnumClass + " is not an implementation of the interface "
                + IdentifiableEnum.class);
    }

    public static <T, S extends IdentifiableEnum<T>> S getEnumById(Class<S> enumClass, T value) {
        Objects.requireNonNull(enumClass);
        Objects.requireNonNull(value);

        final Class<T> identifiableEnumIdType = getIdentifiableEnumIdType(enumClass);
        if (!identifiableEnumIdType.isInstance(value)) {
            throw new IdentifiableEnumException(
                    "Error when getting enum value for class " + enumClass.getName() + ": expected id type "
                            + identifiableEnumIdType.getName() + ", actual type: " + value.getClass().getName());
        }

        final Map<T, S> mappings = getEnumMappingsFor(enumClass);
        return mappings.get(value);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static <T, S extends IdentifiableEnum<T>> Map<T, S> getEnumMappingsFor(
            Class<? extends IdentifiableEnum<?>> identifiableEnumType) {
        final Map saved = enumMappings.get(identifiableEnumType);
        if (saved != null) {
            return saved;
        }

        final Enum[] enumValues = getEnumValues((Class<? extends Enum>) identifiableEnumType);
        final Map mapping = Arrays.stream(enumValues)
                .collect(Collectors.toMap(x -> ((IdentifiableEnum) x).getId(), Function.identity()));

        enumMappings.put(identifiableEnumType, mapping);

        return (Map<T, S>) mapping;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static <E extends Enum> E[] getEnumValues(Class<E> enumClass) {
        try {
            final Field field = enumClass.getDeclaredField("$VALUES");
            field.setAccessible(true);
            return (E[]) field.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IdentifiableEnumException(e);
        }
    }

}
