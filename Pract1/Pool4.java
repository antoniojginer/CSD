// CSD feb 2013 Juansa Sendra

public class Pool4 extends Pool { //kids cannot enter if there are instructors waiting to exit
    public int cap, ki, contKid, contIns;
    boolean aux = false;
    public void init(int ki, int cap)           {
        this.ki = ki;
        this.cap = cap;
    }
    public synchronized void  kidSwims() throws InterruptedException     {
        while(contIns < 1 || contIns * ki <= contKid || cap == contKid + contIns || aux){
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
            aux = true;
            wait();
        }
        
        contIns--;
        log.resting(); 
        notifyAll();
    }
}

