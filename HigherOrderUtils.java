package CSE216HW_3;

import java.util.Arrays;
import java.util.function.*;
import java.util.List;


public class HigherOrderUtils {

    //Part 3
    public interface NamedBiFunction<T, U, R> extends BiFunction<T, U, R> {
        String name();
    }

    public static NamedBiFunction<Double, Double, Double> add = new NamedBiFunction<Double, Double, Double>() {

        @Override
        public Double apply(Double aDouble, Double aDouble2) {
            return aDouble + aDouble2;
        }

        @Override
        public String name() {
            return "plus";
        }
    };

    public static NamedBiFunction<Double, Double, Double> subtract = new NamedBiFunction<Double, Double, Double>() {

        @Override
        public Double apply(Double aDouble, Double aDouble2) {
            return aDouble - aDouble2;
        }

        @Override
        public String name() {
            return "minus";
        }
    };

    public static NamedBiFunction<Double, Double, Double> multiply = new NamedBiFunction<Double, Double, Double>() {

        @Override
        public Double apply(Double aDouble, Double aDouble2) {
            return aDouble * aDouble2;
        }

        @Override
        public String name() {
            return "mult";
        }
    };

    public static NamedBiFunction<Double, Double, Double> divide = new NamedBiFunction<Double, Double, Double>() {

        @Override
        public Double apply(Double aDouble, Double aDouble2) {
            if (aDouble2 == 0){
                throw new java.lang.ArithmeticException();
            }
            return aDouble/aDouble2;
        }

        @Override
        public String name() {
            return "div";
        }
    };

    //Part 4
    public static <T> T zip(List<T> args, List<? extends BiFunction<T, T, T>> bifunctions){
        if((args.size()-1) != bifunctions.size()){
            throw new IllegalArgumentException();
        }
        return args.stream().reduce((T a, T b) -> bifunctions.get(args.indexOf(b)-1).apply(a,b)).orElse(null);
    }

    public static void main(String... args) {
        List<Double> numbers = Arrays.asList(-0.5, 2.0, 3.0, 0.0, 4.0); // documentation example
        List<NamedBiFunction<Double, Double, Double>> operations = Arrays.asList(add,multiply,add,divide);
        Double d = zip(numbers, operations); // expected correct value: 1.125
        System.out.println(d);
// different use case, not with NamedBiFunction objects
        List<String> strings = Arrays.asList("a", "n", "t");
// note the syntax of this lambda expression
        BiFunction<String, String, String> concat = (s, t) -> s + t;
        String s = zip(strings, Arrays.asList(concat, concat)); // expected correct value: "ant"
        System.out.println(s);
    }

}
