package com.rsschool.android2021;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;

public class MainActivity extends AppCompatActivity implements FragmentCallback, SecondFragmentCallback {

    private static final String SECOND_FRAGMENT_TAG = "second";
    private static final String FIRST_FRAGMENT_TAG = "first";
    private SecondFragment secondFragment;
    private FirstFragment firstFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openFirstFragment(0);
    }

    private void openFirstFragment(int previousNumber) {
        firstFragment = FirstFragment.newInstance(previousNumber);
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, firstFragment,FIRST_FRAGMENT_TAG);
        // TODO: invoke function which apply changes of the transaction
        transaction.commit();
    }

    private void openSecondFragment(int min, int max) {
        // TODO: implement it
        secondFragment = SecondFragment.newInstance(min, max);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, secondFragment,SECOND_FRAGMENT_TAG).commit();
    }

    @Override
    public void sendData(int min, int max) {
        openSecondFragment(min, max);
    }

    @Override
    public void onBackPressed() {
        if (secondFragment==null || !(secondFragment.getLifecycle().getCurrentState() == Lifecycle.State.RESUMED)){
            super.onBackPressed();
        }else {
            onSecondFragmentBackPressed(secondFragment.getRandomResult());
            secondFragment=null;
        }
    }

    @Override
    public void onSecondFragmentBackPressed(int resultValue) {
        openFirstFragment(resultValue);
    }
}
