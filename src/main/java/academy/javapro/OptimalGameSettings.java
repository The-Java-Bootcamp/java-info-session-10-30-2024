package academy.javapro;

public class OptimalGameSettings {
    private int volume;
    private int brightness;

    private OptimalGameSettings() {
        volume = 50;
        brightness = 70;
    }

    private static class Holder {
        private static final OptimalGameSettings INSTANCE = new OptimalGameSettings();
    }

    public static OptimalGameSettings getInstance() {
        return Holder.INSTANCE;
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
        OptimalGameSettings settings = OptimalGameSettings.getInstance();
        settings.setVolume(90);

        OptimalGameSettings anotherSettings = OptimalGameSettings.getInstance();
        System.out.println("Volume: " + settings.getVolume()); // Prints 90
        System.out.println("Same instance? " + (settings == anotherSettings)); // Prints true
    }
}
