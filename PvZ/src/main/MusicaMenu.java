package main;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;

public class MusicaMenu implements Runnable {

    private volatile boolean running = true;  // Flag para controlar el hilo
    private Clip clip;

    @Override
    public void run() {
        try {
            // Cargar la música
            java.io.InputStream winry = getClass().getResourceAsStream("/sonidos/sly.wav"); // musica input
            BufferedInputStream bufferedInputStream = new BufferedInputStream(winry);
            AudioInputStream scar = AudioSystem.getAudioInputStream(bufferedInputStream); // audio input stream
            clip = AudioSystem.getClip();
            clip.open(scar);
            clip.loop(Clip.LOOP_CONTINUOUSLY); // Reproduce en bucle

            // Mantener el hilo corriendo mientras running sea verdadero
            while (running) {
                try {
                    Thread.sleep(1000);  // Pausa para evitar uso intensivo de CPU
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();  // Restablece el estado de interrupción
                    break;  // Salir del ciclo si el hilo es interrumpido
                }
            }
            
            // Si el ciclo termina, detener la música
            clip.stop();
            clip.close();

        } catch (Exception e) {
            System.out.println("Error al reproducir la música de fondo: " + e.getMessage());
        }
    }

    // Método para detener el hilo de forma segura
    public void stopPlaying() {
        running = false;
    }

    
}

