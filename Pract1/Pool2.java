// CSD feb 2015 Juansa Sendra

public class Pool2 extends Pool{ //max kids/instructor
    public int max = 2;
    public int contKid, ki;
    public int contIns, cap;
    
    
    public void init(int ki, int cap)           {
        this.ki = ki;
        this.cap = cap;
    }
    public synchronized void kidSwims() throws InterruptedException     {
        while(contIns<1 || (contIns*ki<=contKid)){
            log.waitingToSwim();
            wait();
        }
        contKid++;
        log.swimming();        
        
    }
    public synchronized void kidRests()      {
        contKid--;
        log.resting();
        
        notifyAll();
    }
    public synchronized void instructorSwims()   {
        contIns++;
        log.swimming();
        
        notifyAll();
    }
    public synchronized void instructorRests() throws InterruptedException   {
        while(contKid >= contIns*ki || (contIns>0 && contKid>0)){
            log.waitingToRest();
            wait();
        } 
        contIns--;
        log.resting();
        
        notifyAll();
    }
}
