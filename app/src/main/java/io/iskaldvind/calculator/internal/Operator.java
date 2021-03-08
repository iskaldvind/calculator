package io.iskaldvind.calculator.internal;

import java.io.Serializable;

public enum Operator implements Serializable {
    
    NULL, SIN, COS, SQRT, PLUS, MINUS, MULTIPLY, DIVIDE, PERCENT, RND;
    
    public String getValue() {
        String result;
        switch (this) {
            case NULL: {
                result = "";
                break;
            }
            case SIN: {
                result = "sin";
                break;
            }
            case COS: {
                result = "cos";
                break;
            }
            case SQRT: {
                result = "sqrt";
                break;
            }
            case PLUS: {
                result = "+";
                break;
            }
            case MINUS: {
                result = "-";
                break;
            }
            case MULTIPLY: {
                result = "*";
                break;
            }
            case DIVIDE: {
                result =  "/";
                break;
            }
            case PERCENT: {
                result = "%";
                break;
            }
            case RND: {
                result = "rnd";
                break;
            }
            default: {
                result = "E";
            }
        }
        return result;
    }
}
