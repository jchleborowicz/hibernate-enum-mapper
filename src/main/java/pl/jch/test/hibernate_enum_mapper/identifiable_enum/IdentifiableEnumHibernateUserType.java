package pl.jch.test.hibernate_enum_mapper.identifiable_enum;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.DynamicParameterizedType;
import org.hibernate.usertype.UserType;

//todo jch asap implement all simple id types
//todo jch implement test
public final class IdentifiableEnumHibernateUserType implements DynamicParameterizedType, UserType {

    private Class<? extends IdentifiableEnum<?>> enumClass;
    private int sqlType;

    @Override
    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return cached;
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {
        return value;
    }

    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        return (Serializable) value;
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        return x == y;
    }

    @Override
    public int hashCode(Object x) throws HibernateException {
        return x == null ? 0 : x.hashCode();
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner)
            throws HibernateException, SQLException {
        //todo jch implement custom mapping types
        final Object enumId;
        switch (this.sqlType) {
            case Types.VARCHAR:
                enumId = rs.getString(names[0]);
                break;
            case Types.INTEGER:
                enumId = rs.getInt(names[0]);
                break;
            default:
                throw new RuntimeException("Unexpeced sql type: " + this.sqlType);
        }

        if (rs.wasNull()) {
            return null;
        }

        return IdentifiableEnumUtil.getEnumById((Class) enumClass, enumId);
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session)
            throws HibernateException, SQLException {
        if (value == null) {
            st.setNull(index, sqlType);
        } else {
            switch (this.sqlType) {
                case Types.VARCHAR:

                    st.setString(index, ((IdentifiableEnum<?>) value).getId().toString());
                    break;
                case Types.INTEGER:
                    @SuppressWarnings("unchecked")
                    final Integer id = ((IdentifiableEnum<Integer>) value).getId();

                    st.setInt(index, id);
                    break;
                default:
                    throw new RuntimeException("Unexpected sql type: " + this.sqlType);
            }
        }
    }

    @Override
    public Object replace(Object original, Object target, Object owner)
            throws HibernateException {
        return original;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Class returnedClass() {
        return enumClass;
    }

    @Override
    public int[] sqlTypes() {
        return new int[]{sqlType};
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void setParameterValues(Properties parameters) {
        final ParameterType params = (ParameterType) parameters.get(PARAMETER_TYPE);
        final Class returnedClass = params.getReturnedClass();

        final Class identifiableEnumIdType = IdentifiableEnumUtil.getIdentifiableEnumIdType(returnedClass);

        if (identifiableEnumIdType == String.class) {
            this.sqlType = Types.VARCHAR;
        } else if (identifiableEnumIdType == Integer.class) {
            this.sqlType = Types.INTEGER;
        } else {
            throw new RuntimeException("Unexpected IdentifiableEnum id type: " + identifiableEnumIdType.getName());
        }

        enumClass = returnedClass;
    }
}
