package com.mm.planzajec;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import java.util.HashMap;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

public class PickPlanFragment extends Fragment {

    HashMap<String, String> spinners_choose = new HashMap<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_pick_plan, container, false);

        Spinner department_spinner = v.findViewById(R.id.department_spinner);
        Spinner field_of_study_spinner = v.findViewById(R.id.field_of_study_spinner);
        Spinner semester_spinner = v.findViewById(R.id.semester_spinner);
        Spinner group_spinner = v.findViewById(R.id.group_spinner);
        Spinner subgroup_spinner = v.findViewById(R.id.subgroup_spinner);

        ReadJson readJson = new ReadJson(getActivity().getApplicationContext());

        ArrayAdapter<String> department_adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),
                android.R.layout.simple_spinner_item, readJson.getDepartment());
        department_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        department_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                Object item = adapterView.getItemAtPosition(position);
                if (item != null) {
                    String s = item.toString();
                    spinners_choose.put("department", s);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        department_spinner.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        department_adapter,
                        R.layout.spinner_row_nothing_selected,
                        getActivity().getApplicationContext()));


        ArrayAdapter<String> field_of_study_adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item, readJson.getField_of_study());
        field_of_study_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        field_of_study_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                Object item = adapterView.getItemAtPosition(position);
                if (item != null) {
                    String s = item.toString();
                    spinners_choose.put("field", s);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        field_of_study_spinner.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        field_of_study_adapter,
                        R.layout.spinner_row_nothing_selected,
                        getActivity().getApplicationContext()));


        ArrayAdapter<String> semester_adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item, readJson.getSemester());
        semester_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        semester_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                Object item = adapterView.getItemAtPosition(position);
                if (item != null) {
                    String s = item.toString();
                    spinners_choose.put("semester", s);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        semester_spinner.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        semester_adapter,
                        R.layout.spinner_row_nothing_selected,
                        getActivity().getApplicationContext()));

        ArrayAdapter<String> group_adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item, readJson.getGroup());
        group_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        group_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                Object item = adapterView.getItemAtPosition(position);
                if (item != null) {
                    String s = item.toString();
                    spinners_choose.put("group", s);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        group_spinner.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        group_adapter,
                        R.layout.spinner_row_nothing_selected,
                        getActivity().getApplicationContext()));

        ArrayAdapter<String> subgroup_adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item, readJson.getSubgroup());
        subgroup_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subgroup_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                Object item = adapterView.getItemAtPosition(position);
                if (item != null) {
                    String s = item.toString();
                    spinners_choose.put("subgroup", s);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        subgroup_spinner.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        subgroup_adapter,
                        R.layout.spinner_row_nothing_selected,
                        getActivity().getApplicationContext()));

        Button button_add_plan = v.findViewById(R.id.add_plan_button);

        button_add_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {

                SharedPreferences.Editor preference = PickPlanFragment.this.getActivity().getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit();
                Set<String> keySet = spinners_choose.keySet();
                for (String key : keySet) {
                    preference.putString(key, spinners_choose.get(key));
                }
                preference.apply();
                PickPlanFragment.this.getActivity().finish();
            }
        });

        return v;
    }
}