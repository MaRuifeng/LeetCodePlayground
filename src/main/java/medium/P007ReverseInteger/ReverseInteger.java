package medium.P007ReverseInteger;

/**
 * Reverse integer.
 *
 * @author Ruifeng Ma
 * @since 2022-Aug-19
 */

public class ReverseInteger {

    /**
     * Use arithmetic operations and math lib.
     */
    private static int reverseV3( int x ) {
        if (x == 0) return 0;
        if (x == Integer.MIN_VALUE) return 0;

        int powerOf = (int) Math.log10(Math.abs(x)); // x = 0 not applicable

        int res = 0, leftExponent = (int) Math.pow(10, powerOf), rightExponent = 1;
        int msd; // most significant digit

        try {
            while (leftExponent > 0) {
                msd = x / leftExponent;
                res = Math.addExact(res, Math.multiplyExact(msd, rightExponent));
                x = x % leftExponent;
                leftExponent /= 10;
                rightExponent *= 10;
            }
        } catch (ArithmeticException e) {
            return 0;
        }

        return res;
    }

    /**
     * Use arithmetic operations.
     */
    private static int reverseV2( int x ) {
        if (x == 0) return 0;
        if (x == Integer.MIN_VALUE) return 0;

        int sign = x < 0 ? -1 : 1;
        int absX = Math.abs(x);
        int powerOf = (int) Math.log10(absX); // x = 0 not applicable

        int res = 0, leftExponent = (int) Math.pow(10, powerOf), rightExponent = 1;
        int msd; // most significant digit
        while (leftExponent > 0) {
            msd = absX / leftExponent;
            if (Integer.MAX_VALUE / rightExponent < msd) return 0; // overflowing
            if (Integer.MAX_VALUE - res < msd * rightExponent) return 0; // overflowing
            res += msd * rightExponent;
            absX = absX % leftExponent;
            leftExponent /= 10;
            rightExponent *= 10;
        }

        return res * sign;
    }

    /**
     * Use wrapper class for conversion.
     */
    private static int reverseV1( int x ) {
        int sign = x < 0 ? -1 : 1;
        String abs = Integer.toString(Math.abs(x));

        StringBuilder sb = new StringBuilder();
        for (int i = abs.length() - 1; i > -1; i--) sb.append(abs.charAt(i));
        String reversed = sb.toString();

        try {
            return Integer.parseInt(reversed) * sign;
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    @FunctionalInterface
    private static interface Reverse {
        int apply( int x );
    }

    private static void execute( int x, Reverse func, int expected ) {
        System.out.println("Given integer: " + x);
        int actual = func.apply(x);

        assert ( actual == expected ) : "Expected " + expected + ", but received " + actual;
        System.out.println("Result: " + actual);
    }

    public static void main( String[] args ) {
        System.out.println("Welcome to the rabbit hole of reversed integers.");

        System.out.println("# Test case 1: ");
        execute(123, ReverseInteger::reverseV1, 321);
        execute(123, ReverseInteger::reverseV2, 321);
        execute(123, ReverseInteger::reverseV3, 321);

        System.out.println("# Test case 2: ");
        execute(-2985, ReverseInteger::reverseV1, -5892);
        execute(-2985, ReverseInteger::reverseV2, -5892);
        execute(-2985, ReverseInteger::reverseV3, -5892);

        System.out.println("# Test case 3: ");
        execute(Integer.MAX_VALUE, ReverseInteger::reverseV1, 0);
        execute(Integer.MAX_VALUE, ReverseInteger::reverseV2, 0);
        execute(Integer.MAX_VALUE, ReverseInteger::reverseV3, 0);

        System.out.println("# Test case 4: ");
        execute(-2147483648, ReverseInteger::reverseV1, 0); // min int
        execute(-2147483648, ReverseInteger::reverseV2, 0); // min int
        execute(-2147483648, ReverseInteger::reverseV3, 0); // min int

        System.out.println("All rabbits gone.");
    }
}
