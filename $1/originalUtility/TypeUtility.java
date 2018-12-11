package com.jstarcraft.core.utility;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.reflect.TypeUtils;

import com.fasterxml.jackson.databind.JavaType;

/**
 * 类型工具
 * 
 * @author Birdy
 *
 */
public class TypeUtility extends TypeUtils {

	/**
	 * 将JavaType转换为Type
	 * 
	 * @param source
	 * @return
	 */
	public static Type convertType(JavaType source) {
		Type target = null;

		if (source.isArrayType()) {
			// 数组类型
			target = source.getRawClass();
		} else if (source.hasGenericTypes()) {
			// 泛型类型
			List<JavaType> types = source.getBindings().getTypeParameters();
			Type[] generics = new Type[types.size()];
			int index = 0;
			for (JavaType type : types) {
				generics[index++] = convertType(type);
			}
			Class<?> clazz = source.getRawClass();
			target = TypeUtility.parameterize(clazz, generics);
		} else {
			target = source.getRawClass();
		}

		return target;
	}

	private final static Type[] emptyTypes = new Type[0];

	public static Type refineType(Type fromType, Class<?> toType, Type... context) {
		if (fromType == null) {
			return null;
		}
		Type[] types = emptyTypes;
		if (fromType instanceof ParameterizedType && TypeUtility.isAssignable(fromType, toType)) {
			ParameterizedType parameterizedType = ParameterizedType.class.cast(fromType);
			types = parameterizedType.getActualTypeArguments();
			int cursor = 0;
			for (int index = 0, size = types.length; index < size; index++) {
				Type type = types[index];
				if (type instanceof TypeVariable || type instanceof WildcardType) {
					if (context.length > cursor) {
						types[index] = context[cursor++];
					}
				}
			}
			if (types.length == toType.getTypeParameters().length) {
				return TypeUtility.parameterize(toType, types);
			}
		}
		Class clazz = TypeUtility.getRawType(fromType, null);
		Type[] interfaceTypes = clazz.getGenericInterfaces();
		for (Type interfaceType : interfaceTypes) {
			Type type = refineType(interfaceType, toType, types);
			if (type != null) {
				return type;
			}
		}
		return refineType(clazz.getGenericSuperclass(), toType, types);
	}

}
