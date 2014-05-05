package br.ufrj.superchef.control;

import java.lang.reflect.Field;
import java.util.ArrayList;

import br.ufrj.superchef.service.NavDrawerListAdapter;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.ViewDragHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;

import br.ufrj.superchef.R;

import br.ufrj.superchef.domain.NavDrawerItem;

public class BasicActivity extends Activity{

	protected DrawerLayout drawerLayout;
	protected ListView drawerList;
	protected String[] layers;
	protected ActionBarDrawerToggle drawerToggle;
	protected ArrayList<NavDrawerItem> navDrawerItems;
	protected NavDrawerListAdapter adapter;

	// slider menu items details 
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;

	final static String APP_PREFS = "app_prefs";
	final static String USERNAME_KEY = "username";
	final static String DIET_KEY = "diet";
	final static String COOKTYPE_KEY = "cookType";
	final static String RECIPYCOUSINE_KEY = "recipyCousine";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getOverflowMenu();

	}

	public void menuOptions() {
	    getWindow().requestFeature(Window.FEATURE_ACTION_BAR); // Add this line
	    ActionBar actionBar = getActionBar();
	    actionBar.show();
	    getActionBar().setIcon(R.drawable.super_chef_icon);     
	    getActionBar().setTitle("Super Chef");
	    getActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_background)); 

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (drawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		switch (item.getItemId()) {
		case R.id.home:
			SharedPreferences prefs = getSharedPreferences(APP_PREFS, MODE_PRIVATE);
			String userName = prefs.getString(USERNAME_KEY, null);
			String diet = prefs.getString(DIET_KEY, null);
			String cookType = prefs.getString(COOKTYPE_KEY, null);
			String recipyCousine = prefs.getString(RECIPYCOUSINE_KEY, null);
			
			
			if (userName != null) {
				if(diet==null){
					diet = "";
				}
				if(cookType==null){
					cookType = "";
				}
				if(recipyCousine==null){
					recipyCousine = "";
				}
				if(!conectado(this)){
					Intent intent = new Intent(this, br.ufrj.superchef.control.FavoriteActivity.class);
					startActivity(intent);
					finish();
				}else{
					Intent intent = new Intent(this, br.ufrj.superchef.control.RecomendationRecipesActivity.class);
					intent.putExtra("name", userName);
					intent.putExtra("diet", diet);
					intent.putExtra("cookType", cookType);
					intent.putExtra("recipyCousine", recipyCousine);
					startActivity(intent);
				}
			}else{
				startActivity(new Intent(this, br.ufrj.superchef.control.MainActivity.class));
			}
			return true;
		case R.id.search:
			startActivity(new Intent(this, br.ufrj.superchef.control.SearchRecipyActivity.class));
			return true;
//		case R.id.favorite:
//			startActivity(new Intent(this, FavoriteActivity.class));
//			return true;
//		case R.id.changeInfo:
//			startActivity(new Intent(this, AddUserInfoActivity.class));
//			return true;
//		case R.id.chronometer:
//			startActivity(new Intent(this, ChronometerActivity.class));
//			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	protected void getOverflowMenu() {

		try {
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class
					.getDeclaredField("sHasPermanentMenuKey");
			if (menuKeyField != null) {
				menuKeyField.setAccessible(true);
				menuKeyField.setBoolean(config, false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void leftMenu() {
		try {
			drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
			drawerList = (ListView) findViewById(R.id.left_drawer);
		
			drawerToggle = new ActionBarDrawerToggle((Activity) this, drawerLayout,
					R.drawable.ic_drawer, 0, 0) {
				public void onDrawerClosed(View view) {
				}
		
				public void onDrawerOpened(View drawerView) {
				}
			};
			drawerLayout.setDrawerListener(drawerToggle);
			getActionBar().setHomeButtonEnabled(true);
			getActionBar().setDisplayHomeAsUpEnabled(true);
		
			// getting items of slider from array
			navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

			// getting Navigation drawer icons from res 
			navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);
			
			navDrawerItems = new ArrayList<NavDrawerItem>();

			
			navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
			navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
			navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
			navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));
			navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));
			
			// Recycle array
			navMenuIcons.recycle();
			
			// setting list adapter for Navigation Drawer
			adapter = new NavDrawerListAdapter(getApplicationContext(),
					navDrawerItems);
			drawerList.setAdapter(adapter);
			
			Field dragger = null;
		
			try {
				dragger = drawerLayout.getClass().getDeclaredField("mLeftDragger");
			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			dragger.setAccessible(true);
			ViewDragHelper draggerObj = null;
			try {
				draggerObj = (ViewDragHelper) dragger.get(drawerLayout);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			Field mEdgeSize = null;
			try {
				mEdgeSize = draggerObj.getClass().getDeclaredField("mEdgeSize");
			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mEdgeSize.setAccessible(true);
			int edge = 0;
			try {
				edge = mEdgeSize.getInt(draggerObj);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			try {
				mEdgeSize.setInt(draggerObj, edge * 5);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			drawerList.setOnItemClickListener(new SlideMenuClickListener());
		
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		drawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		drawerToggle.onConfigurationChanged(newConfig);
	}
	
	
	public static boolean conectado(Context context) {
		try {
			ConnectivityManager cm = (ConnectivityManager)
			context.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected()) {
		    	return true;
		    } else if(cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected()){
		        return true;
		    } else {
		    	return false;
		    }
		} catch (Exception e) {
			return false;
		}
	}
	
	
	
	/**
	 * Slider menu item click listener
	 * */
	protected class SlideMenuClickListener implements ListView.OnItemClickListener {
		protected Context context;
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			context = parent.getContext();
			displayView(position);
		}
		protected void displayView(int position) {
			Intent intent = null;
			switch (position) {
			
			case 0:
				SharedPreferences prefs = getSharedPreferences(APP_PREFS, MODE_PRIVATE);
				String userName = prefs.getString(USERNAME_KEY, null);
				String diet = prefs.getString(DIET_KEY, null);
				String cookType = prefs.getString(COOKTYPE_KEY, null);
				String recipyCousine = prefs.getString(RECIPYCOUSINE_KEY, null);
				
				
				if (userName != null) {
					if(diet==null){
						diet = "";
					}
					if(cookType==null){
						cookType = "";
					}
					if(recipyCousine==null){
						recipyCousine = "";
					}

					if(!conectado(context)){
						intent = new Intent(context, br.ufrj.superchef.control.FavoriteActivity.class);
						startActivity(intent);
						finish();
					}else{
						intent = new Intent(context, br.ufrj.superchef.control.RecomendationRecipesActivity.class);
						intent.putExtra("name", userName);
						intent.putExtra("diet", diet);
						intent.putExtra("cookType", cookType);
						intent.putExtra("recipyCousine", recipyCousine);
						startActivity(intent);
					}
				}else{
					intent = new Intent(context, br.ufrj.superchef.control.MainActivity.class);
				}
				break;
			case 1:
				intent = new Intent(context, br.ufrj.superchef.control.SearchRecipyActivity.class);
				break;
			case 2:
				intent = new Intent(context, br.ufrj.superchef.control.FavoriteActivity.class);
				break;
			case 3:
				intent = new Intent(context, br.ufrj.superchef.control.AddUserInfoActivity.class);
				break;
			case 4:
				intent = new Intent(context, br.ufrj.superchef.control.ChronometerActivity.class);
				break;

			default:
				break;
			}
			
			if(intent == null){
				intent = new Intent(context, br.ufrj.superchef.control.RecomendationRecipesActivity.class);
			}
			startActivity(intent);

		}
	}
}
