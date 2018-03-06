package com.artemmensk.model;

import com.artemmensk.exception.ErrorMessage;
import lombok.EqualsAndHashCode;

import java.util.*;

/**
 * Immutable.
 * Wrapper around coins.
 */
@EqualsAndHashCode(of = "coins")
public class Money {

    private final Map<CoinType, Integer> coins;

    public Money(Map<CoinType, Integer> coins) {
        this.coins = new HashMap<>();
        Arrays.asList(CoinType.values()).forEach(type -> this.coins.put(type, 0));
        this.coins.putAll(coins);
    }

    public Map<CoinType, Integer> getCoins() {
        return new HashMap<>(coins);
    }

    public Integer value() {
        return coins.entrySet().stream().mapToInt(entry -> entry.getKey().getValue() * entry.getValue()).sum();
    }

    public Money add(Money money) {
        final Map<CoinType, Integer> result = new HashMap<>();

        coins.forEach(result::put);

        money.getCoins().forEach((type, amount) -> {
            if (!result.containsKey(type)) {
                result.put(type, amount);
            } else {
                result.put(type, amount + result.get(type));
            }
        });

        return new Money(result);

    }

    public Money subtract(Money money) {
        final Map<CoinType, Integer> result = new HashMap<>();

        coins.forEach(result::put);

        money.getCoins().forEach((type, amount) -> {

            if (!result.containsKey(type) || result.get(type) - amount < 0) {
                throw new IllegalArgumentException("money can't be negative");
            }

            result.put(type, result.get(type) - amount);
        });

        return new Money(result);
    }

    public Money computeChange(Integer rest) {

        final Map<CoinType, Integer> change = new HashMap<>();

        if (rest.equals(0)) {
            return new Money(change);
        }

        for (Map.Entry<CoinType, Integer> entry : new TreeMap<>(coins).descendingMap().entrySet()) {

            final CoinType coinType = entry.getKey();
            final Integer coinValue = coinType.getValue();
            final Integer coinAmount = entry.getValue();

            if (coinValue > rest) {
                continue;
            }

            for (int counter = 1; counter <= coinAmount; counter++) {
                if (rest.equals(coinValue * counter)) {
                    change.put(coinType, counter);
                    return new Money(change);
                }
                if (rest - (counter * coinValue) < coinValue || coinAmount.equals(counter)) {
                    rest -= coinValue * counter;
                    change.put(coinType, counter);
                    break;
                }
            }
        }
        throw new IllegalStateException(ErrorMessage.IMPOSSIBLE_CHANGE.getMessage());
    }
}
