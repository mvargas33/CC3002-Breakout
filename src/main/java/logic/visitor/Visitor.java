package logic.visitor;
import logic.level.*;
import logic.brick.*;

public interface Visitor {
    void visitGlassBrick(GlassBrick b);
    void visitMetalBrick(MetalBrick b);
    void visitWoodenBrick(WoodenBrick b);
}
