package logic.visitor;

import logic.ball.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class EmptyBallVisitor implements Visitor{

    @Override
    public boolean visitBallController(BallController ballController) {
        ArrayList<Ball> balls = ballController.getGameBalls();
        if(balls.size() == 0){
            return true;
        }
        return false;
    }
}
