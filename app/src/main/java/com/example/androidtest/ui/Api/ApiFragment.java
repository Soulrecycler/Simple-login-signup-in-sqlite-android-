package com.example.androidtest.ui.Api;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.androidtest.R;
import com.example.androidtest.displayApi;

public class ApiFragment extends Fragment {
    Button GithubViewUserButton;
    EditText InputGithubUser;
    private Intent i;
    private NotificationsViewModel notificationsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        GithubViewUserButton=root.findViewById(R.id.gitHubButton);
//        InputGithubUser=root.findViewById(R.id.githubEditText);

        GithubViewUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), displayApi.class);
                startActivity(intent);
            }
        });
        return root;
    }






}
