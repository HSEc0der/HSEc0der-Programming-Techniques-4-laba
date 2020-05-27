package laba_4;

import java.math.*;
import java.util.*;
import java.io.*;

public class Measure {
    /**
     * Функция считает и возвращает выборочное среднее сгенерированной выборки
     * @param input сгенерированная выборка
     * @return среднее в обертке класса BiGDecimal
     */
    public static BigDecimal expectedValue(List<BigDecimal> input){
        BigDecimal exp = BigDecimal.ZERO;
        for (int i = 0; i < input.size();i++){
            exp = exp.add(input.get(i));
        }
        exp = exp.divide(BigDecimal.valueOf(input.size()),3,RoundingMode.HALF_UP);
        return exp;
    }

    /**
     * Функция считает и возвращает дисперсию сгенерированной выборки
     * @param input сгенерированная выборка
     * @return возвращает дисперсию в виде объекта класса BigDecimal
     */
    public static BigDecimal variance(List<BigDecimal> input){
        BigDecimal expValue = expectedValue(input);
        BigDecimal var = BigDecimal.ZERO;
        for (int i = 0;i < input.size();i++){
            var = var.add((input.get(i).subtract(expValue)).pow(2));
        }
        var = var.divide(BigDecimal.valueOf(input.size()),3,RoundingMode.HALF_UP);
        return var;
    }

    /**
     * Функция считает и возвращает среднеквадратичное отклонение
     * сгенерированной выборки
     * @param input сгенерированная выборка
     * @return среднеквадратичное отклонение в обертке класса BigDecimal
     */
    public static BigDecimal standardDeviation(List<BigDecimal> input){
        Double maximum = 1.7E+308;
        BigDecimal ret;
        if (BigDecimal.valueOf(maximum).compareTo(variance(input)) > 0){
            double var = variance(input).doubleValue();
            var = Math.sqrt(var);
            ret = BigDecimal.valueOf(var);
        }
        else {
            ret = variance(input).sqrt(new MathContext(2));
        }
        return ret;
    }

    /**
     * Функция считает и возвращает коэффициент вариации в процентах
     * @param input сгенерированная выборка
     * @return
     */
    public static BigDecimal coefficientOfVariation(List<BigDecimal> input){
        return standardDeviation(input).divide(expectedValue(input),3,RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
    }

    /**
     * Функция считает и выводит в консоль выборочное среднее, среднеквадратичное
     * отклонение, коэффициент вариации, частотный анализ и стастику Пирсона для выборки, которую генерирует
     * @param param1 один из параметров: для лин. конг. генератора это параметр "a", для
     * последовательности Фибоначчи с запаздыванием - параметр "a"
     * @param param2 один из параметров: для лин. конг. генератора это параметр "c", для
     * последовательности Фибоначчи с запаздыванием - параметр "b"
     * @param module для обоих генераторов задает диапозон возможных значений
     * @param length параметр задает длину сгенерированной выборки
     * @param option параметр по логике должен принимать только два значения: если 0 - то
     * используется линейный конгруэнтный генератор, если 1 - то последовательность Фибоначчи с
     * запаздыванием, в остальных случаях выбрасывает исключение Exception;
     * @return ничего
     */
    public static void help(int param1, int param2, int module, int length, int option) throws Exception{
        List<BigDecimal> input = new ArrayList<>();
        if (option != 0 && option != 1) {
            throw new Exception("Задано неправильное значение параметра option: должно быть 0 или 1");
        }
        if (option == 0) input = Generators.linearGenerator(param1, param2, module,length);
        if (option == 1) input = Generators.fibGen(param1, param2, module,length);
        System.out.println("Выборочное среднее: " + expectedValue(input));
        System.out.println("Среднеквадратичное отклонение: " + standardDeviation(input));
        System.out.println("Коэффициент вариации: " + coefficientOfVariation(input) + "%");
        XiKvadrat.check(input, module);
        frequencyTest(input);
        System.out.println("------------------------------------------------------------");
    }

    /**
     * Функция засекает время, которое нужно для генерации выборок разных обьемов 3 разными способами:
     * линейным конгр. методов, последовет. Фибоначчи и средствами языка
     * Результаты выводятся в консоль
     * @param arr массив должен содержать числа - длины генерируемых выборок
     * @return ничего
     * */
    public static void timeCheck(int[] arr) {
        double start, end;
        List<BigDecimal> list;
        for (int i = 0; i < arr.length; i++) {
            start = System.currentTimeMillis();
            list = Generators.linearGenerator(1291, 4621, 21870, arr[i]);
            end = System.currentTimeMillis();
            System.out.println("Лин. конгр. метод. n = " + arr[i] + ". Время составило: " + (end - start) + " миллисекунд");
            start = System.currentTimeMillis();
            list = Generators.fibGen(37, 100, 20000, arr[i]);
            end = System.currentTimeMillis();
            System.out.println("Посл. Фибоначчи. n = " + arr[i] + ". Время составило: " + (end - start) + " миллисекунд");
            start = System.currentTimeMillis();
            list = Generators.javaRandom(20000, arr[i]);
            end = System.currentTimeMillis();
            System.out.println("Средства языка. n = " + arr[i] + ". Время составило: " + (end - start) + " миллисекунд");
            System.out.println("----------------------------------------------------------");
        }
    }
        /**
         * Функция записывает в файл сгенерированную выборку
         * @param param1 один из параметров: для лин. конг. генератора это параметр "a", для
         * последовательности Фибоначчи с запаздыванием - параметр "a"
         * @param param2 один из параметров: для лин. конг. генератора это параметр "c", для
         * последовательности Фибоначчи с запаздыванием - параметр "b"
         * @param module для обоих генераторов задает диапозон возможных значений
         * @param length параметр задает длину сгенерированной выборки
         * @param option параметр по логике должен принимать только два значения: если 0 - то
         * используется линейный конгруэнтный генератор, если 1 - то последовательность Фибоначчи с
         * запаздыванием, в остальных случаях выбрасывает исключение Exception;
         * @return ничего
         * */
        public static void writeToFile(int param1, int param2, int module, int length, int option) throws Exception{
            FileWriter fw = new FileWriter("D:\\УЧЕБА\\Методы программирования\\4labaJava\\JavaGen.txt",false);
            List<BigDecimal> input = new ArrayList<>();
            if (option != 0 && option != 1) {
                throw new Exception("Задано неправильное значение параметра option: должно быть 0 или 1");
            }
            if (option == 0) input = Generators.linearGenerator(param1, param2, module,length);
            if (option == 1) input = Generators.fibGen(param1, param2, module,length);
            for (int i = 0; i < input.size();i++){
                    fw.write(input.get(i).toString() + "\n");
            }
            fw.close();
        }

        public static void frequencyTest(List<BigDecimal> input){
            BigDecimal min = expectedValue(input).subtract(standardDeviation(input));
            BigDecimal max = expectedValue(input).add(standardDeviation(input));
            int n = input.size();
            for (int i = 0; i < input.size();i++){
                if (input.get(i).compareTo(min) < 0 || input.get(i).compareTo(max) > 0 ){
                    n -= 1;
                }
            }
            System.out.println("Частотный анализ: " + (double)(n) / input.size());
        }
}
