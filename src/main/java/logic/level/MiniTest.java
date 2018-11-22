package logic.level;
import java.util.Random;

public class MiniTest {
    public static void main(String[] args) {
        Random random = new Random(123);
        System.out.println(random.nextDouble());
        System.out.println(random.nextDouble());
        System.out.println(random.nextDouble());
        System.out.println(random.nextDouble());
    }
}
