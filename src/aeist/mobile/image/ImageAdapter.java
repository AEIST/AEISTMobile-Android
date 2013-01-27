package aeist.mobile.image;

import aeist.mobile.R;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter{

	   int mGalleryItemBackground;
	    private Context mContext;

	    private Integer[] mImageIds = {
	            R.drawable.aeist_logo,
	            R.drawable.aeist_logo,
	            R.drawable.aeist_logo,
	            R.drawable.aeist_logo,
	            R.drawable.aeist_logo,
	            R.drawable.aeist_logo,
	            R.drawable.aeist_logo,
	            R.drawable.aeist_logo,
	            R.drawable.aeist_logo,
	            R.drawable.aeist_logo,
	            R.drawable.aeist_logo,
	            R.drawable.aeist_logo,
	            R.drawable.aeist_logo,
	            R.drawable.aeist_logo
	    };

	    public ImageAdapter(Context c) {
	        mContext = c;
/*	        TypedArray a = c.obtainStyledAttributes(R.styleable.HelloGallery);
	        mGalleryItemBackground = a.getResourceId(
	                R.styleable.HelloGallery_android_galleryItemBackground, 0);
	        a.recycle();*/
	    }
	    
	public Integer[] getImageIDs()
	{
		return mImageIds;
	}
	
	public int getCount() {
        return mImageIds.length;
	}

	public Object getItem(int position) {
        return position;
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		
		ImageView i = new ImageView(mContext);

        i.setImageResource(mImageIds[position]);
        i.setLayoutParams(new GridView.LayoutParams(150, 150));
        i.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        i.setBackgroundResource(mGalleryItemBackground);

        return i;

	}

}
