package ru.sbt.reports;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportGeneratorImpl<T> implements ReportGenerator<T> {
    private final Class<T> clazz;
    private final Map<String, String> generatedNameMap;

    public ReportGeneratorImpl(Class<T> clazz, Map<String, String> generatedNameMap) {
        this.clazz = clazz;
        this.generatedNameMap = generatedNameMap == null ? new HashMap<>() : generatedNameMap;
    }

    @Override
    public ReportImpl generate(List<? extends T> entities) {
        if (entities == null || entities.isEmpty()) return new ReportImpl(new byte[0]);
        ArrayList<Field> defaultFields = ReportReflection.getFields(clazz);

        StringBuilder builder = new StringBuilder();
        builder.append(String.join(", ", ReportReflection.updateFields(defaultFields, generatedNameMap)));
        builder.append('\n');

        ArrayList<String> valuesOfFields = new ArrayList<>();

        String info;
        for (T entity : entities) {
            valuesOfFields.clear();
            for (Field reportedField : defaultFields) {
                reportedField.setAccessible(true);
                try {
                    info = reportedField.get(entity).toString();
                } catch (NullPointerException | IllegalAccessException e) {
                    info = "no info";
                }
                valuesOfFields.add(info);
            }

            builder.append(String.join(", ", valuesOfFields));
            builder.append('\n');
        }

        return new ReportImpl(builder.toString().getBytes());
    }
}
