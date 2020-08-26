// CSD Mar 2013 Juansa Sendra

public class LimitedTable extends RegularTable { //max 4 in dinning-room
    int cap = 0;
    public LimitedTable(StateManager state) {super(state);}
    
    public synchronized void enter(int id) throws InterruptedException {
        while(cap >=4){
            state.wenter(id);
            wait();
        }
        cap++;
        state.enter(id);
    }
    
    public synchronized void exit(int id)  {
        cap--;
        state.exit(id);
        notifyAll();
    }
}
