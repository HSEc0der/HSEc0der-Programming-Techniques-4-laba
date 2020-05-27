package laba_4;
import java.math.*;
import java.util.*;

public class Generators {
    /** Функция возвращает последовательность, сгенерированную линейным
     *  конгруэнтным методом
     *  X(i+1) = (a * X(i) + c) mod m
     *  возможные значения (a, c, m) :
     *  (106,1283,6075),
     *  (1255,6173,29282),
     *  (281,28411,134456)
     *  (1366,150889,714025)
     *  (1021,24631,116640)
     *  (859,2531,11979)
     *  (1291,4621,21870)
     *  (967,3041,14406)
     *  @param a параметр а
     *  @param c параметр с
     *  @param module параметр module
     *  @param length длина сгенерированный выборки
     *  @return возвращает сгенерированную последовательность
     * */
    public static List<BigDecimal> linearGenerator(int a, int c, int module, long length){
            BigDecimal A = BigDecimal.valueOf(a);
            BigDecimal C = BigDecimal.valueOf(c);
            BigDecimal M = BigDecimal.valueOf(module);
            BigDecimal X0 = BigDecimal.valueOf(new Random().nextInt(Integer.MAX_VALUE));
            BigDecimal Xi;
            List<BigDecimal> list = new ArrayList<>();
            ;
            for (int i  = 0; i < length; i++){
                Xi = X0.multiply(A).add(C).remainder(M);
                list.add(Xi);
                X0 = Xi;
            }
            return list;
    }

    /**
     * Функция - аддитивный ГСПЧ
     * последовательность генерируется с помощью
     * последовательности Фибоначчи с запаздыванием
     * Возвращает эту послежовательнось в виде List
     * возможные значения (a,b):
     * (24,55), (9,49), (19,58), (18,65), (25,73), (38,89), (2,93), (21,94), (11,95), (37, 100),
     * (33,118), (10,111), (37,124), (29,132), (52,145)
     * @param a первый параметр последовательности Фибоначчи с запаздыванием
     * @param b второй параметр последовательности Фибоначчи с запаздыванием
     * @param length длина генерируемой последовательности
     * @param module диапазон возможных значений
     * @return возвращает сгенерированную последовательность
     * */
    public static List<BigDecimal> fibGen(int a, int b, int module, int length){
        BigDecimal M = BigDecimal.valueOf(module);
        List<BigDecimal> values = new ArrayList<>();
        int max = Math.max(a,b);
        Random r = new Random();

        for (int i =0; i < max;i++){
            values.add(BigDecimal.valueOf(r.nextInt(Integer.MAX_VALUE)));
        }
        //values = LinearCongruentialGenerator.linearGenerator(1366,150889,714025,31, max);
        for (int i = max;i < length + max;i++){
            values.add(values.get(i - a).add(values.get(i - b)).remainder(BigDecimal.valueOf(module)));
        }
        return values.subList(max,length + max);
    }

    /**
     * Функция генерирует случайные средства, используя средства языка
     * Случайые числа имеют диапазон от 0 до module
     * @param module задает диапазон чисел
     * @param length задает длину сгенерированной выборки
     * @return выборка, каждый элемент - объект класса BigDecimal
     * */
    public static List<BigDecimal> javaRandom(int module, int length){
        List<BigDecimal> list = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < length; i++){
            list.add(BigDecimal.valueOf(r.nextInt(module)));
        }
        return list;
    }
}
