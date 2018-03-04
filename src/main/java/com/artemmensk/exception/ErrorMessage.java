package com.artemmensk.exception;

import com.artemmensk.Configuration;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ErrorMessage {
    COIN_TYPE_NOT_USED("Coin type not used. Passed type: %s"),
    SLOT_NOT_FOUND("Slot not found. Passed slotId: %s"),
    PRICE_TOO_SMALL("Price can't be less than " + Configuration.MINIMAL_PRICE + " penny. Passed price: %s"),
    NOT_MULTIPLICITY_PRICE("Price must be multiplicity of " + Configuration.MINIMAL_PRICE + " penny. Passed price: %s"),
    NEGATIVE_AMOUNT("Amount can't be negative. Passed amount: %s"),
    NEGATIVE_QUANTITY("Quantity can't be negative. Passed quantity: %s");

    private final String message;
}
