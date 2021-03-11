package io.iskaldvind.calculator;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

import io.iskaldvind.calculator.internal.Memory;
import io.iskaldvind.calculator.internal.Operator;
import io.iskaldvind.calculator.internal.State;

public class MainActivity extends AppCompatActivity {
    
    @SuppressWarnings("SpellCheckingInspection")
    private final double INTEGER_TRESHOLD = 0.00000000001;
    @SuppressWarnings({"FieldCanBeLocal", "SpellCheckingInspection"})
    private final double VALUE_TRESHOLD = 9999999999.0;
    @SuppressWarnings({"FieldCanBeLocal"})
    private final double PI = Math.PI;
    @SuppressWarnings({"FieldCanBeLocal"})
    private final String ERROR = "ERROR";
    
    private int theme = R.style.AppThemeLight;
    
    private static final String PREFIX = MainActivity.class.getCanonicalName();
    public static final String MEMORY = PREFIX + ".memory";
    public static final String RESULT = PREFIX + ".result";
    public static final String HELPER = PREFIX + ".helper";
    public static final String OPERATOR = PREFIX + ".operator";
    public static final String MAIN = PREFIX + ".main";
    public static final String THEME = PREFIX + ".theme";
    public static final int ACTIVITY_CODE = 100;
    
    private double result;
    private Memory memory;
    
    private TextView screenMain;
    private TextView screenOperator;
    private TextView screenHelper;

