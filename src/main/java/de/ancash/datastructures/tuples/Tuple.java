package de.ancash.datastructures.tuples;

public class Tuple {

    private Tuple() {
    }

    public static <A> Unit<A> of(A first) {
        return Unit.of(first);
    }

    public static <A, B> Duplet<A, B> of(A first, B second) {
        return Duplet.of(first, second);
    }

    public static <A, B, C> Triplet<A, B, C> of(A first, B second, C third) {
        return Triplet.of(first, second, third);
    }

    public static <A, B, C, D> Quartet<A, B, C, D> of(A first, B second, C third, D fourth) {
        return Quartet.of(first, second, third, fourth);
    }
    
    public static <A, B, C, D,E> Quintet<A, B, C, D,E> of(A first, B second, C third, D fourth, E fifth) {
        return Quintet.of(first, second, third, fourth, fifth);
    }
}