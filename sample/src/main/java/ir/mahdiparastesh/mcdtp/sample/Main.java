package ir.mahdiparastesh.mcdtp.sample;

import android.os.Bundle;

import androidx.activity.ComponentActivity;

import ir.mahdiparastesh.mcdtp.sample.databinding.MainBinding;

public class Main extends ComponentActivity {
    MainBinding b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = MainBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());
    }
}
