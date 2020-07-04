package fr.hypario.raycasting;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            throw new IllegalArgumentException("Wrong argument, please give a scene file to the program");
        }
        ImageGenerator imageGenerator = new ImageGenerator(args[0]);
        imageGenerator.generate();
    }

}
