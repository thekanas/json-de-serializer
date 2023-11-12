package by.stolybko.api;

public interface DeSerializer {

    <T> T deSerializingJson(Class<T> clazz, String json);
}
