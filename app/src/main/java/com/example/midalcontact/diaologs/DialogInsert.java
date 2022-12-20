package com.example.midalcontact.diaologs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.midalcontact.R;
import com.example.midalcontact.model.ContactModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class DialogInsert extends DialogFragment {

    private ButtonEvent buttonEvent;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        buttonEvent = (ButtonEvent) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

        View view = LayoutInflater.from(requireContext()).inflate(R.layout.diloag_insert, null);
        TextInputLayout inputName = view.findViewById(R.id.edt_input_name);
        TextInputLayout inputNumber = view.findViewById(R.id.edt_input_phone);
        TextInputLayout inputLabel = view.findViewById(R.id.edt_dialog_label);
        TextInputEditText edtName = view.findViewById(R.id.edt_dialog_name);
        TextInputEditText edtNumber = view.findViewById(R.id.edt_dialog_phone);
        MaterialButton btnSave = view.findViewById(R.id.btn_dialog_save);
        AutoCompleteTextView txtAuto = view.findViewById(R.id.txtAuto);

        String[] list = {"موبایل", "تلفن منزل", "تلفن محل کار"};
        ArrayAdapter adapter = new ArrayAdapter(requireContext(), R.layout.item_phone, list);
        txtAuto.setThreshold(1);
        txtAuto.setAdapter(adapter);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!edtName.getText().toString().isEmpty()
                        && !edtNumber.getText().toString().isEmpty()
                        && !txtAuto.getText().toString().isEmpty()) {

                    String name = edtName.getText().toString();
                    String number = edtNumber.getText().toString();
                    String label = txtAuto.getText().toString();

                    ContactModel contactModel = new ContactModel();
                    contactModel.setContactName(name);
                    contactModel.setContactNumber(number);
                    contactModel.setContactLabel(label);

                    buttonEvent.onInsertClicked(contactModel);
                    dismiss();


                } else {
                    inputName.setError("لطفا اطلاعات را کامل کنید");
                    inputNumber.setError("لطفا اطلاعات را کامل کنید");
                }
            }
        });

        builder.setView(view);
        return builder.create();
    }

    public interface ButtonEvent{
        void onInsertClicked(ContactModel contactModel);
    }

}
