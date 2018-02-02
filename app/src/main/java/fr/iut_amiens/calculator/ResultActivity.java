package fr.iut_amiens.calculator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.MathContext;

public class ResultActivity extends Activity {

    public static final String EXTRA_NBA = "fr.iut_amiens.calculator.EXTRA_NBA";
    public static final String EXTRA_NBB = "fr.iut_amiens.calculator.EXTRA_NBB";
    public static final String EXTRA_OPE = "fr.iut_amiens.calculator.EXTRA_OPE";

    private TextView resultField;

    private TextView operationField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        resultField = findViewById(R.id.resultTextView);
        operationField = findViewById(R.id.operationTextView);

        displayOperation();
        displayResult();
    }

    private void displayOperation() {
        operationField.setText(getOperationString());
    }

    public String getOperationString() {
        return String.format("%s %s %s", getNumberA(), getText(getOperation()), getNumberB());
    }

    public void displayResult() {
        try {
            resultField.setText(getResult().toString());
        } catch (ArithmeticException e) {
            resultField.setText("ERROR");
        }
    }

    private BigDecimal getNumberB() {
        return (BigDecimal) getIntent().getSerializableExtra(EXTRA_NBB);
    }

    private BigDecimal getNumberA() {
        return (BigDecimal) getIntent().getSerializableExtra(EXTRA_NBA);
    }

    private int getOperation() {
        return getIntent().getIntExtra(EXTRA_OPE, -1);
    }

    public BigDecimal getResult() {
        switch (getOperation()) {
            case R.string.add:
                return getNumberA().add(getNumberB());
            case R.string.minus:
                return getNumberA().min(getNumberB());
            case R.string.multiply:
                return getNumberA().multiply(getNumberB(), MathContext.DECIMAL64);
            case R.string.divide:
                return getNumberA().divide(getNumberB(), MathContext.DECIMAL64);
        }
        throw new IllegalArgumentException("unsupported operation");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_result, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.share) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, String.format("%s = %s", getOperationString(), getResult()));
            sendIntent.setType("text/plain");
            startActivity(Intent.createChooser(sendIntent, "Share your operation!"));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
