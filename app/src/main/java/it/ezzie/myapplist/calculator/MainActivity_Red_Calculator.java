package it.ezzie.myapplist.calculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import it.ezzie.myapplist.databinding.ActivityRedCalculatorBinding;

public class MainActivity_Red_Calculator extends AppCompatActivity {

    private ActivityRedCalculatorBinding redBinding;

    List<String> operatorList = new ArrayList<>();
    List<Integer> intList = new ArrayList<>();
    List<Double> doubleList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        redBinding = ActivityRedCalculatorBinding.inflate(getLayoutInflater());
        setContentView(redBinding.getRoot());

        redBinding.actBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity_Red_Calculator.this , MainActivity_Blue_Calculator.class);
                startActivity(i);
            }
        });

    }

    //On Number Clicked Btn
    public void onNumberClicked(View view){
        Button numberBtn = (Button) view;
        String text = numberBtn.getText().toString();
        String input = redBinding.etCalculate.getText().toString();
        if(input.equals("0") || input.equals("00")){
            redBinding.etCalculate.setText(text);
        }
        else{
            redBinding.etCalculate.append(text);
        }
    }

    public void onOperatorClicked(View view){
        Button operatorBtn = (Button) view;
        String operator = operatorBtn.getText().toString();

        //Adding the current value to the NumList
        if(operator.equals("=")){
            try {
                String input = redBinding.etCalculate.getText().toString();
                if(input.isEmpty()){
                    Toast.makeText(this, "Enter a number", Toast.LENGTH_SHORT).show();
                    return;
                }
                int current = Integer.parseInt(input);
                intList.add(current);
                int result = evaluateExpression(intList, operatorList);

                //Update UI
                redBinding.operation.setText("");
                redBinding.etCalculate.setText(String.valueOf(result));//For the next input

                //Clearing list for the next calculation
                intList.clear();
                operatorList.clear();
            } catch (NumberFormatException e){
                Toast.makeText(this, "Invalid Action", Toast.LENGTH_SHORT).show();

            } catch (Exception e){
                Toast.makeText(this, "Error In Calculation", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            String input = redBinding.etCalculate.getText().toString();
            if(input.isEmpty()){
                Toast.makeText(this, "Enter a number", Toast.LENGTH_SHORT).show();
                return;
            }
            //Store the current value and operator
            int current = Integer.parseInt(redBinding.etCalculate.getText().toString());
            intList.add(current);
            operatorList.add(operator);

            //Update the operation display
            StringBuilder strBuilder = new StringBuilder();
            for(int i= 0 ; i < intList.size() ; i++){
                strBuilder.append(intList.get(i));
                if( i < operatorList.size()){
                    strBuilder.append(" ").append(operatorList.get(i)).append(" ");
                }
            }
            redBinding.operation.setText(strBuilder);
            redBinding.etCalculate.setText("");

        }
    }


    public int evaluateExpression(List<Integer> intList, List<String> operatorList){
        //Handle Multiplication and Division first
        for(int i = 0 ; i < operatorList.size(); i++){
            String opStr = operatorList.get(i);
            if(opStr.equals("*") || opStr.equals("/")){
                int leftValue = intList.get(i);
                int rightValue = intList.get(i+1);

                if (opStr.equals("/") && rightValue == 0) {
                    throw new ArithmeticException("Cannot divide by zero");
                }
                //Calculate result based on the operator
                int result = opStr.equals("*")? leftValue * rightValue : leftValue / rightValue ;

                //Replace the intList and operator list
                intList.set(i,result);

                intList.remove(i+1);
                operatorList.remove(i);
                i--;//Adjust index after removal
            }
        }

        //Now handle addition and subtraction
        int result = intList.get(0);
        for(int i=0 ; i < operatorList.size() ; i++){
            String opStr = operatorList.get(i);
            int nextInput = intList.get(i+1);
            if(opStr.equals("+")){
                result += nextInput;
            }
            else if (opStr.equals("-")){
                result -= nextInput;
            }
        }
        return result;
    }

    public void onClearClicked(View view) {

        redBinding.etCalculate.setText("");
    }

    public void onBackSpaceClicked (View view ){
        String current = redBinding.etCalculate.getText().toString();
        if(!current.isEmpty()){
            String newText = current.substring(0, current.length() -1);
            redBinding.etCalculate.setText(newText);
        }
    }
}