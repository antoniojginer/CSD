// CSD feb 2015 Juansa Sendra

public class Pool1 extends Pool {   //no kids alone
    int countIns = 0;
    int countKid = 0;
    public void init(int ki, int cap){}

    public synchronized void kidSwims() throws InterruptedException{

        try {
            while (countIns<1){
                log.waitingToSwim();
                wait();
            }

            countKid++;
            log.swimming();}
        catch(NullPointerException e){
            System.err.println(e);
        }
    }

    public synchronized void kidRests() throws InterruptedException     {
        try{
            countKid --;
            log.resting(); 

            notifyAll();
        }
        catch(NullPointerException e){
            System.err.println(e);
        }
    }

    public synchronized void instructorSwims() throws InterruptedException {
        try{
            countIns ++;
            log.swimming();

            notifyAll();
        }
        catch(NullPointerException e){
            System.err.println(e);
        }
    }

    public synchronized void instructorRests() throws InterruptedException   {
        try{
            while (countIns<=1 && countKid >0){
                log.waitingToRest();
                wait();
            }
            countIns--;

            log.resting();
        }
        catch(NullPointerException e){
            System.err.println(e);
        }
    }
}
