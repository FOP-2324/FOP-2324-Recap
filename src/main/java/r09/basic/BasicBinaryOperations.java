package r09.basic;

public interface BasicBinaryOperations<X, Y> {
    X add(X left, X right);
    X mul(X left, Y right);
}
