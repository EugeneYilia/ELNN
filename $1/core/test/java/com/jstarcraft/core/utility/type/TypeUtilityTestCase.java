package com.jstarcraft.core.utility.type;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicBoolean;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.type.TypeFactory;
import com.jstarcraft.core.codec.MockComplexObject;
import com.jstarcraft.core.codec.MockEnumeration;
import com.jstarcraft.core.codec.MockMatrix;
import com.jstarcraft.core.utility.TypeUtility;

public class TypeUtilityTestCase {

	private static final TypeFactory typeFactory = TypeFactory.defaultInstance();

	private void convertType(Type type) {
		Assert.assertThat(type, CoreMatchers.equalTo(TypeUtility.convertType(typeFactory.constructType(type))));
	}

	@Test
	public void testConvertType() {
		// 基于对象类型测试
		convertType(MockComplexObject.class);
		convertType(MockMatrix.class);

		// 基于枚举类型测试
		convertType(MockEnumeration.class);

		// 基于数组类型测试
		convertType(MockEnumeration[].class);
		convertType(Integer[].class);
		convertType(int[].class);
		convertType(MockComplexObject[].class);
		convertType(Byte[].class);
		convertType(byte[].class);

		// 基于集合类型测试
		convertType(TypeUtility.parameterize(ArrayList.class, MockEnumeration.class));
		convertType(TypeUtility.parameterize(HashSet.class, MockEnumeration.class));

		convertType(TypeUtility.parameterize(ArrayList.class, Integer.class));
		convertType(TypeUtility.parameterize(TreeSet.class, Integer.class));

		convertType(TypeUtility.parameterize(ArrayList.class, MockComplexObject.class));
		convertType(TypeUtility.parameterize(HashSet.class, MockComplexObject.class));

		// 基于映射类型测试
		convertType(TypeUtility.parameterize(HashMap.class, String.class, MockComplexObject.class));

		// 基于原始与包装类型测试
		convertType(AtomicBoolean.class);
		convertType(Boolean.class);
		convertType(boolean.class);
	}

}
