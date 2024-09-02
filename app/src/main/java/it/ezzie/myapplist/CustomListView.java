package it.ezzie.myapplist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import it.ezzie.myapplist.databinding.CustomListViewBinding;

public class CustomListView extends BaseAdapter {

    private Context context;
    private List<AppList> appList;
    private CustomListViewBinding customListViewBinding;

    CustomListView(Context context,List<AppList> appList){
        this.context = context;
        this.appList = appList;
    }

    @Override
    public int getCount() {
        return appList.size();
    }

    @Override
    public Object getItem(int position) {
        return appList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
           // convertView = customListViewBinding.getRoot();
            customListViewBinding = CustomListViewBinding.inflate(layoutInflater);
            convertView = customListViewBinding.getRoot();
            AppList currentApp = appList.get(position);
            customListViewBinding.imgView.setImageResource(currentApp.appImg());
            customListViewBinding.txtTitle.setText(currentApp.appTitle());
            customListViewBinding.txtType.setText(currentApp.appTitle());
        }
        return convertView;
    }
}
