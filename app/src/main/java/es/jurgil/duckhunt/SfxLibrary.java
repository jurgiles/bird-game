package es.jurgil.duckhunt;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class SfxLibrary {

    private final SoundPool sp;
    private final int[] soundIds;

    public SfxLibrary(Context context) {
        sp = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        soundIds = new int[10];
        soundIds[0] = sp.load(context, R.raw.gotduck, 1);
        soundIds[1] = sp.load(context, R.raw.gunshot, 1);
    }

    public void playGunshot(){
        sp.play(soundIds[1], 1, 1, 1, 0, 1.0f);
    }

    public void playHitsound(){
        sp.play(soundIds[1], 1, 1, 1, 0, 1.0f);
    }


}
