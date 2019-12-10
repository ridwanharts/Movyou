package com.jangkriek.ridwanharts.movyou.main;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jangkriek.ridwanharts.movyou.R;
import com.jangkriek.ridwanharts.movyou.favorit.FavoritFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private View view;

    public MenuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_menu, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        viewPager.setAdapter(new ViewPagerAdapter(getChildFragmentManager()));
        tabLayout = (TabLayout)view.findViewById(R.id.tabLay);
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return view;
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {

        private final String tabs[] = {getString(R.string.NowPlaying),getString(R.string.Upcoming),"FAVORIT"};

        public ViewPagerAdapter(FragmentManager manager){

            super(manager);
        }

        @Override
        public Fragment getItem(int i) {
            switch (i){
                case 0 :
                    return new NowPlayingFragment();
                case 1 :
                    return new UpcomingFragment();
                case 2 :
                    return new FavoritFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return tabs.length;
        }

        @Override
        public CharSequence getPageTitle(int i){
            return tabs[i];
        }
    }

}
