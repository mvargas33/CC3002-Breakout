package logic.brick;

import logic.visitor.Visitor;

public class MetalBrick extends AbstractBrick{

    public MetalBrick(){
        super(10, 0);
    }

    @Override
    public void accept(Visitor v) {
        v.visitMetalBrick(this);
    }

}
