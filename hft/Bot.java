package hft;

import java.util.Random;

public abstract class Bot implements Runnable{
    protected final Book book;
    protected final Pricer pricer;
    protected final int id;
    protected final Random rnd = new Random();

    private volatile boolean running = true;

    public Bot(Book book, Pricer pricer, int id){
        this.book = book;
        this.pricer = pricer;
        this.id = id;
    }

    @Override
    public void run(){
        while(running){
            try{
                trade();
                //simulating latency
                Thread.sleep(1+ rnd.nextInt(10));
            }catch(InterruptedException e){
                Thread.currentThread().interrupt();
                break;
            }
            /*
            }catch(Exception e){
                System.err.println("bot " + this.id + " crashed");
                e.printStackTrace();
            }
            */
        }
    }
    
    public abstract void trade();

    public void stop(){
        this.running = false;
    }
}
