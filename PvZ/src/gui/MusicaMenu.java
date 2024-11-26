package gui;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;

public class MusicaMenu implements Runnable {

    private volatile boolean running = true;  
    private Clip clip;
    public static String sonidoM;

    @Override
    public void run() {
        try {
            java.io.InputStream winry = getClass().getResourceAsStream(sonidoM); // musica input
            BufferedInputStream bufferedInputStream = new BufferedInputStream(winry);
            AudioInputStream scar = AudioSystem.getAudioInputStream(bufferedInputStream); // audio input stream
            clip = AudioSystem.getClip();
            clip.open(scar);
            clip.loop(Clip.LOOP_CONTINUOUSLY); 

            
            while (running) {
                try {
                    Thread.sleep(1000); 
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();  
                    break;  
                }
            }
            
            clip.stop();
            clip.close();

        } catch (Exception e) {
            System.out.println("Error al reproducir la musica de fondo: " + e.getMessage());
        }
    }

    public void stopPlaying() {
        running = false;
    }

    
}

