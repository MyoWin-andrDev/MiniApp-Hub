package it.ezzie.myapplist.voucher;

import android.content.Intent;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import java.util.ArrayList;
import java.util.List;

import it.ezzie.myapplist.databinding.ActivityMainVoucherBinding;

public class MainActivityVoucher extends AppCompatActivity {
   private ActivityMainVoucherBinding binding;
   private List<Voucher> voucherList;
   private static final int ADD_CODE = 123;
   private static final int UPDATE_CODE = 124;
   private VoucherAdapter voucherAdapter;
   private Voucher voucher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainVoucherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initRecyclerView();
        initListener();
    }

    /*
    1.Adapter
    2.Layout Manager
    3.List Of Items
     */



    private void initRecyclerView() {
        voucherList = new ArrayList<>();
        voucherAdapter = new VoucherAdapter();
        binding.rvVoucher.setAdapter(voucherAdapter);
        binding.rvVoucher.setLayoutManager(new LinearLayoutManager(this));
//        voucherList = List.of(
//                new Voucher("Eat Breakfast",5000,1),
//                new Voucher("Eat Lunch",7000,1),
//                new Voucher("Eat Dinner",10000,1)
//        );
        voucherAdapter.setVoucherList(voucherList);
        voucherAdapter.setOnClickedListener(new OnVoucherClickListener() {
            @Override
            public void onVoucherClicked(Voucher voucher) {
               Intent intent = new Intent(MainActivityVoucher.this,AddActivity.class);
               intent.putExtra("voucher",voucher);
               startActivityForResult(intent,UPDATE_CODE);
               //voucherAdapter.setVoucherList(voucherList);
            }

            @Override
            public void onVoucherDeleted(Voucher voucher) {
                voucherList.remove(voucher);
                voucherAdapter.setVoucherList(voucherList);
            }
        });

    }

    private void initListener() {
        binding.floatingBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddActivity.class);
            startActivityForResult(intent,ADD_CODE);
        });
    }

    private void updateVoucher(){
        int totalQuality = voucherList.stream().map(voucher -> voucher.quantity()).reduce((v1,v2) -> v1 +v2).get();
        int totalAmount = voucherList.stream().map(voucher -> voucher.amount()).reduce((v1,v2) -> v1+v2).get();
        binding.tvQuantity.setText(String.valueOf(totalQuality));
        binding.tvAmount.setText(String.valueOf(totalAmount));
        voucherAdapter.setVoucherList(voucherList);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_CODE && resultCode == RESULT_OK) {
            Voucher voucher = (Voucher) data.getSerializableExtra("voucher");
            voucherList.add(voucher);
         //   voucherAdapter.setVoucherList(voucherList);
//            Log.d("MyTag",voucher.title()+""+voucher.amount()+""+voucher.quantity());
            updateVoucher();
        }
        else if(requestCode == UPDATE_CODE && resultCode == RESULT_OK){
            Voucher updatedVoucher = (Voucher) data.getSerializableExtra("voucher");
            if(updatedVoucher != null){
                Voucher existingVoucher = voucherList.stream().filter(voucher1 -> voucher1.id() == voucher.id()).findFirst().get();
                int index = voucherList.indexOf(existingVoucher);
                voucherList.set(index,updatedVoucher);
             //   voucherAdapter.setVoucherList(voucherList);
                updateVoucher();
            }
        }
    }
}