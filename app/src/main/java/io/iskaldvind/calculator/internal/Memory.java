package io.iskaldvind.calculator.internal;

import java.io.Serializable;

public class Memory implements Serializable {
    
    private double memorizedValue;
    private State currentState;
    private Operator currentOperator;
    
    public Memory() {
        reset();
    }

    public double getMemorizedValue() {
        return memorizedValue;
    }

    public void setMemorizedValue(double memorizedValue) {
        this.memorizedValue = memorizedValue;
    }

    public State getCurrentState() {
        return currentState;
    }

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }

    public Operator getCurrentOperator() {
        return currentOperator;
    }

    public void setCurrentOperator(Operator currentOperator) {
        this.currentOperator = currentOperator;
    }
    
    public void reset() {
        this.memorizedValue = 0;
        this.currentState = State.STATIC_RESULT;
        this.currentOperator = Operator.NULL;
    }
}
