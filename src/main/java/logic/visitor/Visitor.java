package logic.visitor;

import logic.ball.*;

public interface Visitor {
    public boolean visitBallController(BallController ballController);
}
