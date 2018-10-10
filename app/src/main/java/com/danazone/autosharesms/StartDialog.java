package com.danazone.autosharesms;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class StartDialog extends BaseDialog implements View.OnClickListener {

    public StartDialog(Context context) {
        super(context);

    }

    private EditText mEdtName;

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        TextView mTvCall = (TextView) findViewById(R.id.mTvSubmit);
        mEdtName = (EditText) findViewById(R.id.mEdtName);

        mTvCall.setOnClickListener(this);

    }

    @Override
    protected int getLayout() {
        return R.layout.dialog_start;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.mTvSubmit:
                if (mEdtName.getText().toString().trim().equals("")) {
                    Toast.makeText(getContext(), "Vui lòng nhập tên bộ lọc", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!SessionManager.getInstance().getKeySaveName().equals("")) {
                    SessionManager.getInstance().updateSaveName(mEdtName.getText().toString());
                } else {
                    SessionManager.getInstance().setKeyName(mEdtName.getText().toString());
                }

                dismiss();
                break;
        }
    }
}

