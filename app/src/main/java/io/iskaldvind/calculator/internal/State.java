package io.iskaldvind.calculator.internal;

import java.io.Serializable;

public enum State implements Serializable {
    
    COLLECTING_OPERAND_1, COLLECTING_OPERAND_2, STATIC_RESULT, ERROR
}
