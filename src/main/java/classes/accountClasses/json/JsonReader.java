package classes.accountClasses.json;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;


public class JsonReader <T> {

    public T getObjectFromJson(String folder, String login, Class<T> objectClass) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        T objectFromJson;
        try (FileReader reader = new FileReader((String.format(folder, login)))) {
            objectFromJson = gson.fromJson(reader, objectClass);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
        return objectFromJson;
    }
}
