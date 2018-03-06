package com.artemmensk.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CoinType {
    ONE_TENTH(10), // 10 penny
    ONE_FIFTH(20), // 20 penny
    HALF(50), // 50 penny
    ONE(100); // £1

    private final Integer value;
}
