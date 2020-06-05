package com.example.eksamensprojekt.presentation.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.eksamensprojekt.presentation.fragments.BookingStep1Fragment;
import com.example.eksamensprojekt.presentation.fragments.BookingStep2Fragment;
import com.example.eksamensprojekt.presentation.fragments.BookingStep3Fragment;
import com.example.eksamensprojekt.presentation.fragments.BookingStep4Fragment;

public class BookingViewPagerAdapter extends FragmentPagerAdapter {



    public BookingViewPagerAdapter(FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    public BookingViewPagerAdapter(FragmentManager supportFragmentManager) {
        super(supportFragmentManager);
    }


    @Override
    public Fragment getItem(int currentStep) {

        switch (currentStep)
        {
            case 0:
                return BookingStep1Fragment.getInstance();
            case 1:
                return BookingStep2Fragment.getInstance();
            case 2:
                return BookingStep3Fragment.getInstance();
            case 3:
                return BookingStep4Fragment.getInstance();


        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
