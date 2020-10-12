package ru.sbt.reports;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Map;

public class ReportReflection {
    static ArrayList<Field> getFields(Class<?> clazz) {
        ArrayList<Field> getFields = new ArrayList<>();
        while (clazz != null) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if (Modifier.isStatic(field.getModifiers())) continue;
                getFields.add(field);
            }
            clazz = clazz.getSuperclass();
        }
        return getFields;
    }

    static ArrayList<String> updateFields(ArrayList<Field> fields, Map<String, String> generatedNameMap) {
        ArrayList<String> updatedNames = new ArrayList<>();
        if (fields == null) return updatedNames;

        for (Field field : fields) {
            String replaceFieldName = generatedNameMap.get(field.getName());
            String updatedName = replaceFieldName != null ? replaceFieldName : field.getName();
            updatedNames.add(updatedName);
        }
        return updatedNames;
    }
}
