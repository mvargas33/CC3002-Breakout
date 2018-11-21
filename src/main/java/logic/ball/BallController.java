package logic.ball;

import logic.visitor.*;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class BallController extends Observable {
    private ArrayList<Ball> gameBalls = new ArrayList<>();

    public void addBall(int numero){
        while(numero > 0) {
            gameBalls.add(new Ball());
            numero--;
        }
    }
    public void deleteBall(){
        gameBalls.remove(gameBalls.get(gameBalls.size()-1));
        notifyObservers(this);
    }
    public ArrayList<Ball> getGameBalls(){
        return this.gameBalls;
    }
    public int getNumberOfBalls(){
        return this.gameBalls.size();
    }
    public boolean accept(Visitor v){
        return v.visitBallController(this);
    }

    @Override
    public void addObserver(Observer o){
        super.addObserver(o);
    }

    @Override
    public void deleteObserver(Observer o) {
        super.deleteObserver(o);
    }

    @Override
    public void notifyObservers(Object arg){
        super.notifyObservers(arg);
    }
}
