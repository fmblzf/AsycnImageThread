package com.example.asynthread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class MainActivity extends Activity {

	/**
	 * URL≤ªƒ‹Õ¸¡À°∞http://°±
	 */
	private static final String URL = "http://www.imooc.com/api/teacher?type=4&num=30";
	

	private static ListView mListView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}

		new NewAsynask().execute(URL);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	

	class NewAsynask extends AsyncTask<String, Void, List<NewsBeans>>{

		@Override
		protected List<NewsBeans> doInBackground(String... params) {
			// TODO Auto-generated method stub
			return getJsonData(params[0]);
		}
		
		@Override
		protected void onPostExecute(List<NewsBeans> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result == null)
				return;
			NewsAdapter adapter = new NewsAdapter(MainActivity.this, result);
			mListView.setAdapter(adapter);
		}
	}

	private List<NewsBeans> getJsonData(String url){
		List<NewsBeans> mList = new ArrayList<>();
		
		try {
			String jsonString = readStream(new URL(url).openStream());
			JSONObject jsonObj = new JSONObject(jsonString);
			JSONArray jsonArray = jsonObj.getJSONArray("data");
			for(int i = 0;i<jsonArray.length();i++){
				NewsBeans mNewsBeans = new NewsBeans();
				mNewsBeans.mImgUrl = jsonArray.getJSONObject(i).getString("picSmall");
				mNewsBeans.mTitle = jsonArray.getJSONObject(i).getString("name");
				mNewsBeans.mContent = jsonArray.getJSONObject(i).getString("description");
				mList.add(mNewsBeans);
			}
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return mList;
	}
	
	private String readStream(InputStream is){
		InputStreamReader isr = null;
		String result = "";
		
		try {
			String line = "";
			isr = new InputStreamReader(is,"utf-8");
			BufferedReader br = new BufferedReader(isr);
			while((line = br.readLine())!=null){
				result += line;
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {


		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			mListView = (ListView) rootView.findViewById(R.id.iv_main);
			return rootView;
		}
		
		
			
	}

}
