package laba_4;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Comparator;

public class ComporatorBI implements Comparator<BigDecimal> {
    @Override
    public int compare(BigDecimal o1, BigDecimal o2) {
        return o1.compareTo(o2);
    }
}
