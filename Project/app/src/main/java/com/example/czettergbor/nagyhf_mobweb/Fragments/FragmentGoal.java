package com.example.czettergbor.nagyhf_mobweb.Fragments;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.akaita.android.morphview.MorphView;
import com.example.czettergbor.nagyhf_mobweb.R;
import com.example.czettergbor.nagyhf_mobweb.data.Account;
import com.example.czettergbor.nagyhf_mobweb.data.Goal;
import com.example.czettergbor.nagyhf_mobweb.data.Saver;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FragmentGoal extends Fragment {

    MorphView btnEdit;
    TextView txtDaysLeft;
    TextView txtMoneyLeft;
    EditText txtGoalDate;
    EditText txtGoalBalance;
    TextView txtMoneyPerDay;
    Account myCard;
    Calendar myCalendar;
    boolean editing;
    Saver saver;
    Goal myGoal;
    DatePickerDialog.OnDateSetListener date;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_goal, container, false);
        saver = new Saver(getContext());
        myCalendar = Calendar.getInstance();
        myCard = saver.loadCreditCard();
        btnEdit = view.findViewById(R.id.btnEdit);
        txtDaysLeft = view.findViewById(R.id.txtDaysLeft);
        txtGoalBalance = view.findViewById(R.id.txtGoalBalance);
        txtGoalDate = view.findViewById(R.id.txtGoalDate);
        txtMoneyLeft = view.findViewById(R.id.txtMoneyLeft);
        txtMoneyPerDay = view.findViewById(R.id.txtMoneyPerDay);
        final Drawable originalDrawable = txtGoalDate.getBackground();
        editing = false;
        setNotEditing();


        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                myCalendar.set(Calendar.YEAR, i);
                myCalendar.set(Calendar.MONTH, i1);
                myCalendar.set(Calendar.DAY_OF_MONTH, i2);
                updateLabel();

            }
        };

        myGoal = myCard.getGoal();
        if (myGoal == null) {
            txtMoneyLeft.setText(getResources().getText(R.string.cel_beallitasa));
            myGoal = new Goal();
        } else {
            configureTexts();
        }

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editing = !editing;
                if (editing) {
                    btnEdit.morph();
                    txtGoalDate.setClickable(true);
                    txtGoalBalance.setInputType(InputType.TYPE_CLASS_NUMBER);
                    txtGoalDate.setInputType(InputType.TYPE_CLASS_TEXT);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        txtGoalDate.setBackground(originalDrawable);
                        txtGoalBalance.setBackground(originalDrawable);
                    }

                    txtGoalDate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            new DatePickerDialog(getContext(), date, myCalendar
                                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                        }
                    });
                } else {
                    closeKeyboard();
                    setNotEditing();

                    if (!txtGoalBalance.getText().toString().isEmpty() && !txtGoalDate.getText().toString().isEmpty()) {
                        myGoal.setBalanceGoal(Integer.parseInt(txtGoalBalance.getText().toString()));
                        myGoal.setGoalDate(dateFromString(txtGoalDate.getText().toString()));
                        configureTexts();
                    }

                    saver.saveCreditCard(myCard);
                }
            }
        });

        return view;
    }

    public void configureTexts() {
        txtGoalDate.setText(dateConverter(myGoal.getGoalDate()));
        txtGoalBalance.setText("" + myGoal.getBalanceGoal());
        txtMoneyLeft.setText(getString(R.string.hufcelig, myGoal.getBalanceGoal() - myCard.getBalance()));
        txtDaysLeft.setText(getString(R.string.hatranap, dayDifference(myGoal.getGoalDate())));
        if (dayDifference(myGoal.getGoalDate()) != 0)
            txtMoneyPerDay.setText(getString(R.string.hufpernap, (myGoal.getBalanceGoal() - myCard.getBalance()) / dayDifference(myGoal.getGoalDate())));
        else
            txtMoneyPerDay.setText(getString(R.string.hufpernap, (myGoal.getBalanceGoal() - myCard.getBalance())));
    }

    public Date dateFromString(String s) {
        String[] split = s.split("\\.");
        for (int i = 0; i < split.length; i++)
            if (split[i].charAt(0) == '0') {
                split[i] = split[i].substring(1);
            }
        return new Date(Integer.parseInt(split[0]) - 1900, Integer.parseInt(split[1]) - 1, Integer.parseInt(split[2]));
    }

    public String dateConverter(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return year + "." + month + "." + day;
    }

    public int dayDifference(Date date) {
        Calendar today = Calendar.getInstance();
        long todayMilis = today.getTimeInMillis();
        long dateMilis = date.getTime();
        long result = dateMilis - todayMilis;
        result = result / (1000 * 60 * 60 * 24);
        return (int) result+1;
    }

    private void updateLabel() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        txtGoalDate.setText(dateFormat.format(myCalendar.getTime()));
    }

    private void setNotEditing() {
        btnEdit.morph();
        txtGoalBalance.setInputType(InputType.TYPE_NULL);
        txtGoalDate.setInputType(InputType.TYPE_NULL);
        txtGoalDate.setClickable(false);
        txtGoalDate.setOnClickListener(null);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            txtGoalBalance.setBackgroundColor(Color.TRANSPARENT);
            txtGoalDate.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    public void closeKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(txtGoalDate.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(txtGoalBalance.getWindowToken(), 0);
    }
}
