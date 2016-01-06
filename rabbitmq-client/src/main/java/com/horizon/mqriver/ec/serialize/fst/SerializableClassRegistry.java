package com.horizon.mqriver.ec.serialize.fst;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by wesley lee
 */
public class SerializableClassRegistry {

    private static final Set<Class<?>> registrations = new LinkedHashSet<Class<?>>();

    public static void registerClass(Class<?> clazz) {
        registrations.add(clazz);
    }

    public static Set<Class<?>> getRegisteredClasses() {
        return registrations;
    }
}
