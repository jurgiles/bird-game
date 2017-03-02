package es.jurgil.duckhunt;

import android.media.SoundPool;

public class Game {
    private final SoundPool sp;
    private final int[] soundIds;
    int points;

    public Game(SoundPool sp, int[] soundIds) {

        this.sp = sp;
        this.soundIds = soundIds;
    }

    public int getPoints() {
        return points;
    }

    private void addPoints(int i) {
        points += i;
    }

    public void fireShot(Crosshair crosshair, Duck duck) {
        if(crosshair.aimingAt(duck)){
            addPoints(100);
            duck.die();
            duck.speedUp();
            sp.play(soundIds[0], 1, 1, 1, 0, 1.0f);
        }
    }
}
