package com.danazone.autosharesms;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class AddActivity extends AppCompatActivity {
    private static final int RESULT_PICK_CONTACT = 100;

    private Button mBtnSubmit, mBtnContact;
    private RecyclerView mRecycleView;
    private EditText mEdtName, mEdtPhone;

    private PhoneAdapter mAdapter;
    private List<Phone> phone = new ArrayList<>();
    private DBManager dbManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        getSupportActionBar().hide();

        dbManager = new DBManager(getBaseContext());
        phone = dbManager.getAllPhone();

        mBtnSubmit = (Button) findViewById(R.id.mBtnSubmit);
        mBtnContact = (Button) findViewById(R.id.mBtnContact);
        mRecycleView = (RecyclerView) findViewById(R.id.mRecycleView);
        mEdtName = (EditText) findViewById(R.id.mEdtName);
        mEdtPhone = (EditText) findViewById(R.id.mEdtPhone);

        setUpAdapter();
    }

    private void setUpAdapter() {
        final Phone phones = new Phone();
        RecyclerViewUtils.Create().setUpVertical(this, mRecycleView);

        mAdapter = new PhoneAdapter(this, phone, new PhoneAdapter.OnHistoryClickListener() {
            @Override
            public void onClickItem(int position) {
                Toast.makeText(AddActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                dbManager.deletePhone(phone.get(position));
                phone.remove(position);
                mAdapter.notifyDataSetChanged();

            }
        });
        mRecycleView.setAdapter(mAdapter);

        mBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mEdtPhone.getText().toString().trim().isEmpty() || mEdtName.getText().toString().trim().isEmpty()) {
                    Toast.makeText(AddActivity.this, "Vui lòng nhập tên và số điện thoại", Toast.LENGTH_SHORT).show();
                    return;
                }
                phones.setPhone(mEdtPhone.getText().toString());
                phones.setName(mEdtName.getText().toString());
                dbManager.addPhone(phones);
                //phone.add(phones);
                mAdapter.notifyDataSetChanged();


                startActivity(new Intent(AddActivity.this, AddActivity.class));
                finish();
            }
        });

        mBtnContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(contactPickerIntent, RESULT_PICK_CONTACT);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // check whether the result is ok
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RESULT_PICK_CONTACT:
                    contactPicked(data);
                    break;
            }
        } else {
            Log.e("MainActivity", "Failed to pick contact");
        }
    }

    /**
     * Query the Uri and read contact details. Handle the picked contact data.
     *
     * @param data
     */
    private void contactPicked(Intent data) {
        Cursor cursor = null;
        try {
            String phoneNo = null;
            String name = null;
            // getData() method will have the Content Uri of the selected contact
            Uri uri = data.getData();
            //Query the content uri
            cursor = getContentResolver().query(uri, null, null, null, null);
            cursor.moveToFirst();
            // column index of the phone number
            int phoneIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            // column index of the contact name
            int nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            phoneNo = cursor.getString(phoneIndex);
            name = cursor.getString(nameIndex);
            // Set the value to the textviews
            mEdtName.setText(name);
            mEdtPhone.setText(phoneNo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
