# Understanding the Singleton Design Pattern in Java

### Learning Objectives

- Implement the Singleton pattern to ensure a class has only one instance.
- Compare different Singleton implementation approaches and their thread-safety considerations.
- Apply best practices for lazy initialization and handle concurrent access scenarios.

### Introduction

The Singleton pattern is one of the most essential design patterns, primarily used to control object creation. Like a
government ensures a single authority, the Singleton pattern ensures that a specific class has only one instance within
an application. This instance acts as a central point for managing shared resources and maintaining a consistent state
across your application.

The Singleton pattern achieves this by making the class responsible for its own unique instance. Instead of creating new
instances directly, other classes must access this instance through the Singleton class, which carefully controls its
existence.

### Why Use the Singleton Pattern?

Imagine you’re developing a video game where multiple parts of the game need access to the same settings. Without
Singleton, each component might create its own settings instance, causing inconsistent configurations and memory waste.
For example, one instance might have the volume set to 50, while another has it set to 80, leading to a chaotic
experience.

The Singleton pattern provides a global point of access, ensuring that no matter which part of the game accesses the
settings, they all share the same instance. This pattern is invaluable for handling resources that must remain
consistent, such as database connections, logging, configuration, and more.

**Key Characteristics of Singleton**

- **Single Instance:** Ensures only one instance exists
- **Global Access:** Provides a way to access the instance from anywhere
- **Lazy Creation:** Creates the instance only when first needed (optional)
- **Thread Safety:** Handles multiple threads trying to access/create the instance (when needed)

### The Problem: Without Singleton

Let’s start with a basic class implementation without Singleton and see how multiple instances can create inconsistency.

```java
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

```

In this example, we have a `GameSettings` class that stores volume, brightness, and difficulty settings. The `GameDemo`
class creates two instances of `GameSettings`: `menuSettings` and `gameplaySettings`. When we set the volume
of `menuSettings` to 80, the volume of `gameplaySettings` remains at the default value of 50. This demonstrates the lack
of consistency when multiple instances are created.

### Implementing Basic Singleton Pattern

To address this, let’s create a basic Singleton that controls the instance of GameSettings.

```java
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

```

In the single-threaded scenario (shown in `GameSettingsSingleton.java`), everything works perfectly. When we request the
settings instance and change the volume to 80, every subsequent request for the settings shows that same volume level.
This is exactly what we want – all parts of the game seeing and working with the same settings.

This Singleton implementation ensures that only one instance of `GameSettingsSingleton` exists, accessed
through `getInstance()`.

### Threading Issues in Singleton Pattern

In multi-threaded applications, the basic Singleton approach may lead to issues if multiple threads try to create an
instance simultaneously. Consider the following example:

```java
package academy.javapro;

public class ThreadingIssuesDemo {
    public static void main(String[] args) {
        Runnable task = () -> {
            GameSettingsSingleton settings = GameSettingsSingleton.getInstance();
            System.out.println("Thread " + Thread.currentThread().getId() +
                    " got instance: " + settings.hashCode());
        };

        Thread thread1 = new Thread(task);
        Thread thread2 = new Thread(task);

        thread1.start();
        thread2.start();
    }
}

```

The `ThreadingIssuesDemo` creates two threads that both try to get the game settings instance. The issue lies in
the `getInstance()` method, which has what we call a "race condition." Imagine two threads checking if the settings
instance exists at almost exactly the same time. Both see that it's null, and both decide to create a new instance. Now
we have two separate sets of game settings – completely defeating the purpose of a Singleton!

This race condition is similar to two people checking if there's milk in the fridge at the same time. Both see no milk,
both go to the store, and both buy milk – resulting in duplicate purchases. In our game settings case, this could lead
to serious problems: one thread might set the volume to 80, but another thread, working with a different instance, still
sees the default volume of 50. The game ends up in an inconsistent state where different parts are using different
settings.

The solution involves using Java's synchronization mechanisms. By marking the `getInstance()` method as synchronized, we
ensure that only one thread can execute it at a time. It's like putting a lock on the fridge with a note saying "
checking for milk – please wait." Additionally, marking the instance variable as volatile ensures that all threads see
the most up-to-date value of the settings instance.

This example illustrates a fundamental principle in concurrent programming: code that works perfectly in a
single-threaded environment can have subtle but serious bugs when multiple threads are involved. It's crucial to
identify shared resources (like our settings instance) and protect them appropriately using synchronization techniques.
The Singleton pattern, while simple in concept, requires careful implementation to work correctly in a multi-threaded
environment.

### Thread-Safe Singleton Patterns

**Synchronized Method Singleton**

The simplest way to make Singleton thread-safe is by synchronizing `getInstance()`.

```java
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

```

