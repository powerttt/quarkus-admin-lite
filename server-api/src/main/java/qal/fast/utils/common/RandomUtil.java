package qal.fast.utils.common;

import java.util.Random;

public class RandomUtil {
    private static final Random random = new Random();

    public static double randomDouble(double start, double end) {
        if (start > end) {
            throw new IllegalArgumentException("Start must be less than end.");
        }
        if (start == end) {
            return start;
        }
        return start + ((end - start) * random.nextDouble());
    }

    public static int randomInt(int i, int size) {
        return random.nextInt(size - i) + i;
    }
}
