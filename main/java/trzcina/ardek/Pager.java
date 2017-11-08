package trzcina.ardek;

import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import trzcina.ardek.ustawienia.Ustawienia;

/**
 * Created by piotr.trzcinski on 06.11.2017.
 */

public class Pager extends PagerAdapter implements ViewPager.OnPageChangeListener {

    public Pager() {
        super();
    }

    public int getCount() {
        return 2;
    }

    public Object instantiateItem(View collection, int position) {
        View view = null;
        switch (position) {
            case 0:
                view = MainActivity.activity.przyciski;
                break;
            case 1:
                view = MainActivity.activity.opcje;
                break;
        }
        ((ViewPager) collection).addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
        ((ViewPager) arg0).removeView((View) arg2);

    }


    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == ((View) arg1);

    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                break;
            case 1:
                Ustawienia.wczytajDoPol();
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}