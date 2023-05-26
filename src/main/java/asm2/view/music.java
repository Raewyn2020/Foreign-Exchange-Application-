package asm2.view;

import javafx.scene.media.AudioClip;
import javafx.scene.media.MediaPlayer;
import static javafx.scene.media.MediaPlayer.INDEFINITE;
import java.io.File;

public class music {
//    public AudioClip ac = new AudioClip(new File("src/main/resources/Walk-Off" +
//            "-the-Earth-Bet-On-Me.mp3").toURI().toString());
    private AudioClip ac = new AudioClip(new File("src/main/resources/Walk-Off" +
        "-the-Earth-Bet-On-Me.wav").toURI().toString());
    public void playMusic() {
        ac.setCycleCount(MediaPlayer.INDEFINITE);
        ac.play();
    }
    public void stopMusic(){
        ac.stop();
    }

}
