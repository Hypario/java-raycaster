package fr.hypario.raycasting;

public class Main {

    public static void main(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("Wrong argument, please give a scene file to the program");
        }
        Window window = new Window(args[0]);
        window.start();
    }

}
