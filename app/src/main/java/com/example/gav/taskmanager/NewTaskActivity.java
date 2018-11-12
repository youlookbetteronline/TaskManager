package com.example.gav.taskmanager;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class NewTaskActivity extends AppCompatActivity {

    private TextView tvPriority;
    private ImageButton ibAddTask;
    private EditText etTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        initViews();
        addSpannable();
        initListeners();
    }

    private void initViews() {
        tvPriority = findViewById(R.id.tvPriority);
        ibAddTask = findViewById(R.id.ibAddTask);
        etTitle = findViewById(R.id.etTitle);

        ibAddTask.setEnabled(false);
    }

    private void addSpannable() {
        Spannable text = new SpannableString(tvPriority.getText().toString());
        text.setSpan(new ForegroundColorSpan(Color.RED), 0, 1,  Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvPriority.setText(text);
    }

    private void initListeners() {
        ibAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(NewTaskActivity.this, "Add new item success", Toast.LENGTH_SHORT).show();
            }
        });

        etTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ibAddTask.setEnabled(!TextUtils.isEmpty(s));
            }
        });
    }
}
