/*
package edu.sjsu.android.restaurantroller;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class TabsAdapter extends FragmentStatePagerAdapter {
    int tabCount;

    public TabsAdapter(FragmentManager fm, int tabs){
        super(fm);
        this.tabCount = tabs;
    }
    @Override
    public int getCount() {
        return tabCount;
    }

    @Override
    public Fragment getItem(int position){
        switch (position){
            case 0:
                return home;
            case 1:
                AboutFragment about = new AboutFragment();
                return about;
            case 2:
                ContactFragment contact = new ContactFragment();
                return contact;
            default:
                return null;
        }
    }
}
*/
