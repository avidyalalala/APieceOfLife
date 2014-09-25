package com.github.avidyalalala.support.typehandler;

import com.github.avidyalalala.support.IntEnum;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: junyi.glj
 * Date: 3/14/14
 * Time: 1:54 PM
 */
public abstract class IntEnumTypeHandler<E extends IntEnum> implements TypeHandler<E> {

    private Class<E> enumClazz;

    protected IntEnumTypeHandler(Class<E> enumClazz) {
        this.enumClazz = enumClazz;
    }

    @Override
    public void setParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.intValue());
    }

    @Override
    public E getResult(ResultSet rs, String columnName) throws SQLException {
        int val = rs.getInt(columnName);
        return valToStatus(val);
    }

    @Override
    public E getResult(ResultSet rs, int columnIndex) throws SQLException {
        int val = rs.getInt(columnIndex);
        return valToStatus(val);
    }

    @Override
    public E getResult(CallableStatement cs, int columnIndex) throws SQLException {
        int val = cs.getInt(columnIndex);
        return valToStatus(val);
    }

    private E valToStatus(int val) {
        return IntEnumUtils.valueOf(enumClazz, val);
    }

    public static final class IntEnumUtils {

        private IntEnumUtils(){}

        public static <E extends IntEnum> E valueOf(Class<E> enumClazz, int value) {

            for (E i : enumClazz.getEnumConstants()) {
                if (i.intValue() == value) {
                    return i;
                }
            }

            throw new IllegalArgumentException("No this value [" + value + "] of " + enumClazz );
        }

    }
}
