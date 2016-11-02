package com.toquescript.www.norcexproject.fragment;


import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.toquescript.www.norcexproject.R;
import com.toquescript.www.norcexproject.adapter.ViewPagerAdapter;
import com.toquescript.www.norcexproject.base.BaseFragment;
import com.toquescript.www.norcexproject.callbacks.CallBackOpenBottomSheet;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateList extends Fragment implements CallBackOpenBottomSheet {

    String TAG = this.getClass().getSimpleName();
    /**
     * Adapter
     */
    private ViewPagerAdapter adapter = null;

    /**
     * Views
     */
    private View view = null;
    private TabLayout tabLayout = null;
    private ViewPager viewPager = null;
    private FragmentManager fragmentManager = null;
    private LinearLayout bottomSheet;
    private BottomSheetBehavior bsb;

    private List<Fragment> fragmentList;
    private Fragment base_fragment, fragment_tool, fragment_material, fragment_epps;

    public CreateList() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_create_list, container, false);

        viewPager = (ViewPager) view.findViewById(R.id.viewpager);

        base_fragment = new BaseFragment();
        ((BaseFragment)base_fragment).setArgumentCallBack(this);

        fragment_tool = new Tools();
        fragment_material = new Materials();
        fragment_epps = new Epps();

        /*Creamos nuestron contenedor list*/
        fragmentList = new ArrayList<>();
        /*Agregamos los fragmentos para su posterior uso*/
        listFragmentadd(fragment_tool,
                fragment_material,
                fragment_epps);

        setupViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d(TAG, "onPageScrolled - " + position + " " + positionOffset + " " + positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                Log.d(TAG, "onPageSelected - " + fragmentList.get(position).getClass().getSimpleName());
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                /*Eliminar el ActionMode al cambiar*/
                if (state == viewPager.SCROLL_STATE_IDLE) {
                    Log.d(TAG, "onPageScrollStateChanged - SCROLL_STATE_IDLE");
                    ((Tools) fragment_tool).actionModeFinish();
                    ((Tools) fragment_tool).getFilterAdapter(null, false);
                    ((Tools) fragment_tool).clearFocusAndQuerySearchView();

                    ((Materials) fragment_material).actionModeFinish();
                    ((Materials) fragment_material).getFilterAdapter(null, false);
                    ((Materials) fragment_material).clearFocusAndQuerySearchView();

                    ((Epps) fragment_epps).actionModeFinish();
                    ((Epps) fragment_epps).getFilterAdapter(null, false);
                    ((Epps) fragment_epps).clearFocusAndQuerySearchView();
                } else if (state == viewPager.SCROLL_STATE_DRAGGING) {
                    Log.d(TAG, "onPageScrollStateChanged -SCROLL_STATE_DRAGGING");
                } else if (state == viewPager.SCROLL_STATE_DRAGGING) {
                    Log.d(TAG, "onPageScrollStateChanged -SCROLL_STATE_DRAGGING");
                }
            }
        });
        tabLayout = (TabLayout) view.findViewById(R.id.tabslayout);
        tabLayout.setupWithViewPager(viewPager);


        return view;
    }

    @Override
    public void openBottomSheet() {

    }


    private void listFragmentadd(Fragment... fragments) {
        for (Fragment fragment : fragments) {
            fragmentList.add(fragment);
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(fragment_tool, "Herramientas");
        adapter.addFragment(fragment_material, "Materiales");
        adapter.addFragment(fragment_epps, "Epps");
        viewPager.setAdapter(adapter);
    }


}
