package io.iskaldvind.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.Calendar;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    private TextView buttonValue;
    private EditText editText;
    private Switch switchWidget;
    private CheckBox checkBox;
    private TextView switchValue;
    private TextView checkBoxValue;
    private ToggleButton toggleButton;
    private String editTextValue = "";
    private Integer buttonClickedTimes = 0;
    private Boolean isSwitchChecked = false;
    private Boolean isCheckBoxChecked = false;
    private Boolean isToggleButtonChecked = false;
    private LinearLayout calendarContainer;
    private CalendarView calendar;

    
    enum Checkable {
        SWITCH, BOX
    }
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadWidgetsLayout(null);
    }
    
    public void loadAttributesLayout(View buttonView) {
        editTextValue = editText.getText().toString();
        isSwitchChecked = switchWidget.isChecked();
        isCheckBoxChecked = checkBox.isChecked();
        isToggleButtonChecked = toggleButton.isChecked();
        setContentView(R.layout.activity_main_attributes);
    }
    
    
    public void loadWidgetsLayout(View buttonView) {
        setContentView(R.layout.activity_main_widgets);
        editText = findViewById(R.id.edit_text);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(view -> onButtonClick());
        buttonValue = findViewById(R.id.button_value);
        switchWidget = findViewById(R.id.switch_widget);
        switchWidget.setOnCheckedChangeListener((view, isChecked) -> onCheck(Checkable.SWITCH, isChecked));
        switchValue = findViewById(R.id.switch_value);
        checkBox = findViewById(R.id.checkbox);
        checkBox.setOnCheckedChangeListener((view, isChecked) -> onCheck(Checkable.BOX, isChecked));
        checkBoxValue = findViewById(R.id.checkbox_value);
        toggleButton = findViewById(R.id.toggle_button);
        toggleButton.setOnCheckedChangeListener((view, isChecked) -> onToggle(isChecked));
        calendarContainer = findViewById(R.id.calendar_container);
        calendar = findViewById(R.id.calendar);
        refreshFields();
    }


    private void refreshFields() {
        editText.setText(editTextValue);
        buttonValue.setText(getString(R.string.bt_clicked).replace("#", buttonClickedTimes.toString()));
        checkBox.setChecked(isCheckBoxChecked);
        onCheck(Checkable.BOX, isCheckBoxChecked);
        switchWidget.setChecked(isSwitchChecked);
        onCheck(Checkable.SWITCH, isSwitchChecked);
        toggleButton.setChecked(isToggleButtonChecked);
        onToggle(isToggleButtonChecked);
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getDefault());
        long millis = cal.getTimeInMillis();
        calendar.setDate(millis);
    }


    private void onButtonClick() {
        buttonClickedTimes ++;
        buttonValue.setText(getString(R.string.bt_clicked).replace("#", buttonClickedTimes.toString()));
    }


    private void onCheck(Checkable widget, Boolean isChecked) {
        String value = isChecked ? getString(R.string.state_on) : getString(R.string.state_off);
        TextView field = widget == Checkable.SWITCH ? switchValue : checkBoxValue;
        field.setText(value);
    }


    private void onToggle(Boolean isChecked) {
        int visibility = isChecked ? View.VISIBLE : View.GONE;
        calendarContainer.setVisibility(visibility);
    }
}
