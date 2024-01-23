package datastructure;

import java.util.function.Consumer;

public class Sync {
    
    public Integer nodeId;
    public boolean transfer;
    public boolean isCompleted;

    public Sync()
    {
        transfer = true;
        isCompleted = false;
    }

    public synchronized void send(Integer id, Consumer<Integer> callback)
    {

        while(!transfer)
        {
            try{
                wait();
            }
            catch(InterruptedException e)
            {
                System.out.println("Interrupt caught in send()");
            }
        }
        transfer = false;

        nodeId = id;

        callback.accept(id);

        notify();
    }


    public synchronized void receive(Consumer<Integer> callback)
    {

        while(transfer)
        {
            try{
                wait();
            }catch(InterruptedException e)
            {
                System.out.println("Interrupt caught in receive()");
            }
        }
        transfer = true;

        callback.accept(nodeId);

        notify();
    }

}
