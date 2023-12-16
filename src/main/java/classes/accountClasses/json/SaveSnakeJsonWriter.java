package classes.accountClasses.json;

import classes.accountClasses.SaveSnakeClass;
import classes.snakeClasses.Direction;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class SaveSnakeJsonWriter {

    public static void write(String folder, String login, String password,
                             short score, short speed, Direction direction,
                             List<Integer> x, List<Integer> y) {

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        try (FileWriter file = new FileWriter(String.format(folder, login))) {

            file.write(gson.toJson(new SaveSnakeClass(
                    password,
                    score,
                    speed,
                    direction,
                    x, y)));

            file.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}