package logic.brick;

import logic.level.AbstractLevel;
import logic.visitor.Visitor;

public class GlassBrick extends AbstractBrick {

    public GlassBrick(){
        super(1, 50);
    }

    @Override
    public void accept(Visitor v) {
        v.visitGlassBrick(this);
    }

}
