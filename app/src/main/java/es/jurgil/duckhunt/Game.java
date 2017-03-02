package es.jurgil.duckhunt;

public class Game {
    int points;

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
        }
    }
}
