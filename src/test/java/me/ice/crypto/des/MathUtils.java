package me.ice.crypto.des;

import java.math.BigDecimal;

public class MathUtils {
    public static void main(String[] args) {
        BigDecimal combination = combination(365, 20);
        System.out.println("combination = " + combination.toString());

        BigDecimal permutation = permutation(365, 20);
        System.out.println("permutation = " + permutation.toString());
        BigDecimal pow = pow(365, 20);
        System.out.println("pow = " + pow.toString());
        System.out.println(permutation.divide(pow, 3, BigDecimal.ROUND_HALF_UP));
    }

    public static BigDecimal permutation(int high, int low) {
        if (high < low) {
            throw new IllegalArgumentException("param is invalid, a: " + high + "b:" + low);
        }

        BigDecimal result = BigDecimal.valueOf(high);
        for (int i = high - 1; i > high - low; i--) {
            result = result.multiply(BigDecimal.valueOf(i));
        }
        return result;
    }


    public static BigDecimal factorial(int number) {
        BigDecimal bigInteger = BigDecimal.valueOf(number);
        for (int i = 1; i < number; i++) {
            bigInteger = bigInteger.multiply(BigDecimal.valueOf(i));
        }
        return bigInteger;
    }

    public static BigDecimal combination(int high, int low) {
        return permutation(high, low).divide(factorial(low));
    }

    public static BigDecimal pow(int number, int exponet) {
        return BigDecimal.valueOf(number).pow(exponet);
    }

}
