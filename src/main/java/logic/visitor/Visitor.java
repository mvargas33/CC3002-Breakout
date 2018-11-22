package logic.visitor;
import logic.level.*;
import logic.brick.*;

public interface Visitor {
    void visitNullLevel(NullLevel l);
    void visitRealLevel(RealLevel l);
    void visitGlassBrick(GlassBrick b);
    void visitMetalBrick(MetalBrick b);
    void visitWoodenBrick(WoodenBrick b);
}
