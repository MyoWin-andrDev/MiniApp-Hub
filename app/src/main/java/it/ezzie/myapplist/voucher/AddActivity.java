package it.ezzie.myapplist.voucher;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;
import it.ezzie.myapplist.databinding.ActivityAddVoucherBinding;

public class AddActivity extends AppCompatActivity {
    private ActivityAddVoucherBinding binding;
    private Voucher voucher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityAddVoucherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initUI();
        initListener();
    }

    private void initUI() {
        Intent intent = getIntent();
        if( intent != null) {
            Voucher voucher = (Voucher) intent.getSerializableExtra("voucher");
            if(voucher != null) {
                binding.etTitle.setText(voucher.title());
                binding.etQuantity.setText(String.valueOf(voucher.quantity()));
                binding.etAmount.setText(String.valueOf(voucher.amount()));
                binding.addBtn.setText("Update");
            }

        }
    }

    private void initListener() {
        binding.cancelBtn.setOnClickListener(v -> {
            finish();
        });

        binding.addBtn.setOnClickListener(v -> {
            if (voucher != null) {
                //Update Voucher
                try {
                    String title = binding.etTitle.getText().toString();
                    int quantity = Integer.parseInt(binding.etQuantity.getText().toString());
                    int amount = Integer.parseInt(binding.etAmount.getText().toString());
                    if (!title.isEmpty() && quantity > 0 && amount > 0) {
                        Voucher newVoucher = new Voucher(voucher.id(), title, quantity, amount);
                        if ( this.voucher.equals(newVoucher)) {
                            Toast.makeText(this, "No info updated", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Intent intent = new Intent();
                        intent.putExtra("voucher", newVoucher);
                        setResult(RESULT_OK, intent);
                        finish();
                    } else {
                        Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                }
                }
                else{
                    try {
                        String title = binding.etTitle.getText().toString();
                        int quantity = Integer.parseInt(binding.etQuantity.getText().toString());
                        int amount = Integer.parseInt(binding.etAmount.getText().toString());
                        if (!title.isEmpty() && quantity > 0 && amount > 0) {
                            Voucher voucher = new Voucher(new Random().nextInt(), title, quantity, amount);
                            Intent intent = new Intent();
                            intent.putExtra("voucher", voucher);
                            setResult(RESULT_OK, intent);
                            finish();

                        } else {
                            Toast.makeText(this, "Invalid Action", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }
        });
    }
}
