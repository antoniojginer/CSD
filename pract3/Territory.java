package pract3;

import java.util.concurrent.*;
import java.util.concurrent.locks.*;

public class Territory {
    private int tam; // Matrix size
    private boolean occupied[][];
    String description = "Basic Java Synchronization (using synchronized)";
    private Log log;

    private ReentrantLock Lock = new ReentrantLock();
    private Condition condicion[][] = new Condition[tam][tam];;

    public String getDesc() {
        return description;
    }

    public Territory(int tamT, Log l) {
        tam = tamT;
        occupied = new boolean[tam][tam];
        log = l;
        // Initializing the matrix
        for (int i = 0; i < tam; i++) {
            for (int j = 0; j < tam; j++) {
                occupied[i][j] = false;
            }
        }
    }

    public int getTam() {
        return tam;
    }

    public void putAnt(Ant h, int x, int y) {
        Lock.lock();

        try{
            while (occupied[x][y]) {
                try {
                    // Write in the log: ant waiting
                    log.writeLog(LogItem.PUT, h.getid(), x, y, LogItem.WAITINS,
                        "Ant " + h.getid() + " waiting for [" + x + "," + y + "]");
                    condicion[x][y].await();
                } catch (InterruptedException e) {
                }
            }
            occupied[x][y] = true;
            h.setPosition(x, y);
            // Write in the log: ant inside territory

            log.writeLog(LogItem.PUT, h.getid(), x, y, LogItem.OK, "Ant " + h.getid() + " : [" + x + "," + y + "]  inside");
        }
        finally{
            Lock.unlock();
        }
    }

    public synchronized void takeAnt(Ant h) {
        int x = h.getX();
        int y = h.getY();
        occupied[x][y] = false;
        // Write in the log: ant outside territory
        log.writeLog(LogItem.TAKE, h.getid(), x, y, LogItem.OUT, "Ant " + h.getid() + " : [" + x + "," + y + "] out");
        notifyAll();
    }

    public void moves(Ant h, int x1, int y1, int step) {
        Lock.lock();  
        int x = h.getX();
        int y = h.getY();  
        try{

            while (occupied[x1][y1]) {
                try {
                    // Write in the log: ant waiting
                    log.writeLog(LogItem.MOVE, h.getid(), x1, y1, LogItem.WAIT,
                        "Ant " + h.getid() + " waiting for [" + x1 + "," + y1 + "]");
                    condicion[x1][x1].await();
                } catch (InterruptedException e) {
                }
            }
            occupied[x][y] = false;
            occupied[x1][y1] = true;
            h.setX(x1);
            h.setY(y1);
            // Write in the log: ant moving
            log.writeLog(LogItem.MOVE, h.getid(), x1, y1, LogItem.OK,
                "Ant " + h.getid() + " : [" + x + "," + y + "] -> [" + x1 + "," + y1 + "] step:" + step);
        }
        finally{

            condicion[x][y].signal();
            Lock.unlock();
        }
    }
}
