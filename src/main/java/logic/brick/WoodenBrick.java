package logic.brick;

import logic.visitor.Visitor;

public class WoodenBrick extends AbstractBrick {

    public WoodenBrick(){
        super(3, 200);
    }

    @Override
    public void accept(Visitor v) {
        v.visitWoodenBrick(this);
    }

}
