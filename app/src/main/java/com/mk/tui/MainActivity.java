package com.mk.tui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvDisplay;
    private Button btnClear, btnDelete, btnDivide, btnMultiply;
    private Button btnSubtract, btnAdd, btnEquals, btnDot;
    private Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;

    private String currentNumber = "";
    private String operator = "";
    private double firstNumber = 0;
    private double secondNumber = 0;
    private double result = 0;
    private boolean isOperatorPressed = false;
    private boolean isResultShown = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        setClickListeners();
    }

    private void initializeViews() {
        tvDisplay = findViewById(R.id.tv_display);

        btnClear = findViewById(R.id.btn_clear);
        btnDelete = findViewById(R.id.btn_delete);
        btnDivide = findViewById(R.id.btn_divide);
        btnMultiply = findViewById(R.id.btn_multiply);
        btnSubtract = findViewById(R.id.btn_subtract);
        btnAdd = findViewById(R.id.btn_add);
        btnEquals = findViewById(R.id.btn_equals);
        btnDot = findViewById(R.id.btn_dot);

        btn0 = findViewById(R.id.btn_0);
        btn1 = findViewById(R.id.btn_1);
        btn2 = findViewById(R.id.btn_2);
        btn3 = findViewById(R.id.btn_3);
        btn4 = findViewById(R.id.btn_4);
        btn5 = findViewById(R.id.btn_5);
        btn6 = findViewById(R.id.btn_6);
        btn7 = findViewById(R.id.btn_7);
        btn8 = findViewById(R.id.btn_8);
        btn9 = findViewById(R.id.btn_9);
    }

    private void setClickListeners() {
        btnClear.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnDivide.setOnClickListener(this);
        btnMultiply.setOnClickListener(this);
        btnSubtract.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        btnEquals.setOnClickListener(this);
        btnDot.setOnClickListener(this);

        btn0.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_0:
            case R.id.btn_1:
            case R.id.btn_2:
            case R.id.btn_3:
            case R.id.btn_4:
            case R.id.btn_5:
            case R.id.btn_6:
            case R.id.btn_7:
            case R.id.btn_8:
            case R.id.btn_9:
                handleNumberInput(((Button) v).getText().toString());
                break;

            case R.id.btn_dot:
                handleDotInput();
                break;

            case R.id.btn_add:
            case R.id.btn_subtract:
            case R.id.btn_multiply:
            case R.id.btn_divide:
                handleOperatorInput(((Button) v).getText().toString());
                break;

            case R.id.btn_equals:
                handleEqualsInput();
                break;

            case R.id.btn_clear:
                handleClearInput();
                break;

            case R.id.btn_delete:
                handleDeleteInput();
                break;
        }
    }

    private void handleNumberInput(String number) {
        if (isResultShown) {
            currentNumber = "";
            isResultShown = false;
        }

        if (isOperatorPressed) {
            currentNumber = "";
            isOperatorPressed = false;
        }

        currentNumber += number;
        updateDisplay(currentNumber);
    }

    private void handleDotInput() {
        if (isResultShown) {
            currentNumber = "0";
            isResultShown = false;
        }

        if (isOperatorPressed) {
            currentNumber = "0";
            isOperatorPressed = false;
        }

        if (!currentNumber.contains(".")) {
            if (currentNumber.isEmpty()) {
                currentNumber = "0";
            }
            currentNumber += ".";
            updateDisplay(currentNumber);
        }
    }

    private void handleOperatorInput(String op) {
        if (!currentNumber.isEmpty()) {
            if (!operator.isEmpty() && !isOperatorPressed) {
                secondNumber = Double.parseDouble(currentNumber);
                calculate();
                firstNumber = result;
                updateDisplay(formatResult(result));
            } else {
                firstNumber = Double.parseDouble(currentNumber);
            }
        }

        operator = op;
        isOperatorPressed = true;
        isResultShown = false;
    }

    private void handleEqualsInput() {
        if (!operator.isEmpty() && !currentNumber.isEmpty() && !isOperatorPressed) {
            secondNumber = Double.parseDouble(currentNumber);
            calculate();
            updateDisplay(formatResult(result));
            
            operator = "";
            currentNumber = "";
            isResultShown = true;
        }
    }

    private void handleClearInput() {
        currentNumber = "";
        operator = "";
        firstNumber = 0;
        secondNumber = 0;
        result = 0;
        isOperatorPressed = false;
        isResultShown = false;
        updateDisplay("0");
    }

    private void handleDeleteInput() {
        if (!currentNumber.isEmpty() && !isOperatorPressed && !isResultShown) {
            currentNumber = currentNumber.substring(0, currentNumber.length() - 1);
            if (currentNumber.isEmpty()) {
                updateDisplay("0");
            } else {
                updateDisplay(currentNumber);
            }
        }
    }

    private void calculate() {
        switch (operator) {
            case "+":
                result = firstNumber + secondNumber;
                break;
            case "-":
                result = firstNumber - secondNumber;
                break;
            case "×":
                result = firstNumber * secondNumber;
                break;
            case "÷":
                if (secondNumber != 0) {
                    result = firstNumber / secondNumber;
                } else {
                    updateDisplay("Error");
                    return;
                }
                break;
        }
    }

    private String formatResult(double value) {
        DecimalFormat df = new DecimalFormat("#.##########");
        String formatted = df.format(value);
        
        // Remove trailing zeros and decimal point if not needed
        if (formatted.contains(".")) {
            formatted = formatted.replaceAll("0*$", "").replaceAll("\\.$", "");
        }
        
        return formatted;
    }

    private void updateDisplay(String text) {
        tvDisplay.setText(text);
    }
}