package com.example.asynthread;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NewsAdapter extends BaseAdapter {

	private List<NewsBeans> mList;
	private LayoutInflater mInflater;

	public NewsAdapter(Context context, List<NewsBeans> mList) {
		this.mList = mList;
		this.mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return mList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	private ImageLoader loader = null;

	@Override
	public View getView(int position, View rootView, ViewGroup group) {
		// TODO Auto-generated method stub
		if (rootView == null) {
			rootView = mInflater.inflate(R.layout.item_layout, null);
		}
		TextView tv_content = HolderViewContairs.getView(rootView,
				R.id.iv_content);
		TextView tv_title = HolderViewContairs.getView(rootView, R.id.iv_title);
		ImageView ic_icon = HolderViewContairs.getView(rootView, R.id.ic_icon);
		tv_content.setText(mList.get(position).mContent);
		tv_title.setText(mList.get(position).mTitle);
		String url = mList.get(position).mImgUrl;
		ic_icon.setTag(url);
		loader = new ImageLoader();
		loader.showImageByAsycnTask(ic_icon, url);

//		HolderView holderView = null;
//		if (rootView == null) {
//			holderView = new HolderView();
//			rootView = mInflater.inflate(R.layout.item_layout, null);
//			holderView.contentView = (TextView) rootView
//					.findViewById(R.id.iv_content);
//			holderView.iconImage = (ImageView) rootView
//					.findViewById(R.id.ic_icon);
//			holderView.titleView = (TextView) rootView
//					.findViewById(R.id.iv_title);
//			rootView.setTag(holderView);
//		} else {
//			holderView = (HolderView) rootView.getTag();
//		}
//		holderView.contentView.setText(mList.get(position).mContent);
//		holderView.titleView.setText(mList.get(position).mTitle);
//		String url = mList.get(position).mImgUrl;
//		holderView.iconImage.setTag(url);
		// new ImageLoader().loaderImageByThread(holderView.iconImage, url);
		// if(loader == null)
//		loader = new ImageLoader();
//		loader.showImageByAsycnTask(holderView.iconImage, url);
		// holderView.iconImage.setImageResource(R.drawable.ic_launcher);
		return rootView;
	}

	// private Map<String,View> viewHolderMap = new HashMap<String,View>();

	public static class HolderViewContairs {
		public static <T extends View> T getView(View view, int id) {
			SparseArray<View> sparseArray = (SparseArray<View>) view
					.getTag(R.string.hello_world);
			if (sparseArray == null) {
				sparseArray = new SparseArray<View>();
				view.setTag(R.string.hello_world, sparseArray);
			}
			View childView = sparseArray.get(id);
			if (childView == null) {
				childView = view.findViewById(id);
				sparseArray.put(id, childView);
			}
			return (T) childView;
		}
	}

	class HolderView {
		public TextView titleView, contentView;
		public ImageView iconImage;
	}

}
