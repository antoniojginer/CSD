// CSD feb 2015 Juansa Sendra

public class Pool3 extends Pool{ //max capacity
    public int cap, ki, contKid, contIns;
    public void init(int ki, int cap)           {
        this.ki = ki;
        this.cap = cap;
    }
    public synchronized void  kidSwims() throws InterruptedException     {
        while(contIns < 1 || contIns * ki <= contKid || cap == contKid + contIns){
            log.waitingToSwim();
            wait();
        }
        
        contKid ++;
        log.swimming();
        notifyAll();
    
    }
    public synchronized void kidRests()      {
        contKid --;
        log.resting();
        notifyAll();
    }
    public synchronized void instructorSwims() throws InterruptedException  {
        while(cap == contIns + contKid){
            log.waitingToSwim();
            wait();
        }
        contIns++;
        log.swimming();
        notifyAll();
    }
    public synchronized void instructorRests() throws InterruptedException  {
        while(contKid >= contIns*ki || (contIns>0 && contKid>0)){
            log.waitingToRest();
            wait();
        }
        
        contIns--;
        log.resting(); 
        notifyAll();
    }
}
