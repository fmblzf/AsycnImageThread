package com.example.asynthread;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

public class ImageLoader {
	private static Map<String,SoftReference<Bitmap>> cacheBitmap = new HashMap<String, SoftReference<Bitmap>>();
	private ImageView mImageView;
	private String mUrl;

	private Handler handler = new Handler() {
		@Override
		public void dispatchMessage(Message msg) {
			// TODO Auto-generated method stub
			super.dispatchMessage(msg);
			if (mImageView.getTag().equals(mUrl)) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mImageView.setImageBitmap((Bitmap) msg.obj);
			}
		}
	};

	public void loaderImageByThread(ImageView imageView, String url) {
		this.mImageView = imageView;
		this.mUrl = url;
		(new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				Bitmap bitmap = getBitmapByUrl(mUrl);
				Message message = handler.obtainMessage();
				message.obj = bitmap;
				handler.sendMessage(message);
			}
		}).start();
	}

	public Bitmap getBitmapByUrl(String url) {
		// HttpConnection http = new
		Bitmap bitmap = null;
		InputStream is = null;
		try {
			is = new URL(url).openStream();
			bitmap = BitmapFactory.decodeStream(is);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return bitmap;
	}
	
	public void showImageByAsycnTask(ImageView imageView, String url){
		this.mImageView = imageView;
		this.mUrl = url;
		SoftReference<Bitmap> soft = cacheBitmap.get(url);
		if(soft!=null){
			Bitmap bitmap = soft.get();
			setImagViewByBitmap(bitmap);
			return;
		}
		new NewsAsycnTask().execute(url);
	}
	
	private class NewsAsycnTask extends AsyncTask<String, Void, Bitmap>{

		@Override
		protected Bitmap doInBackground(String... params) {
			// TODO Auto-generated method stub
			return getBitmapByUrl(params[0]);
		}
		
		@Override
		protected void onPostExecute(Bitmap result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			setImagViewByBitmap(result);
		}
	} 
	
	public void setImagViewByBitmap(Bitmap result){
		if (mImageView.getTag().equals(mUrl)) {
			SoftReference<Bitmap> soft = new SoftReference<Bitmap>(result);
			cacheBitmap.put(mUrl, soft);
			mImageView.setImageBitmap(result);
		}
	}

}
