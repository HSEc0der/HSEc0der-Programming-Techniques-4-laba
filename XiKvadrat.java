package laba_4;

import java.math.*;
import java.util.ArrayList;
import java.util.List;

public class XiKvadrat {
    public static void check(List<BigDecimal> list, int m) {
        // число интервалов
        //int k = 45;
        //int k = (int) (1 + 3.322 * Math.log(list.size()));
        int k = 12;
        //int k =(int) Math.sqrt(length);
        // сортируем массив
        list.sort(new ComporatorBI());
        // разница между границами при разбиении
        int diap = m / k;
        // массив значений-границ
        List<Integer> masSplit = new ArrayList<>();
        masSplit.add(0);
        for (int i = 0; i < k - 1; i++) {
            masSplit.add((i+1) * diap);
        }
        masSplit.add(m);
        int t = 0;
        List<Integer> n_i = new ArrayList<>();
        for (int i = 1; i < masSplit.size(); i++){
            int n  = 0;
            while (t < list.size() && list.get(t).compareTo(BigDecimal.valueOf(masSplit.get(i))) < 0){
                n += 1;
                t += 1;
            }
            n_i.add(n);
        }
        double sum = 0;
        for (int i =0; i < n_i.size();i++){
            sum += n_i.get(i) * n_i.get(i) * k;
        }
        sum /= list.size();
        sum -= list.size();
        System.out.println("Значение критерия хи-квадрат: " + sum);

    }
}
