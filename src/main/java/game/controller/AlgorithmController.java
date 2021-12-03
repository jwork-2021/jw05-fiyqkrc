package game.controller;

import com.pFrame.Position;
import game.graphic.creature.Creature;
import game.graphic.interactive.GameThread;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class AlgorithmController extends CreatureController implements Runnable {
    protected Creature aim;
    protected double direction;
    protected double lastSearchAim = System.currentTimeMillis();
    protected Random random = new Random();
    protected Thread thread;


    public AlgorithmController() {
        thread = new Thread(this);
        GameThread.threadSet.add(thread);
        thread.start();
    }

    public void tryMove() {
        if (!controllable.move(direction)) {
            if (random.nextDouble(1) > 0.8)
                direction = random.nextDouble(Math.PI * 2);
        }
    }

    public void trySearchAim() {
        if (System.currentTimeMillis() - lastSearchAim > 2000) {
            aim = controllable.searchAim();
            lastSearchAim = System.currentTimeMillis();
        }
    }

    public void stop() {
        thread.interrupt();
    }

    @Override
    public void run() {
        Thread dataAnalysis=new Thread(new DataAnalysis(this));
        dataAnalysis.start();
        while (!Thread.currentThread().isInterrupted()) {
            try {
                if (controllable.isDead()) {
                    controllable.dead();
                    break;
                }
                trySearchAim();
                if (aim==null) {
                    tryMove();
                } else {
                    controllable.responseToEnemy();
                }
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                aim = null;
                direction = 0;
            }
        }
        dataAnalysis.interrupt();
        GameThread.threadSet.remove(Thread.currentThread());
    }


}

class DataAnalysis implements Runnable{

    public static FileOutputStream stream;

    AlgorithmController controller;

    static{
        try {
            File file=new File("/home/fiyqkrc/data.txt");
            if(!file.exists())
                file.createNewFile();
            stream=new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void wirteInfo(String str){
        try {
            stream.write(str.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public DataAnalysis(AlgorithmController controller){
        this.controller=controller;
    }

    @Override
    public void run() {
        while(!Thread.interrupted()){
            try{
                Thread.sleep(2000);
                if(controller.aim!=null){
                    Creature me=((Creature)controller.controllable);
                    Creature enemy=((Creature)controller.aim);
                    //health,distance,enemy health,nenmy attack
                    String str=String.format("%f\t%f\t%f\t%f\n",me.getHealth(),Position.distance(me.getCentralPosition(), enemy.getCentralPosition()),
                            enemy.getHealth(),enemy.getAttack());
                    wirteInfo(str);
                }
            }
            catch(InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
    }
}
