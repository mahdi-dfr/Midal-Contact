package com.example.midalcontact.diaologs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.midalcontact.R;
import com.example.midalcontact.model.ContactModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class DialogUpdate extends DialogFragment {

    private ContactModel contactModel;
    private UpdateCallback updateCallback;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        updateCallback = (UpdateCallback) context;
        contactModel = getArguments().getParcelable("contact");
        if (contactModel == null)
            dismiss();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View view = LayoutInflater.from(requireContext()).inflate(R.layout.diloag_update, null);
        builder.setView(view);

        TextInputLayout inputName = view.findViewById(R.id.input_dialog_name);
        TextInputLayout inputNumber = view.findViewById(R.id.input_dialog_phone);
        TextInputLayout inputLabel = view.findViewById(R.id.input_update_label);
        TextInputEditText edtName = view.findViewById(R.id.edt_update_name);
        TextInputEditText edtNumber = view.findViewById(R.id.edt_update_phone);
        MaterialButton btnSave = view.findViewById(R.id.btn_dialog_update);
        AutoCompleteTextView txtAuto = view.findViewById(R.id.txtUpdateAuto);

        String[] list = {"موبایل", "تلفن منزل", "تلفن محل کار"};
        ArrayAdapter adapter = new ArrayAdapter(requireContext(), R.layout.item_phone, list);
        txtAuto.setThreshold(1);
        txtAuto.setAdapter(adapter);

        edtName.setText(contactModel.getContactName());
        edtNumber.setText(contactModel.getContactNumber());
        txtAuto.setText(contactModel.getContactLabel());

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!edtName.getText().toString().isEmpty()
                        && !edtNumber.getText().toString().isEmpty()
                        && !txtAuto.getText().toString().isEmpty()) {

                    String name = edtName.getText().toString();
                    String number = edtNumber.getText().toString();
                    String label = txtAuto.getText().toString();

                    contactModel.setContactName(name);
                    contactModel.setContactNumber(number);
                    contactModel.setContactLabel(label);

                    updateCallback.onUpdateClicked(contactModel);
                    dismiss();


                } else {
                    inputName.setError("لطفا اطلاعات را کامل کنید");
                    inputNumber.setError("لطفا اطلاعات را کامل کنید");
                }
            }
        });

        return builder.create();
    }

    public interface UpdateCallback{
        void onUpdateClicked(ContactModel contact);
    }
}
