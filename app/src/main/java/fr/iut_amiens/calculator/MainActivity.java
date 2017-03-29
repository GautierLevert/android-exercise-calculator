package fr.iut_amiens.calculator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.math.BigDecimal;
import java.math.MathContext;

public class MainActivity extends Activity implements View.OnClickListener {

    private EditText editTextA;

    private EditText editTextB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.buttonPlus).setOnClickListener(this);
        findViewById(R.id.buttonMinus).setOnClickListener(this);
        findViewById(R.id.buttonTime).setOnClickListener(this);
        findViewById(R.id.buttonDivide).setOnClickListener(this);

        editTextA = (EditText) findViewById(R.id.editTextA);
        editTextB = (EditText) findViewById(R.id.editTextB);
    }

    public BigDecimal getNumberA() {
        try {
            return new BigDecimal(editTextA.getText().toString(), MathContext.DECIMAL64);
        } catch (Exception e) {
            Log.e("NbrA", "error reading value", e);
            return BigDecimal.ZERO;
        }
    }

    public BigDecimal getNumberB() {
        try {
            return new BigDecimal(editTextB.getText().toString(), MathContext.DECIMAL64);
        } catch (Exception e) {
            Log.e("NbrB", "error reading value", e);
            return BigDecimal.ZERO;
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra(ResultActivity.EXTRA_NBA, getNumberA());
        intent.putExtra(ResultActivity.EXTRA_NBB, getNumberB());
        intent.putExtra(ResultActivity.EXTRA_OPE, getOperation(v));
        startActivity(intent);
    }

    private int getOperation(View button) {
        switch (button.getId()) {
            case R.id.buttonPlus:
                return R.string.add;
            case R.id.buttonMinus:
                return R.string.minus;
            case R.id.buttonTime:
                return R.string.multiply;
            case R.id.buttonDivide:
                return R.string.divide;
        }
        throw new IllegalArgumentException("given view is not a valid button");
    }
}
