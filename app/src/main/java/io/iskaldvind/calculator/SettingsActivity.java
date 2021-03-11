package io.iskaldvind.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class SettingsActivity extends AppCompatActivity {
    
    private int theme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Intent intent = getIntent();
        theme = intent.getIntExtra(MainActivity.THEME, R.style.AppThemeLight);
        
        setTheme(theme);
        setContentView(R.layout.activity_settings);

        SwitchMaterial switchMaterial = findViewById(R.id.theme_switch);
        switchMaterial.setChecked(theme == R.style.AppThemeDark);
        switchMaterial.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked) {
                theme = R.style.AppThemeDark;
            } else {
                theme = R.style.AppThemeLight;
            }
        });

        MaterialButton okButton = findViewById(R.id.ok_button);
        okButton.setOnClickListener((view) -> {
            Intent result = new Intent();
            result.putExtra(MainActivity.THEME, theme);
            setResult(RESULT_OK, result);
            finish();
        });
    }
}
