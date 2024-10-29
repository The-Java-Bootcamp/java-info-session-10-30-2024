package academy.javapro;

public class GameSettings {
    private int volume;
    private int brightness;
    private String difficulty;

    public GameSettings() {
        volume = 50;
        brightness = 70;
        difficulty = "MEDIUM";
    }

    // Getters and setters
    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getBrightness() {
        return brightness;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
}

class GameDemo {
    public static void main(String[] args) {
        GameSettings menuSettings = new GameSettings();
        menuSettings.setVolume(80);

        GameSettings gameplaySettings = new GameSettings();
        System.out.println("Menu Volume: " + menuSettings.getVolume());      // Prints 80
        System.out.println("Gameplay Volume: " + gameplaySettings.getVolume()); // Prints 50
    }
}
