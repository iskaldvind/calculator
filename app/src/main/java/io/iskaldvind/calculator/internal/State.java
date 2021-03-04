package io.iskaldvind.calculator.internal;

import java.io.Serializable;

@SuppressWarnings("SpellCheckingInspection")
public enum State implements Serializable {
    
    COLLECTING_OPERAND_1, COLLECTING_OPERAND_2, UNARY_SUBRESULT, BINARY_SUBRESULT, STATIC_RESULT, RANDOM_RESULT, ERROR
}
