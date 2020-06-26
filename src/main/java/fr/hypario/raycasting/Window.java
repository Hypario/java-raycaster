package fr.hypario.raycasting;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Window extends Canvas implements Runnable {

    private boolean isRunning = false;
    private Thread engine;

    private final Frame frame;
    private final static String TITLE = "Raycasting - prototype";
    private final ImageGenerator raycaster;

    Window(String sceneFile) {
        this.engine = new Thread(this, "Client - Render Thread");

        this.frame = new Frame(Window.TITLE);
        this.frame.setSize(1024, 768);
        this.frame.setResizable(false);
        this.frame.add(this);
        this.frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        this.frame.setVisible(true);

        this.raycaster = new ImageGenerator(sceneFile);
    }

    public void start() {
        this.isRunning = true;
        this.engine.start();
    }

    public void stop() {
        try {
            this.engine.join();
            this.engine = null;
            this.isRunning = false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        final double ns = 1000000000.0 / 60.0;
        double deltaTime = 0;

        int frames = 0; // how many frames are drawn
        int updates = 0; // how many times update is called

        while(isRunning) {
            long now = System.nanoTime();
            deltaTime += (now - lastTime) / ns;
            lastTime = now;
            while (deltaTime >= 1) {
                update(); // Update (should be called 60 times per seconds)
                updates++;
                deltaTime--;
            }
            render();
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                frame.setTitle(Window.TITLE + " | " + updates + " updates, " + frames + " FPS");
                frames = 0;
                updates = 0;
            }
        }
        stop();
    }

    public void update() {}

    public void render() {
        BufferStrategy bs = this.getBufferStrategy();

        if (bs == null) {
            createBufferStrategy(2);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        BufferedImage image = this.raycaster.generate();

        g.drawImage(image, 0, 0, 1024, 768, null);

        g.dispose();
        bs.show();
    }

    public static void main(String[] args) {

    }
}
