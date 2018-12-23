package com.example.czettergbor.nagyhf_mobweb.Fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.andexert.library.RippleView;
import com.example.czettergbor.nagyhf_mobweb.R;
import com.example.czettergbor.nagyhf_mobweb.data.Saver;
import com.example.czettergbor.nagyhf_mobweb.data.SpentItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DialogFragmentAddItem extends DialogFragment {
    String TAG = "DialogFragmentAddItem";
    Saver saver;
    ArrayList<SpentItem> items;
    EditText txtName;
    EditText txtValue;
    EditText txtDate;
    RippleView btnKesz;
    SpentItem mItem;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_additem, container, false);
        saver = new Saver(getContext());
        myCalendar = Calendar.getInstance();
        txtName = view.findViewById(R.id.txtItemName);
        txtValue = view.findViewById(R.id.txtAmount);
        txtDate = view.findViewById(R.id.txtItemDate);
        btnKesz = view.findViewById(R.id.btnKesz);

        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };
        txtDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                Log.d(TAG, myCalendar.getTime() + "");
            }
        });
        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        items = saver.loadArrayListItems();
        if (mItem != null) {
            txtName.setText(mItem.getItemName());
            txtValue.setText(mItem.getPrice() + "");
            txtDate.setText(dateConverter(mItem.getDate()));
            if (txtValue.getText().toString() != "" && Integer.parseInt(txtValue.getText().toString()) > 0)
                txtValue.setTextColor(getResources().getColor(R.color.colorDarkGreen));
            else
                txtValue.setTextColor(Color.RED);
        }
        txtName.requestFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        btnKesz.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {

                if (errorCheck())
                    return;

                if (mItem != null) {
                    mItem.setName(txtName.getText().toString());
                    mItem.setPrice(Integer.parseInt(txtValue.getText().toString()));
                    mItem.setDate(dateFromString(txtDate.getText().toString()));
                    items.add(mItem);
                } else {
                    items.add(new SpentItem(txtName.getText().toString(), Integer.parseInt(txtValue.getText().toString()), false, dateFromString(txtDate.getText().toString())));
                }
                saver.saveArrayList("items", items);
                Log.d(TAG, items.size() + "");
                Toast.makeText(getContext(), getResources().getText(R.string.add), Toast.LENGTH_SHORT).show();

                closeKeyboard();

                dismiss();
            }
        });
        txtValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    int num = Integer.parseInt(txtValue.getText().toString());
                    if (num > 0)
                        txtValue.setTextColor(getResources().getColor(R.color.colorDarkGreen));
                    else
                        txtValue.setTextColor(Color.RED);
                } catch (NumberFormatException nf) {
                }

            }


            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        return view;
    }

    public void addItem(SpentItem item) {
        mItem = item;
    }

    private void updateLabel() {

        Log.d("MyCalendar", "" + myCalendar.getTime());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        txtDate.setText(dateFormat.format(myCalendar.getTime()));
    }

    public String dateConverter(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return year+"."+month+"."+day;
    }

    public Date dateFromString(String s) {
        Log.d("dateFromString", s);
        String[] split = s.split("\\.");
        for (int i = 0; i < split.length; i++)
            if (split[i].charAt(0) == '0') {
                split[i] = split[i].substring(1);
            }
        return new Date(Integer.parseInt(split[0])-1900, Integer.parseInt(split[1]), Integer.parseInt(split[2]));
    }

    public ArrayList<SpentItem> getItems() {
        return items;
    }

    private DialogInterface.OnDismissListener onDismissListener;

    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    public void closeKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(txtName.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(txtDate.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(txtValue.getWindowToken(), 0);
    }

    public boolean errorCheck() {
        if (txtName.getText().toString().isEmpty()) {
            txtName.requestFocus();
            txtName.setError(getResources().getString(R.string.error_empty));
            return true;
        }
        if (txtValue.getText().toString().isEmpty()) {
            txtValue.requestFocus();
            txtValue.setError(getResources().getString(R.string.error_empty));
            return true;
        }
        if (txtDate.getText().toString().isEmpty()) {
            txtDate.requestFocus();
            txtDate.setError(getResources().getString(R.string.error_empty));
            return true;
        }
        return false;

    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (onDismissListener != null) {
            onDismissListener.onDismiss(dialog);
        }
    }

}


