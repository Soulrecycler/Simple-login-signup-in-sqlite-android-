package com.example.androidtest.ui.home;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.androidtest.DbManager;
import com.example.androidtest.Home2Activity;
import com.example.androidtest.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    DbManager db;
    private HomeViewModel homeViewModel;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        db = new DbManager(getActivity());
//    final TextView textView = root.findViewById(R.id.text_home);
//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
//        View rootView=inflater.inflate(R.layout.fragment_home,container,false);
//        //Cursor hello = db.getlistContents();
//        ListView lv= (ListView) rootView.findViewById(R.id.listView);
//        ArrayAdapter adapter=new ArrayAdapter(this.getActivity(),android.R.layout.simple_list_item_1,crime);
//        lv.setAdapter(adapter);
//
//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getActivity(),crime[position],Toast.LENGTH_SHORT).show();
//            }
//        });
        ListView listView = root.findViewById(R.id.listView);
        ArrayList<String> theList =  new ArrayList<>();
        Cursor data = db.getAllData();
        if (data.getCount() == 0 )
        {
            Toast.makeText(getActivity(),"The DB is empty",Toast.LENGTH_SHORT).show();
        }else {
            while (data.moveToNext()) {
                theList.add(data.getString(1));
                ArrayAdapter adapter=new ArrayAdapter(this.getActivity(),android.R.layout.simple_list_item_1,theList);
//                ListAdapter listAdapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,theList);
                listView.setAdapter(adapter);

            }

        }
        return root;
    }
}
