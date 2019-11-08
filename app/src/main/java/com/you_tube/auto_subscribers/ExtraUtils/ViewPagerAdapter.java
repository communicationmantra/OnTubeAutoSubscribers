package com.you_tube.auto_subscribers.ExtraUtils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.you_tube.auto_subscribers.AlreadySubList.SubListFragment;
import com.you_tube.auto_subscribers.MainFragments.FirstFragment;
import com.you_tube.auto_subscribers.MainFragments.SecondFragment;
import com.you_tube.auto_subscribers.MainFragments.ThirdFragment;
import com.you_tube.auto_subscribers.MyChannelList.YourVideoFragment;


public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager fm)
    {
        super(fm);
    }



    @Override
    public Fragment getItem(int position)
    {
        if (position == 0)
        {
            return new FirstFragment();
        }
        else if (position == 1)
        {
            return new SecondFragment();
        }
        else if (position == 2)
        {
            return new ThirdFragment();
        }
        else if (position == 3)
        {
            return new YourVideoFragment();
        }
        else if (position == 4)
        {
            return new SubListFragment();
        }

        else return null;
    }

    @Override
    public int getCount() {
        return 5;
    }
}
