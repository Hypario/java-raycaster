package fr.hypario.raycasting;

import fr.hypario.raycasting.environment.Scene;
import fr.hypario.raycasting.exceptions.InvalidEntryException;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;

public class SceneReader {
    private final Scene scene;
    private Scanner scanner;

    SceneReader(String filename) {
        this.scene = new Scene();
        try {
            this.scanner = new Scanner(new File(filename));
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            System.exit(1);
        }
    }

    public Scene read() {
        String line;
        String[] data;

        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            data = line.split("\\s+", 2);
            try {
                if (!(line.startsWith("#") || line.trim().length() == 0))
                    this.scene.getClass().getDeclaredMethod(data[0], String.class).invoke(scene, data[1]);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e ) {
                System.out.println("No such method : " + data[0]);
            }
        }

        return scene;
    }
}
