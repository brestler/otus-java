package ru.otus.jdbc.query;

import ru.otus.core.Id;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class QueryCreatorImpl<T> implements QueryCreator<T> {

    private final static String insertQuery = "insert into <table_name>(<fields>) values (<values>)";
    private final static String selectQuery = "select <fields> from <table_name> where <id> = ?";
    private final static String updateQuery = "update <table_name> set <field_value_pares> where <id> = ?";

    @Override
    public InsertQuery getInsertQuery(T object) {
        List<Field> fieldsWithoutId = Arrays.stream(object.getClass().getDeclaredFields())
                .filter(field -> field.getAnnotation(Id.class) == null)
                .collect(Collectors.toList());
        List<String> fieldNames = new ArrayList<>();
        Map<Class<?>, Object> parameters = new LinkedHashMap<>();
        for (Field field : fieldsWithoutId) {
            field.setAccessible(true);
            fieldNames.add(field.getName());
            try {
                parameters.put(field.getType(), field.get(object));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        String queryWithTableName = insertQuery.replaceAll("<table_name>", object.getClass().getSimpleName().toLowerCase());
        String queryWithFields = queryWithTableName.replaceAll("<fields>", String.join(",", fieldNames));
        String fullQuery = queryWithFields.replaceAll("<values>", fieldNames.stream().map(name -> "?").collect(Collectors.joining(",")));
        return new InsertQuery(fullQuery, parameters);
    }

    @Override
    public String getSelectQuery(Class<T> clazz) {
        String idField = "";
        List<String> fieldNames = new ArrayList<>();
        for (Field field : clazz.getDeclaredFields()) {
            fieldNames.add(field.getName());
            if (field.getAnnotation(Id.class) != null)
                idField = field.getName();
        }
        if (idField.isEmpty())
            throw new RuntimeException("No field annotated with @Id in " + clazz.getName());
        String queryWithTableName = selectQuery.replaceAll("<table_name>", clazz.getSimpleName().toLowerCase());
        return queryWithTableName.replaceAll("<fields>", String.join(",", fieldNames))
                .replaceAll("<id>", idField);
    }

    @Override
    public UpdateQuery getUpdateQuery(T newObject) {
        String idField = "";
        long idVal = -1;
        List<Field> fieldsWithoutId = new ArrayList<>();
        for (Field field : newObject.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (field.getAnnotation(Id.class) == null)
                fieldsWithoutId.add(field);
            else {
                idField = field.getName();
                try {
                    idVal = (long) field.get(newObject);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        if (idField.isEmpty() || idVal < 0)
            throw new RuntimeException("Id field ether not annotated with @Id or has negative value in "
                    + newObject.getClass().getName());
        Map<String, Object> params = new HashMap<>();
        for (Field field : fieldsWithoutId) {
            field.setAccessible(true);
            try {
                params.put(field.getName(), field.get(newObject));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        String queryWithTableName = updateQuery.replaceAll("<table_name>", newObject.getClass().getSimpleName().toLowerCase());
        String updateParams = params.entrySet().stream()
                .map(entry -> entry.getKey() + "=" +
                        (entry.getValue() instanceof String ? ("\'")+ entry.getValue() +("\'") : entry.getValue().toString()))
                .collect(Collectors.joining(","));
        String finalQuery = queryWithTableName.replaceAll("<field_value_pares>", updateParams).replaceAll("<id>", idField);
        return new UpdateQuery(finalQuery, idVal);
    }
}
