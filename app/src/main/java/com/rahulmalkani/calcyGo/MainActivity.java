package com.rahulmalkani.calcyGo;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.mariuszgromada.math.mxparser.Expression;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private Button button1, button2, button3, button4, button5, button6, button7, button8, button9, button0,
            buttonDiv, buttonMul, buttonAdd, buttonSub, buttonEquals, buttonDot, buttonCorrection, buttonClear, buttonSquare, buttonSIgnUnsign;

    private TextView expression, result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState!=null){
            expression.setText(savedInstanceState.getString("expression"));
        }

        setTitle("Calcy GO");

        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        button6 = findViewById(R.id.button6);
        button7 = findViewById(R.id.button7);
        button8 = findViewById(R.id.button8);
        button9 = findViewById(R.id.button9);
        buttonEquals = findViewById(R.id.buttonEquals);
        buttonDiv = findViewById(R.id.buttonDiv);
        buttonMul = findViewById(R.id.buttonMul);
        buttonSub = findViewById(R.id.buttonSub);
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonDot = findViewById(R.id.buttonDot);

        buttonCorrection = findViewById(R.id.buttonCorrection);
        buttonClear = findViewById(R.id.buttonClear);
        buttonSquare = findViewById(R.id.buttonSquare);
        buttonSIgnUnsign = findViewById(R.id.buttonSignUnsign);


        expression = findViewById(R.id.expression);
        result = findViewById(R.id.result);

        ArrayList<Button> numbers = new ArrayList<Button>(Arrays.asList(button1, button2, button3, button4, button5, button6, button7, button8, button9, button0, buttonDot));
        ArrayList<Button> operations = new ArrayList<>(Arrays.asList(buttonDiv, buttonMul, buttonAdd, buttonSub));

        for (Button b : numbers) {
            final Button temp = b;
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (expression.getText().equals("0"))
                        expression.setText(temp.getText().toString());
                    else
                        expression.setText(expression.getText() + temp.getText().toString());

                }
            });
        }

        for (Button b : operations) {
            final Button temp = b;
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    char c = expression.getText().toString().charAt(expression.length() - 1);
                    if (c == '+' || c == '-' || c == '×' || c == '÷') {
                        expression.setText(expression.getText().toString().substring(0, expression.length() - 1) + temp.getText());
                    } else {
                        expression.setText(expression.getText().toString() + temp.getText());
                    }
                }
            });
        }

        buttonCorrection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = expression.getText().toString();

                if (str != null) {
                    str = str.substring(0, str.length() - 1);

                    if (str.length() > 0) {
                        expression.setText(str);
                        return;
                    }
                    expression.setText("0");
                }
            }
        });

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expression.setText("0");
                result.setText("");
            }
        });

        buttonSIgnUnsign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = expression.getText().toString();
                if (str.charAt(0) == '0')
                    return;
                if (str.charAt(0) != '-') {
                    expression.setText("-" + str);
                } else {
                    expression.setText(str.substring(1));
                }
            }
        });

        buttonSquare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Long val = Long.parseLong(expression.getText().toString());
                result.setText(String.valueOf(val * val));
            }
        });

        buttonEquals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = expression.getText().toString();
                char c = str.charAt(str.length() - 1);
                if (c == '+' || c == '-' || c == '×' || c == '÷') {
                    str = str.substring(0, str.length() - 1);
                }
                char[] chars = str.toCharArray();

                for (int i = 0; i < str.length(); i++) {
                    if (chars[i] == '÷') {
                        chars[i] = '/';
                    } else if (chars[i] == '×') {
                        chars[i] = '*';
                    }
                }

                Expression e = new Expression(new String(chars));
                NumberFormat nf = new DecimalFormat("##.####");
                result.setText(nf.format(e.calculate()));
            }
        });

        result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expression.setText(result.getText());
                result.setText("");
            }
        });

    }

    @Override
    public void onBackPressed() {
        final AlertDialog.Builder alertBox = new AlertDialog.Builder(this)
                .setTitle("Alert!")
                .setMessage("Do you want to exit this application?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

        alertBox.show();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("expression",expression.getText().toString());
    }
}
