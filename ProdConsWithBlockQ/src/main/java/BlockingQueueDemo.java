import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

class Producer implements Runnable{
    private final BlockingQueue<Integer> queue;

    public Producer(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i <= 10; i++){
                queue.put(i); //waits if queue is full
                System.out.println("Produced: "+i);
                Thread.sleep(200);
            }
            queue.put(-1); // poison pill to stop consumer
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }
}

class Consumer implements Runnable{
    private final BlockingQueue<Integer> queue;

    Consumer(BlockingQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Integer item = queue.take(); // waits if queue is empty
                if(item==-1) break; // poison pill
                System.out.println("Consumed: " + item);
                Thread.sleep(300);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            }
    }
}

public class BlockingQueueDemo {
    public static void main(String[] args) {
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(5);
        new Thread(new Producer(queue), "ProducerThread").start();
        new Thread(new Consumer(queue), "ConsumerThread").start();
    }
}


