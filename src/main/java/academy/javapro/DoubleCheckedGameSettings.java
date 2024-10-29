package academy.javapro;

public class DoubleCheckedGameSettings {
    private static volatile DoubleCheckedGameSettings instance;
    private int volume;
    private int brightness;

    private DoubleCheckedGameSettings() {
        volume = 50;
        brightness = 70;
    }

    public static DoubleCheckedGameSettings getInstance() {
        if (instance == null) {
            synchronized (DoubleCheckedGameSettings.class) {
                if (instance == null) {
                    instance = new DoubleCheckedGameSettings();
                }
            }
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
        DoubleCheckedGameSettings settings = DoubleCheckedGameSettings.getInstance();

        // Print the default settings
        System.out.println("Default Volume: " + settings.getVolume());
        System.out.println("Default Brightness: " + settings.getBrightness());

        // Modify the settings
        settings.setVolume(80);
        settings.setBrightness(90);

        // Print the updated settings
        System.out.println("Updated Volume: " + settings.getVolume());
        System.out.println("Updated Brightness: " + settings.getBrightness());

        // Retrieve the instance again and confirm the settings are retained
        DoubleCheckedGameSettings newSettingsReference = DoubleCheckedGameSettings.getInstance();
        System.out.println("Retrieved Volume: " + newSettingsReference.getVolume());
        System.out.println("Retrieved Brightness: " + newSettingsReference.getBrightness());
    }
}