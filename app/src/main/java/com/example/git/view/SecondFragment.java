package com.example.git.view;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.git.controller.GithubAPI;
import com.example.git.databinding.FragmentSecondBinding;
import com.example.git.model.Repository;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {


        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //show arraylist of names
        ArrayList<Repository> projects = GithubAPI.getProjects();
        if (projects !=null) {


            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                DateTimeFormatter formatter =
                        DateTimeFormatter.ofLocalizedDateTime( FormatStyle.SHORT )
                                .withLocale( Locale.UK )
                                .withZone( ZoneId.systemDefault() );
                List<String> projectNames = projects.stream().map(r -> r.getName() + "\nSize =  " +r.getSize() +
                                " bytes\nLanguage: " + r.getLanguage() + "\nCreated at: " + formatter.format(r.getCreated_at()) + "\nLast update: " + formatter.format(r.getLast_update()) +
                                "\nWatchers: " + r.getWatchers())
                        .collect(Collectors.toList());
                ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, projectNames);

                binding.projectList.setAdapter(adapter);


            }




        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}