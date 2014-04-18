package com.waiki.pesanmakan.menu;

import java.util.ArrayList;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.waiki.pesanmakan.R;
import com.waiki.pesanmakan.api.MenuAPI;
import com.waiki.pesanmakan.api.MenuCategoryAPI;
import com.waiki.pesanmakan.api.obj.Menu;
import com.waiki.pesanmakan.api.obj.MenuCategories;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class MenuActivity extends FragmentActivity {
	
	private ArrayList<MenuObj> menuObjs;
	private ListView lvItemList;
	private ListView lvCartMenuItemList;
	private SectionsPagerAdapter mSectionsPagerAdapter;
	private ViewPager mViewPager;
	private MenuCategoryAPI menuCategoryAPI;
	private MenuAPI menuAPI;
	private ArrayList<MenuCategories> menuCategory;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		
		menuCategoryAPI = new MenuCategoryAPI();
		menuAPI = new MenuAPI();
		new AsyncTask<Void,Void,String>() {

			@Override
			protected String doInBackground(Void... params) {
				menuCategory = menuCategoryAPI.getAll();
				return null;
			}
			
			@Override
			protected void onPostExecute(String result) {
				mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
				mViewPager = (ViewPager) findViewById(R.id.pager);
				mViewPager.setAdapter(mSectionsPagerAdapter);
			}
			
		}.execute();
		
		String result = getIntent().getExtras().getString("result");
		Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
		
		SlidingMenu menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        menu.setShadowWidthRes(R.dimen.shadow_width);
        menu.setShadowDrawable(R.drawable.shadow);
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        menu.setMenu(R.layout.sidebar_cart_menu);
        
        lvCartMenuItemList = (ListView) findViewById(R.id.sidebar_cart_menu_lv_list);
        menuObjs = new ArrayList<MenuObj>();
		menuObjs.add(new MenuObj("Sirloin", "Rp 14.000"));
		menuObjs.add(new MenuObj("Sirloin Double", "Rp 19.000"));
		lvCartMenuItemList.setAdapter(new MenuAdapter(this, menuObjs));
		
//		lvItemList = (ListView) findViewById(R.id.activity_menu_pager_list_lv_item_list);
//		menuObjs = new ArrayList<MenuObj>();
//		menuObjs.add(new MenuObj("Sirloin", "Rp 14.000"));
//		menuObjs.add(new MenuObj("Sirloin Double", "Rp 19.000"));
//		lvItemList.setAdapter(new MenuAdapter(this, menuObjs));
//		lvItemList.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//					long arg3) {
//				
//			}
//		});
	}
	
	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {
		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			Fragment fragment = new DummySectionFragment();
//			Bundle args = new Bundle();
//			args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
//			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			return menuCategory.size();
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return menuCategory.get(position).getName();
		}
	}

	public static class DummySectionFragment extends Fragment {
		
		private ArrayList<MenuObj> menuObjs;
		private MenuAdapter menuAdapter;
		private ArrayList<Menu> menus;
		private MenuAPI menuAPI;
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.activity_menu_pager_list,container, false);
			ListView lvMenuList = (ListView) rootView.findViewById(R.id.activity_menu_pager_list_lv_item_list);
			menuAPI = new MenuAPI();
			menuObjs = new ArrayList<MenuObj>();
			menuAdapter = new MenuAdapter(rootView.getContext(), menuObjs);
			lvMenuList.setAdapter(menuAdapter);
			new AsyncTask<Void,Void,String>() {
				@Override
				protected String doInBackground(Void... params) {
					menus = menuAPI.getAll();
					return null;
				}
				protected void onPostExecute(String result) {
					for(int i = 0;i < menus.size();i++){
						menuObjs.add(new MenuObj(menus.get(i).getName(), menus.get(i).getPrice()));
					}
					menuAdapter.notifyDataSetChanged();
				};
			}.execute();
			return rootView;
		}
	}

}
