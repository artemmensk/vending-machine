package com.artemmensk.model;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;

public class MoneyTest {

    @DataProvider(name = "enoughCoinsForChange")
    public static Object[][] successData() {
        return new Object[][]{
                {20,
                        new Money(new HashMap<CoinType, Integer>() {{
                            put(CoinType.ONE_FIFTH, 1);
                        }}),
                        new Money(new HashMap<CoinType, Integer>() {{
                            put(CoinType.ONE_FIFTH, 1);
                        }})},
                {20,
                        new Money(new HashMap<CoinType, Integer>() {{
                            put(CoinType.HALF, 1);
                            put(CoinType.ONE_TENTH, 2);
                        }}),
                        new Money(new HashMap<CoinType, Integer>() {{
                            put(CoinType.ONE_TENTH, 2);
                        }})},
                {30,
                        new Money(new HashMap<CoinType, Integer>() {{
                            put(CoinType.ONE_FIFTH, 2);
                            put(CoinType.ONE_TENTH, 2);
                        }}),
                        new Money(new HashMap<CoinType, Integer>() {{
                            put(CoinType.ONE_FIFTH, 1);
                            put(CoinType.ONE_TENTH, 1);
                        }})},
                {50,
                        new Money(new HashMap<CoinType, Integer>() {{
                            put(CoinType.ONE_FIFTH, 3);
                            put(CoinType.ONE_TENTH, 4);
                        }}),
                        new Money(new HashMap<CoinType, Integer>() {{
                            put(CoinType.ONE_FIFTH, 2);
                            put(CoinType.ONE_TENTH, 1);
                        }})},
                {60,
                        new Money(new HashMap<CoinType, Integer>() {{
                            put(CoinType.ONE_TENTH, 4);
                            put(CoinType.HALF, 2);
                        }}),
                        new Money(new HashMap<CoinType, Integer>() {{
                            put(CoinType.HALF, 1);
                            put(CoinType.ONE_TENTH, 1);
                        }})},
                {130,
                        new Money(new HashMap<CoinType, Integer>() {{
                            put(CoinType.ONE, 2);
                            put(CoinType.ONE_TENTH, 3);
                        }}),
                        new Money(new HashMap<CoinType, Integer>() {{
                            put(CoinType.ONE, 1);
                            put(CoinType.ONE_TENTH, 3);
                        }})},
                {160,
                        new Money(new HashMap<CoinType, Integer>() {{
                            put(CoinType.ONE_FIFTH, 12);
                        }}),
                        new Money(new HashMap<CoinType, Integer>() {{
                            put(CoinType.ONE_FIFTH, 8);
                        }})},
                {160,
                        new Money(new HashMap<CoinType, Integer>() {{
                            put(CoinType.HALF, 1);
                            put(CoinType.ONE_FIFTH, 4);
                            put(CoinType.ONE_TENTH, 4);
                        }}),
                        new Money(new HashMap<CoinType, Integer>() {{
                            put(CoinType.HALF, 1);
                            put(CoinType.ONE_FIFTH, 4);
                            put(CoinType.ONE_TENTH, 3);
                        }})}
        };
    }

    @DataProvider(name = "notEnoughCoinsForChange")
    public static Object[][] failData() {
        return new Object[][]{
                {20,
                        new Money(new HashMap<CoinType, Integer>() {{
                        }})
                },
                {20,
                        new Money(new HashMap<CoinType, Integer>() {{
                            put(CoinType.ONE_TENTH, 1);
                        }})
                },
                {60,
                        new Money(new HashMap<CoinType, Integer>() {{
                            put(CoinType.HALF, 5);
                        }})
                },
                {60,
                        new Money(new HashMap<CoinType, Integer>() {{
                            put(CoinType.ONE, 1);
                            put(CoinType.HALF, 1);
                            put(CoinType.ONE_FIFTH, 1);
                        }})
                }
        };
    }

    @Test(dataProvider = "enoughCoinsForChange")
    public void change(Integer rest, Money money, Money expectedChange) {
        // when
        final Money change = money.computeChange(rest);

        // then
        Assert.assertEquals(change, expectedChange);
    }

    @Test(dataProvider = "notEnoughCoinsForChange", expectedExceptions = IllegalStateException.class)
    public void changeWhenImpossible(Integer rest, Money money) {
        // when
        money.computeChange(rest);

        // then
        // throws exception
    }
}
