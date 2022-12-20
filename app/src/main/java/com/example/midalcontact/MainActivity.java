package com.example.midalcontact;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.midalcontact.adapter.ContactAdapter;
import com.example.midalcontact.db.DatabaseManager;
import com.example.midalcontact.diaologs.DialogInsert;
import com.example.midalcontact.diaologs.DialogUpdate;
import com.example.midalcontact.model.ContactModel;
import com.example.midalcontact.presenter.ContactPresenter;
import com.example.midalcontact.presenter.MainContract;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ContactAdapter.CallbackEvent,
        MainContract.MainView,
        DialogInsert.ButtonEvent, DialogUpdate.UpdateCallback
{

    private RecyclerView contactRecycler;
    private ExtendedFloatingActionButton fab;
    private EditText edtSearch;
    private ContactAdapter adapter;
    private MainContract.MainPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new ContactPresenter(this, DatabaseManager.getDatabaseManager(this).contactDao());
        initViews();
        presenter.onAttach(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDetach();
    }

    private void initViews() {
        bindData();
    }

    private void bindData() {
        contactRecycler = findViewById(R.id.rc_contact);
        fab = findViewById(R.id.btn_dialog);
        edtSearch = findViewById(R.id.edt_search);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogInsert dialogInsert = new DialogInsert();
                dialogInsert.show(getSupportFragmentManager(), null);
            }
        });

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                presenter.onSearchContact(charSequence.toString());

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void onCall(String number) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            Dexter
                    .withContext(this)
                    .withPermission(Manifest.permission.CALL_PHONE)
                    .withListener(new PermissionListener() {
                        @Override
                        public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            callIntent.setData(Uri.parse("tel:"+number));
                            startActivity(callIntent);
                        }

                        @Override
                        public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                            Toast.makeText(MainActivity.this, "You denied permission", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        }
                    }).check();
        }

    }

    @Override
    public void onEdit(ContactModel contactModel, int pos) {
        DialogUpdate dialogUpdate = new DialogUpdate();
        Bundle bundle = new Bundle();
        bundle.putParcelable("contact",contactModel);
        dialogUpdate.setArguments(bundle);
        dialogUpdate.show(getSupportFragmentManager(), null);
    }

    @Override
    public void onDelete(ContactModel contactModel, int pos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("آیا میخواهید این مخاطب را حذف کنید؟");
        builder.setPositiveButton("بله", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                presenter.onDeleteContact(contactModel, pos);
                Toast.makeText(MainActivity.this, "مخاطب با موفقیت حذف شد!", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("خیر", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();

    }

    @Override
    public void showContact(List<ContactModel> data) {
        adapter = new ContactAdapter(this);
        adapter.addDataToList(new ArrayList(data));
        contactRecycler.setAdapter(adapter);
        contactRecycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    }

    @Override
    public void refreshData(List<ContactModel> list) {
        adapter.addDataToList(new ArrayList<>(list));

    }

    @Override
    public void addNewContact(ContactModel contact) {
        adapter.addItem(contact);
    }

    @Override
    public void deleteContact(ContactModel contact, int pos) {
        adapter.deleteItem(pos);

    }

    @Override
    public void onInsertClicked(ContactModel contactModel) {
        presenter.onAddContact(contactModel);
    }

    @Override
    public void updateContact(ContactModel contact, int pos) {
        adapter.updateItem(pos, contact);
    }

    @Override
    public void onUpdateClicked(ContactModel contact) {
        presenter.onUpdateContact(contact);
    }
}