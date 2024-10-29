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
