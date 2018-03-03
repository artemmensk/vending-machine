package com.artemmensk.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ErrorMessage {
    COIN_TYPE_NOT_USED("Coin type %s not used");

    private final String message;
}
