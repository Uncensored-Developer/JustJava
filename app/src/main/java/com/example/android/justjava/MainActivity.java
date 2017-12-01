package com.example.android.justjava;

import java.text.NumberFormat;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.attr.x;
import static android.media.CamcorderProfile.get;


public class MainActivity extends AppCompatActivity {

    private int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //calculates the price of order @return total price
    public int calculatePrice(boolean addChocolate, boolean addWhippedCream){
        int basePrice = 5;

        if (addChocolate) {
            basePrice += 2;
        }

        if (addWhippedCream) {
            basePrice += 1;
        }
        return quantity * basePrice;
    }

    /**
     * Create summary of the order
     * @param totalPrice
     * @return text summary
     */
    public String createOrderSummary(int totalPrice, boolean addWhippedCream
    , boolean addChocolate, String name){
        String summaryMessage = getString(R.string.order_summary_name, name);
        summaryMessage += "\nAdd Whipped cream: " + addWhippedCream;
        summaryMessage += "\nAdd Chocolate: " + addChocolate;
        summaryMessage += "\nQuantity: " + quantity;
        summaryMessage += "\nTotal: $" + totalPrice;
        summaryMessage += "\n" + getString(R.string.thank_you);
        return summaryMessage;
    }

    //this method is called when the submit button is clicked
    public void submitOrder(View view){
        //Find the user's name
        EditText nameField = (EditText) findViewById(R.id.id_edit_text);
        String name = nameField.getText().toString();

        //Figure out if the user wants chocolate topping
        CheckBox chocolateTopping = (CheckBox)findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateTopping.isChecked();

        //Figure out if the user wants whipped cream topping
        CheckBox whippedCreamCheckBox = (CheckBox)findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        int price = calculatePrice(hasChocolate, hasWhippedCream);
        String priceMessage = createOrderSummary(price, hasWhippedCream, hasChocolate, name);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Java order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    //this method is called when the increment button is clicked
    public void increment(View view){
        if (quantity == 100) {
            //show an error message as a toast
            Toast.makeText(this, "You cannot have more than 100 coffes", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity++;
        displayQuantity(quantity);
    }

    //this method is called when the decrement button is clicked
    public void decrement(View view){
        if (quantity == 1) {
            //show an error message as a toast
            Toast.makeText(this, "You cannot have less than one coffe", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity--;
        displayQuantity(quantity);
    }

    //this method displays the given quantity value on the screen
    public void displayQuantity(int number){
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

}
