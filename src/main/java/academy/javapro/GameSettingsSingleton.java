package academy.javapro;

public class GameSettingsSingleton {
    private static GameSettingsSingleton instance;
    private int volume;
    private int brightness;
    private String difficulty;

    private GameSettingsSingleton() {
        volume = 50;
        brightness = 70;
        difficulty = "MEDIUM";
    }

    public static GameSettingsSingleton getInstance() {
        if (instance == null) {
            instance = new GameSettingsSingleton();
        }
        return instance;
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

class SingletonDemo {
    public static void main(String[] args) {
        GameSettingsSingleton menuSettings = GameSettingsSingleton.getInstance();
        menuSettings.setVolume(80);

        GameSettingsSingleton gameplaySettings = GameSettingsSingleton.getInstance();
        System.out.println("Menu Volume: " + menuSettings.getVolume());      // Prints 80
        System.out.println("Gameplay Volume: " + gameplaySettings.getVolume()); // Prints 80
        System.out.println("Same instance? " + (menuSettings == gameplaySettings)); // Prints true
    }
}
