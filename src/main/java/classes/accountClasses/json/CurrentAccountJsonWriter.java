package classes.accountClasses.json;

import classes.accountClasses.CurrentAccountSaveClass;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;

public class CurrentAccountJsonWriter {

    public static void write(String folder, String fileName, String login, String password, boolean signed) {

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        try (FileWriter file = new FileWriter(String.format(folder, fileName))) {

            file.write(gson.toJson(new CurrentAccountSaveClass(login, password, signed)));
            file.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
