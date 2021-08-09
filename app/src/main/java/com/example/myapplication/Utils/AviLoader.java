package com.example.myapplication.Utils;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.example.myapplication.R;

public class AviLoader extends Dialog {


    public AviLoader(@NonNull Context context) {
        super(context, R.style.TransparentProgressDialog);
        setContentView(R.layout.progress_dialog);
        WindowManager.LayoutParams wlmp = getWindow().getAttributes();
        wlmp.gravity = Gravity.CENTER_HORIZONTAL;
        getWindow().setAttributes(wlmp);
        setTitle(null);
        setCancelable(false);
        setOnCancelListener(null);

    }

    @Override
    public void show() {
        super.show();
    }

}
