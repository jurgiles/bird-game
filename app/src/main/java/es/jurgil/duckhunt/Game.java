package es.jurgil.duckhunt;

public class Game {
    private SfxLibrary sfxLibrary;
    int points;

    public Game(SfxLibrary sfxLibrary) {
        this.sfxLibrary = sfxLibrary;
    }

    public int getPoints() {
        return points;
    }

    private void addPoints(int i) {
        points += i;
    }

    public void fireShot(Crosshair crosshair, Duck duck) {
        sfxLibrary.playGunshot();

        if (crosshair.aimingAt(duck)) {
            addPoints(100);
            duck.die();
            duck.speedUp();
            sfxLibrary.playHitsound();
        }
    }
}
