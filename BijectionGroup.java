package CSE216HW_3;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BijectionGroup {

    public static <T> Set<Function<T,T>> bijectionsOf(Set<T> domain){
        if (domain == null){
            return null;
        }
        if (domain.size() == 1){
            List<T> oneList = new ArrayList<>(domain);
            List<List<T>> oneBijectionAll = new ArrayList<>();
            oneBijectionAll.add(oneList);
            return oneBijectionAll.stream().map(temp -> (Function<T,T>) a -> temp.get(oneList.indexOf(a))).collect(Collectors.toSet());
        }

        List<T> aList = new ArrayList<>(domain);
        List<T> original = new ArrayList<>(domain);
        return permute(aList).stream().map(temp -> (Function<T,T>) a -> temp.get(original.indexOf(a))).collect(Collectors.toSet());
    };

    public static<T> List<List<T>> permute(List<T> list){
        if(list.size() == 0){
            List<List<T>> returnList = new ArrayList<>();
            returnList.add(new ArrayList<>());
            return returnList;
        }

        T first = list.remove(0);

        List<List<T>> listOne = new ArrayList<>();
        List<List<T>> listRec = permute(list);

        for (List<T> tp : listRec){
            for(int i = 0; i <= tp.size(); i++){
                List<T> temp = new ArrayList<>(tp);
                temp.add(i,first);
                listOne.add(temp);
            }
        }
        return listOne;
    };

    public static <T> Group<Function<T, T>> bijectionGroup(Set<T> finiteSet){
        return new Group<Function<T, T>>() {
            @Override
            public Function<T, T> binaryOperation(Function<T, T> one, Function<T, T> other) {
                return other.andThen(one);
            }

            @Override
            public Function<T, T> identity() {
                return Function.identity();
            }

            @Override
            public Function<T, T> inverseOf(Function<T, T> ttFunction) {
                return ttFunction;
            }
        };
    }
    public static void main(String... args) {
        Set<Integer> a_few = Stream.of(1,2,3).collect(Collectors.toSet());
        // you have to figure out the data type in the line below
        Set<Function<Integer, Integer>> bijections = bijectionsOf(a_few);

        bijections.forEach(aBijection -> {
            a_few.forEach(n -> System.out.printf("%d --> %d; ", n, aBijection.apply(n)));
            System.out.println();
        });
        Group<Function<Integer,Integer>> g = bijectionGroup(a_few);
        Function<Integer,Integer> f1 = bijectionsOf(a_few).stream().findFirst().get();
        Function<Integer,Integer> f2 = g.inverseOf(f1);
        Function<Integer,Integer> id = g.identity();
    }
}
