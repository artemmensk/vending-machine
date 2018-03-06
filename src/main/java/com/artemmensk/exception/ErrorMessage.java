package com.artemmensk.exception;

import com.artemmensk.ConfigModule;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ErrorMessage {
    COIN_TYPE_NOT_USED("Coin type not used. Passed type: %s"),
    SLOT_NOT_FOUND("Slot not found. Passed slotId: %s"),
    PRICE_TOO_SMALL("Price can't be less than " + ConfigModule.MINIMAL_PRICE + " penny. Passed price: %s"),
    NOT_MULTIPLICITY_PRICE("Price must be multiplicity of " + ConfigModule.MINIMAL_PRICE + " penny. Passed price: %s"),
    NOT_ENOUGH_MONEY("Not enough money for complete purchase. Passed money: %s penny"),
    NOT_ENOUGH_ITEMS("Item sold out. Passed slotId: %s"),
    NEGATIVE_AMOUNT("Amount can't be negative. Passed amount: %s"),
    NEGATIVE_QUANTITY("Quantity can't be negative. Passed quantity: %s"),
    IMPOSSIBLE_CHANGE("Not enough coins for change.");

    private final String message;
}
