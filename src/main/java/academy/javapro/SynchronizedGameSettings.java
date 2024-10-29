package academy.javapro;

public class SynchronizedGameSettings {
    private static SynchronizedGameSettings instance;
    private int volume;
    private int brightness;

    private SynchronizedGameSettings() {
        volume = 50;
        brightness = 70;
    }

    public static synchronized SynchronizedGameSettings getInstance() {
        if (instance == null) {
            instance = new SynchronizedGameSettings();
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

    public static void main(String[] args) {
        // Get the singleton instance
        SynchronizedGameSettings settings = SynchronizedGameSettings.getInstance();

        // Print the default settings
        System.out.println("Default Volume: " + settings.getVolume());
        System.out.println("Default Brightness: " + settings.getBrightness());

        // Modify the settings
        settings.setVolume(75);
        settings.setBrightness(85);

        // Print the updated settings
        System.out.println("Updated Volume: " + settings.getVolume());
        System.out.println("Updated Brightness: " + settings.getBrightness());

        // Retrieve the instance again and confirm the settings are retained
        SynchronizedGameSettings newSettingsReference = SynchronizedGameSettings.getInstance();
        System.out.println("Retrieved Volume: " + newSettingsReference.getVolume());
        System.out.println("Retrieved Brightness: " + newSettingsReference.getBrightness());
    }
}
