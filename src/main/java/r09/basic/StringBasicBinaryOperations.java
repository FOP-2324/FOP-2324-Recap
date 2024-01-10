package r09.basic;

public class StringBasicBinaryOperations implements BasicBinaryOperations<String, Integer> {
    @Override
    public String add(String left, String right) {
        return left + right;
    }

    @Override
    public String mul(String left, Integer right) {
        return left.repeat(Math.max(0, right));
    }
}