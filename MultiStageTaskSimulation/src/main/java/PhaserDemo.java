import java.util.concurrent.Phaser;

class Worker implements Runnable{
    private final Phaser phaser;
    private final String name;

    public Worker(Phaser phaser, String name) {
        this.phaser = phaser;
        this.name = name;
//        phaser.register();
    }

    @Override
    public void run() {
        doWork("Loading data",1000);
        phaser.arriveAndAwaitAdvance();

        doWork("Processing data",1500);
        phaser.arriveAndAwaitAdvance();

        doWork("Saving data",1000);
        phaser.arriveAndAwaitAdvance();
        phaser.arriveAndDeregister();
    }

    private void doWork(String task, int time){
        System.out.println(name+" -> "+task);
        try {
            Thread.sleep(time);
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }
}

public class PhaserDemo {
    public static void main(String[] args) {
        Phaser phaser = new Phaser(0); // main thread registered
        for (int i = 1; i <= 3; i++) {
            phaser.register();
            new Thread(new Worker(phaser, "Worker-" + i)).start();
        }

        // Move main thread through phases as well
        for (int phase=0;phase<2;phase++){
            int currentPhase = phaser.getPhase();
            phaser.awaitAdvance(currentPhase); // wait for workers to finish this phase
            System.out.println("---- Phase " + phase + " completed ----");
        }

        System.out.println("All phases completed. Exiting...");
    }
}
