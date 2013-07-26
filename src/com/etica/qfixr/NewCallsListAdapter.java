package com.etica.qfixr;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.etica.qfixr.R;

public class NewCallsListAdapter extends BaseAdapter {

	private ArrayList<NewCallsObject> listData;

	private LayoutInflater layoutInflater;

	public NewCallsListAdapter(Context context, ArrayList<NewCallsObject> listData) {
		this.listData = listData;
		layoutInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return listData.size();
	}

	@Override
	public Object getItem(int position) {
		return listData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.newcalls_list_row, null);
			holder = new ViewHolder();
			holder.defectTextView = (TextView) convertView.findViewById(R.id.defect);
			holder.addressTextView = (TextView) convertView.findViewById(R.id.address);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.defectTextView.setText(listData.get(position).getDefect());
		holder.addressTextView.setText(listData.get(position).getAddress());

		return convertView;
	}

	static class ViewHolder {
		
		TextView defectTextView;
		TextView addressTextView;
		
	}

}
