package it.ezzie.myapplist.voucher;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import it.ezzie.myapplist.R;

public class VoucherAdapter extends RecyclerView.Adapter<VoucherAdapter.VoucherViewHolder> {
    private List<Voucher> voucherList = new ArrayList<>();
    private OnVoucherClickListener listener;
    @NonNull
    @Override
    public VoucherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.adapter_voucher,parent,false);
        return new VoucherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VoucherViewHolder holder, int position) {
        Voucher voucher = voucherList.get(position);
        holder.txtTitle.setText(voucher.title());
        holder.txtQuality.setText("" + voucher.quantity());
        holder.txtAmount.setText("" + voucher.amount());
        holder.itemView.setOnClickListener(v -> {
            listener.onVoucherClicked(voucher);
        });
        holder.itemView.setOnLongClickListener(v -> {
            listener.onVoucherDeleted(voucher);
            return true;
        });
    }

    public void setVoucherList(List<Voucher> voucherList) {
        this.voucherList = voucherList;
        this.notifyDataSetChanged();
    }

    public void setOnClickedListener(OnVoucherClickListener listener){
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return voucherList.size();
    }

    class VoucherViewHolder extends RecyclerView.ViewHolder{
        TextView txtTitle , txtQuality , txtAmount;
        public VoucherViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtQuality = itemView.findViewById(R.id.txtQuantity);
            txtAmount = itemView.findViewById(R.id.txtAmount);
        }
    }
}