    private SharedPreferences sharedPref;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            memory = new Memory();
        }
        sharedPref = getPreferences(MODE_PRIVATE);
        theme = sharedPref.getInt(THEME, R.style.AppThemeLight);
        setTheme(theme);
        
        setContentView(R.layout.activity_main);

        screenMain = findViewById(R.id.screen_main);
        screenHelper= findViewById(R.id.screen_helper);
        screenOperator = findViewById(R.id.screen_operator);

        MaterialButton button0 = findViewById(R.id.bt_0);
        button0.setOnClickListener(it -> onClickDigit(0));
        MaterialButton button1 = findViewById(R.id.bt_1);
        button1.setOnClickListener(it -> onClickDigit(1));
        MaterialButton button2 = findViewById(R.id.bt_2);
        button2.setOnClickListener(it -> onClickDigit(2));
        MaterialButton button3 = findViewById(R.id.bt_3);
        button3.setOnClickListener(it -> onClickDigit(3));
        MaterialButton button4 = findViewById(R.id.bt_4);
        button4.setOnClickListener(it -> onClickDigit(4));
        MaterialButton button5 = findViewById(R.id.bt_5);
        button5.setOnClickListener(it -> onClickDigit(5));
        MaterialButton button6 = findViewById(R.id.bt_6);
        button6.setOnClickListener(it -> onClickDigit(6));
        MaterialButton button7 = findViewById(R.id.bt_7);
        button7.setOnClickListener(it -> onClickDigit(7));
        MaterialButton button8 = findViewById(R.id.bt_8);
        button8.setOnClickListener(it -> onClickDigit(8));
        MaterialButton button9 = findViewById(R.id.bt_9);
        button9.setOnClickListener(it -> onClickDigit(9));

        MaterialButton buttonDot = findViewById(R.id.bt_dot);
        buttonDot.setOnClickListener(it -> onClickDot());

        MaterialButton buttonPi = findViewById(R.id.bt_pi);
        buttonPi.setOnClickListener(it -> onClickPi());

        MaterialButton buttonRnd = findViewById(R.id.bt_rnd);
        buttonRnd.setOnClickListener(it -> onClickRnd());

        MaterialButton buttonBack = findViewById(R.id.bt_back);
        buttonBack.setOnClickListener(it -> onClickBack());

        MaterialButton buttonReset = findViewById(R.id.bt_reset);
        buttonReset.setOnClickListener(it -> onClickReset());

        MaterialButton buttonEquals = findViewById(R.id.bt_equals);
        buttonEquals.setOnClickListener(it -> onClickEquals());

        MaterialButton buttonSin = findViewById(R.id.bt_sin);
        buttonSin.setOnClickListener(it -> onClickUnary(Operator.SIN));
        MaterialButton buttonCos = findViewById(R.id.bt_cos);
        buttonCos.setOnClickListener(it -> onClickUnary(Operator.COS));
        MaterialButton buttonSqrt = findViewById(R.id.bt_sqrt);
        buttonSqrt.setOnClickListener(it -> onClickUnary(Operator.SQRT));

        MaterialButton buttonPlus = findViewById(R.id.bt_plus);
        buttonPlus.setOnClickListener(it -> onClickBinary(Operator.PLUS));
        MaterialButton buttonMinus = findViewById(R.id.bt_minus);
        buttonMinus.setOnClickListener(it -> onClickBinary(Operator.MINUS));
        MaterialButton buttonMultiply = findViewById(R.id.bt_multiply);
        buttonMultiply.setOnClickListener(it -> onClickBinary(Operator.MULTIPLY));
        MaterialButton buttonDivide = findViewById(R.id.bt_divide);
        buttonDivide.setOnClickListener(it -> onClickBinary(Operator.DIVIDE));
        MaterialButton buttonPercent = findViewById(R.id.bt_percent);
        buttonPercent.setOnClickListener(it -> onClickUnary(Operator.PERCENT));

        MaterialButton buttonSettings = findViewById(R.id.bt_settings);
        buttonSettings.setOnClickListener(it -> onClickSettings());

        if (savedInstanceState == null) {
            reset();
        }
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.d("MAIN", "onRestoreInstanceState");
        super.onRestoreInstanceState(savedInstanceState);
        memory = (Memory) savedInstanceState.getSerializable(MEMORY);
        result = savedInstanceState.getDouble(RESULT);
        screenHelper.setText(savedInstanceState.getString(HELPER));
        screenOperator.setText(savedInstanceState.getString(OPERATOR));
        screenMain.setText(savedInstanceState.getString(MAIN));
        theme = savedInstanceState.getInt(THEME);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        Log.d("MAIN", "onSaveInstanceState");
        super.onSaveInstanceState(outState);
        outState.putSerializable(MEMORY, memory);
        outState.putDouble(RESULT, result);
        outState.putString(HELPER, screenHelper.getText().toString());
        outState.putString(OPERATOR, screenOperator.getText().toString());
        outState.putString(MAIN, screenMain.getText().toString());
        outState.putInt(THEME, theme);
    }
     

    private void onClickDigit(int digit) {
        Log.d("Main", "Button " + digit + " click");
        State currentState = memory.getCurrentState();
        if (currentState == State.COLLECTING_OPERAND_1 || currentState == State.COLLECTING_OPERAND_2) {
          String currentValue = screenMain.getText().toString();
          if ( (currentValue.length() < 10 && !currentValue.equals("0")) || currentValue.endsWith(".")) {
              String shownText = currentValue + digit;
              screenMain.setText(shownText);
          } else if (currentValue.equals("0")){
              screenMain.setText(String.valueOf(digit));
          }
        } else if (currentState != State.ERROR) {
            memory.setMemorizedValue(0);
            memory.setCurrentState(State.COLLECTING_OPERAND_1);
            memory.setCurrentOperator(Operator.NULL);
            screenOperator.setText(Operator.NULL.getValue());
            screenMain.setText(String.valueOf(digit));
            result = 0;
        }
    }

    
    private void onClickDot() {
        Log.d("Main", "Button Dot click");
        State currentState = memory.getCurrentState();
        if (currentState == State.COLLECTING_OPERAND_1 || currentState == State.COLLECTING_OPERAND_2) {
            String currentValue = screenMain.getText().toString();
            if (currentValue.length() < 10 && !currentValue.contains(".")) {
                String newValue = currentValue + ".";
                screenMain.setText(newValue);
            }
        } else if (currentState != State.ERROR) {
            memory.setMemorizedValue(0);
            memory.setCurrentState(State.COLLECTING_OPERAND_1);
            memory.setCurrentOperator(Operator.NULL);
            screenOperator.setText(Operator.NULL.getValue());
            screenMain.setText("0.");
            result = 0;
        }
    }

    
    private void onClickPi() {
        Log.d("Main", "Button Pi click");
        State currentState = memory.getCurrentState();
        if (currentState == State.COLLECTING_OPERAND_1 || currentState == State.COLLECTING_OPERAND_2 || currentState == State.STATIC_RESULT) {
            screenMain.setText(formatValueToDisplay(PI));
        }
    }

    
    private void onClickRnd() {
        Log.d("Main", "Button Rnd click");
        State currentState = memory.getCurrentState();
        if (currentState == State.COLLECTING_OPERAND_1 || currentState == State.COLLECTING_OPERAND_2 || currentState == State.STATIC_RESULT) {
            String currentVal = screenMain.getText().toString();
            boolean isNotInteger = currentVal.contains(".");
            if (!isNotInteger && !currentVal.equals("0")) {
                int val = Integer.parseInt(currentVal);
                int res = (int) Math.floor(Math.random() * val);
                screenMain.setText(formatValueToDisplay(res));
            } else if (currentVal.equals("0")) {
                double res = Math.random();
                screenMain.setText(formatValueToDisplay(res));
            } else {
                double val = Double.parseDouble(currentVal);
                double res = Math.random() * val;
                screenMain.setText(formatValueToDisplay(res));
            }
        }
    }

    
    private void onClickBack() {
        Log.d("Main", "Button Back click");
        State currentState = memory.getCurrentState();
        if (currentState == State.COLLECTING_OPERAND_1 || currentState == State.COLLECTING_OPERAND_2 || currentState == State.STATIC_RESULT) {
            String currentText = screenMain.getText().toString();
            String newText = currentText.length() > 1 ? currentText.substring(0, currentText.length() - 1) : "0";
            screenMain.setText(newText);
        } else if (currentState == State.ERROR) {
            reset();
        }
    }
    

    private void onClickReset() {
        Log.d("Main", "Button Reset click");
        reset();
    }

    
    private void onClickEquals() {
        Log.d("Main", "Button Equals click");
        State currentStare = memory.getCurrentState();
        if (currentStare == State.COLLECTING_OPERAND_2) {
            double val1 = memory.getMemorizedValue();
            double val2 = readValue();
            compute(memory.getCurrentOperator(), val1, val2);
            if (memory.getCurrentState() != State.ERROR) {
                screenHelper.setText("");
                screenOperator.setText("");
                screenMain.setText(formatValueToDisplay(result));
                memory.setCurrentState(State.STATIC_RESULT);
            }
        }
    }

    
    private void onClickUnary(Operator passedOperator) {
        Log.d("Main", "Button " + passedOperator + " click");
        State currentState = memory.getCurrentState();
        if (currentState == State.COLLECTING_OPERAND_1 || currentState == State.STATIC_RESULT || currentState == State.COLLECTING_OPERAND_2) {
            double newVal = Double.parseDouble(screenMain.getText().toString());
            switch (passedOperator) {
                case SIN: {
                    if (currentState == State.STATIC_RESULT) {
                        newVal = Math.sin(result);
                    } else {
                        newVal = Math.sin(newVal);
                    }
                    break;
                }
                case COS: {
                    if (currentState == State.STATIC_RESULT) {
                        newVal = Math.cos(result);
                    } else {
                        newVal = Math.cos(newVal);
                    }
                    break;
                }
                case SQRT: {
                    if (newVal >= 0) {
                        if (currentState == State.STATIC_RESULT) {
                            newVal = Math.sqrt(result);
                        } else {
                            newVal = Math.sqrt(newVal);
                        }
                    } else {
                        reset();
                        memory.setCurrentState(State.ERROR);
                        screenMain.setText(ERROR);
                    }
                    break;
                }
                case PERCENT: {
                    if (currentState == State.COLLECTING_OPERAND_2) {
                       double memorized = memory.getMemorizedValue();
                       newVal = (newVal / 100) * memorized;
                    }
                    break;
                }
                
            }
            screenMain.setText(formatValueToDisplay(newVal));
        }
    }

    
    private void onClickBinary(Operator passedOperator) {
        Log.d("Main", "Button " + passedOperator + " click");
        State currentState = memory.getCurrentState();
        if (currentState == State.COLLECTING_OPERAND_1 || currentState == State.STATIC_RESULT) {
            memory.setMemorizedValue(readValue());
            memory.setCurrentOperator(passedOperator);
            screenOperator.setText(passedOperator.getValue());
            String textFromMain = screenMain.getText().toString();
            String textToHelper = textFromMain.endsWith(".") ? textFromMain.substring(0, textFromMain.length() - 1) : textFromMain;
            screenHelper.setText(textToHelper);
            screenMain.setText("0");
            memory.setCurrentState(State.COLLECTING_OPERAND_2);
        } else if (currentState == State.COLLECTING_OPERAND_2) {
            Double value = readValue();
            compute(memory.getCurrentOperator(), memory.getMemorizedValue(), value);
            memory.setMemorizedValue(result);
            memory.setCurrentOperator(passedOperator);
            screenHelper.setText(formatValueToDisplay(result));
            screenMain.setText("0");
            screenOperator.setText(passedOperator.getValue());
        }
    }

    
    @Override
    protected void onResume() {
        super.onResume();
    }
    
    
    private void reset() {
        screenOperator.setText("");
        screenHelper.setText("");
        screenMain.setText("0");
        result = 0;
        memory.reset();
    }
    
    
    private Double readValue() {
        String displayText = screenMain.getText().toString();
        if (displayText.endsWith(".")) displayText = displayText.substring(0, displayText.length() - 1);
        return Double.parseDouble(displayText);
    }
    
    
    private String formatValueToDisplay(double value) {
        double roundedValue = Math.round(value);
        @SuppressLint("DefaultLocale") String displayString = Math.abs(value - roundedValue) < INTEGER_TRESHOLD ? String.valueOf((int) roundedValue) : String.format("%.12f",value);
        if (Math.abs(value) < INTEGER_TRESHOLD || (value > 0 && displayString.length() > 10 && displayString.substring(0, 10).equals("0.00000000")) || value < 0 && displayString.length() > 11 && displayString.substring(1, 9).equals("0.00000000")) {
            return "0";
        } else if (displayString.length() > 11 && displayString.contains(".")) {
            return displayString.substring(0, 10);
        } else if (displayString.length() > 10) {
            return displayString.substring(0, 9);
        } else {
            return displayString;
        }
    }
    
    
    private void compute (Operator op, double val1, double val2) {
        switch (op) {
            case PLUS: {
                result = val1 + val2;
                break;
            }
            case MINUS: {
                result = val1 - val2;
                break;
            }
            case MULTIPLY: {
                result = val1 * val2;
                break;
            }
            case DIVIDE: {
                if (Math.abs(val2) <= INTEGER_TRESHOLD) {
                    reset();
                    memory.setCurrentState(State.ERROR);
                    screenMain.setText(ERROR);
                } else if (Math.abs(val1 / val2) > VALUE_TRESHOLD) {
                    reset();
                    memory.setCurrentState(State.ERROR);
                    screenMain.setText(ERROR);
                } else if (Math.abs(val1 / val2) < INTEGER_TRESHOLD ) {
                    result = 0;
                } else {
                    result = val1 / val2;
                }
            }
        }
    }
    
    private void onClickSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        intent.putExtra(THEME, theme);
        startActivityForResult(intent, ACTIVITY_CODE);
    }
    
    @SuppressLint("ApplySharedPref")
    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent result) {
        super.onActivityResult(requestCode, responseCode, result);
        if (result != null && responseCode == RESULT_OK) {
            if (theme != result.getIntExtra(THEME, R.style.AppThemeLight)) {
                theme = result.getIntExtra(THEME, R.style.AppThemeLight);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt(THEME, theme);
                editor.commit();
                recreate();
            }
        }
    }
}
