package com.skydianshi.chatview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.speech.recognizerdemo.R;

import java.util.List;

/**
 * 比原来的多了getItemViewType和getViewTypeCount这两个方法，
 * 
 * */
public class ChatAdapter extends BaseAdapter {

	public static final String KEY = "key";
	public static final String VALUE = "value";

	public static final int VALUE_TIME_TIP = 0;// 7种不同的布局
	public static final int VALUE_LEFT_TEXT = 1;
	public static final int VALUE_LEFT_IMAGE = 2;
	public static final int VALUE_LEFT_AUDIO = 3;
	public static final int VALUE_RIGHT_TEXT = 4;
	public static final int VALUE_RIGHT_IMAGE = 5;
	private LayoutInflater mInflater;

	private List<ChatMessage> myList;

	public ChatAdapter(Context context, List<ChatMessage> myList) {
		this.myList = myList;

		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return myList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return myList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {

		ChatMessage msg = myList.get(position);
		int type = getItemViewType(position);
		ViewHolderTime holderTime = null;
		ViewHolderRightText holderRightText = null;

		ViewHolderLeftText holderLeftText = null;

		
		if (convertView == null) {
			switch (type) {
			case VALUE_TIME_TIP:
				holderTime = new ViewHolderTime();
				convertView = mInflater.inflate(R.layout.list_item_time_tip,
						null);
				holderTime.tvTimeTip = (TextView) convertView
						.findViewById(R.id.tv_time_tip);
				holderTime.tvTimeTip.setText(msg.getValue());
				convertView.setTag(holderTime);
				break;
			// 左边
			case VALUE_LEFT_TEXT:
				holderLeftText = new ViewHolderLeftText();
				convertView = mInflater.inflate(R.layout.list_item_left_text,
						null);
				holderLeftText.ivLeftIcon = (ImageView) convertView
						.findViewById(R.id.iv_icon);
				holderLeftText.btnLeftText = (Button) convertView
						.findViewById(R.id.btn_left_text);
				holderLeftText.btnLeftText.setText(msg.getValue());
				convertView.setTag(holderLeftText);
				break;
			// 右边
			case VALUE_RIGHT_TEXT:
				holderRightText= new ViewHolderRightText();
				convertView = mInflater.inflate(R.layout.list_item_right_text,
						null);
				holderRightText.ivRightIcon = (ImageView) convertView
						.findViewById(R.id.iv_icon);
				holderRightText.btnRightText = (Button) convertView
						.findViewById(R.id.btn_right_text);
				holderRightText.btnRightText.setText(msg.getValue());
				convertView.setTag(holderRightText);
				break;
			default:
				break;
			}
			
		} else {
			Log.d("baseAdapter", "Adapter_:"+(convertView == null) );
			switch (type) {
			case VALUE_TIME_TIP:
				holderTime=(ViewHolderTime)convertView.getTag();
				holderTime.tvTimeTip.setText(msg.getValue());
				break;
			case VALUE_LEFT_TEXT:
				holderLeftText=(ViewHolderLeftText)convertView.getTag();
				holderLeftText.btnLeftText.setText(msg.getValue());
				break;
			case VALUE_RIGHT_TEXT:
				holderRightText=(ViewHolderRightText)convertView.getTag();
				holderRightText.btnRightText.setText(msg.getValue());
				break;
			default:
				break;
			}
			
			//holder = (ViewHolder) convertView.getTag();
		}
		return convertView;
	}

	/**
	 * 根据数据源的position返回需要显示的的layout的type
	 * 
	 * type的值必须从0开始
	 * 
	 * */
	@Override
	public int getItemViewType(int position) {

		ChatMessage msg = myList.get(position);
		int type = msg.getType();
		return type;
	}

	/**
	 * 返回所有的layout的数量
	 * 
	 * */
	@Override
	public int getViewTypeCount() {
		return 7;
	}

	class ViewHolderTime {
		private TextView tvTimeTip;// 时间
	}

	class ViewHolderRightText {
		private ImageView ivRightIcon;// 右边的头像
		private Button btnRightText;// 右边的文本
	}

	class ViewHolderLeftText {
		private ImageView ivLeftIcon;// 左边的头像
		private Button btnLeftText;// 左边的文本
	}

}