This code presents a robust implementation of a thread-safe Singleton pattern for managing game settings, specifically
handling volume and brightness controls. The `SynchronizedGameSettings` class demonstrates the proper way to maintain a
single source of truth for game configurations. When we run the program, it first creates an initial instance with
default settings (volume at 50 and brightness at 70), which are defined in the private constructor. These default values
are immediately displayed to confirm our starting point. The program then modifies these settings, changing the volume
to 75 and brightness to 85, with the updated values being immediately reflected in our output. The most critical part of
this demonstration comes when we attempt to get another instance of our settings. Instead of creating a new instance
with default values, our synchronized implementation ensures we receive the same instance we previously modified. This
is evidenced by the final output showing the modified values (volume 75 and brightness 85) rather than the default
values, proving our Singleton pattern is working as intended.

Key points that demonstrate successful Singleton implementation:

- We didn't create a new instance
- We're getting the same instance we modified earlier
- Our synchronized approach is maintaining a true singleton

**Double-Checked Locking Singleton**

Double-checked locking reduces the synchronization cost, improving performance.

```java
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

```

In this example we implement a sophisticated thread-safe version of the Singleton pattern called Double-Checked Locking.
The `DoubleCheckedGameSettings` class showcases how to achieve thread safety while optimizing performance compared to a
simple synchronized method. The core of this implementation lies in its `getInstance()` method, which cleverly uses a "
double-check" mechanism along with the volatile keyword. When a thread requests an instance, it first checks if the
instance exists without obtaining a lock (the first check). Only if the instance is null does it enter a synchronized
block, where it performs a second check before creating the instance. This double-checking pattern helps avoid the
performance overhead of synchronization once the instance is created, as most calls to `getInstance()` will find an
existing instance and return it without needing to enter the synchronized block.

The `volatile` keyword on the instance variable is crucial here – it ensures that all threads see the most up-to-date
value of the instance variable, preventing subtle bugs that could occur due to memory visibility issues in
multi-threaded environments. When we run the program, we see the same reliable behavior as our previous synchronized
version: default values (volume 50, brightness 70) are set initially, can be modified (to volume 80, brightness 90), and
these modifications are preserved when we obtain another reference to the instance. However, this implementation
achieves the same thread-safe behavior with potentially better performance, especially in scenarios with frequent
`getInstance()` calls.

Key aspects of Double-Checked Locking:

- The first null check avoids unnecessary synchronization
- The synchronized block ensures thread safety during instance creation
- The second null check prevents multiple instance creation by concurrent threads
- The volatile keyword ensures proper visibility of the instance across threads
- Performance is improved compared to simply synchronizing the entire method

The output demonstrates that our double-checked locking successfully maintains singleton behavior while providing a more
efficient thread-safe implementation.

### Bill Pugh Singleton

The Bill Pugh approach is considered optimal, combining thread safety with lazy initialization.

```java
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

```

In this final example, we explore the most elegant and recommended implementation of the Singleton pattern in Java,
known as the initialization-on-demand holder idiom. The `OptimalGameSettings` class demonstrates this approach which
leverages Java's class loading mechanism to provide thread safety without using explicit synchronization. Rather than
using synchronized blocks or volatile keywords, this implementation creates a private static inner class (Holder) that
contains the single instance. Due to Java's class loading guarantees, the `INSTANCE` will only be created when the
Holder class is first referenced in the `getInstance()` method.

This pattern is considered optimal because it combines the best aspects of thread safety and lazy initialization while
being more readable and maintainable than double-checked locking. When we run the program, we can see it behaving
exactly as expected: setting the volume to 90 and then confirming that we get the same instance when requesting it
again. The output proves both that our settings are maintained (volume stays at 90) and that we're working with the same
instance (the equality check returns true).

The brilliance of this implementation lies in its simplicity. By relying on Java's class loading mechanics rather than
explicit synchronization, we get thread safety for free. The JVM guarantees that the `INSTANCE` will be created exactly
once when it's first needed, and all subsequent calls to `getInstance()` will return this same instance. This approach
is not only thread-safe but also performs better than other implementations since there's no need for synchronization
after initialization.

Key benefits of this implementation:

- Thread safety without explicit synchronization
- Lazy initialization is handled automatically by the JVM
- Clean and simple code that's easy to understand and maintain
- Better performance than synchronized or double-checked locking approaches
- Guaranteed to work correctly on all Java versions and JVM implementations

This pattern represents the current best practice for implementing the Singleton pattern in Java, combining safety,
efficiency, and clarity in one elegant solution.

### Summary

The Singleton pattern is a valuable tool for managing shared resources and providing a consistent state. Depending on
the needs of your application, you can choose from various Singleton implementations, such as synchronized,
double-checked locking, or Bill Pugh Singleton. Each approach has its pros and cons, but for most scenarios, the Bill
Pugh pattern offers a well-balanced solution, providing thread safety, lazy loading, and efficient performance.