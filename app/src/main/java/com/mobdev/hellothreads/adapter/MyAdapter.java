package com.mobdev.hellothreads.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.mobdev.hellothreads.R;
import com.mobdev.hellothreads.model.LogDescriptor;
import com.mobdev.hellothreads.persistence.LogDescriptorManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marco Picone (picone.m@gmail.com) 20/03/2020
 * Recycler View Adapter for Log Data Structure
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

	private List<LogDescriptor> mDataset;
	private Context mContext = null;

	// Provide a reference to the views for each data item
	// Complex data items may need more than one view per item, and
	// you provide access to all the views for a data item in a view holder
	public class ViewHolder extends RecyclerView.ViewHolder {

		private View v = null;

		public ViewHolder(View v) {
			super(v);
			this.v = v;

			v.setOnLongClickListener(new OnLongClickListener() {

				@Override
				public boolean onLongClick(View v) {
					showDeleteConfirmation(getLayoutPosition());
					return false;
				}
			});
		}

		public void setText(String text){
			TextView tView = (TextView)v.findViewById(R.id.myTextView);
			tView.setText(text);
		}
	}

	private void showDeleteConfirmation(final int position){

		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setMessage(R.string.remove_element_message);
		builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				LogDescriptorManager.getInstance(mContext).removeLog(mDataset.get(position));
			}
		}).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				dialog.dismiss();
			}
		});

		builder.create().show();
	}

	// Provide a suitable constructor (depends on the kind of dataset)
	public MyAdapter(List<LogDescriptor> myDataset, Context context) {
		mDataset = myDataset;
		mContext  = context;
	}

	// Provide a suitable constructor (depends on the kind of dataset)
	public MyAdapter(Context context) {
		mDataset = new ArrayList<>();
		mContext  = context;
	}

	// Create new views (invoked by the layout manager)
	@Override
	public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {

		// create a new view
		View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_element, parent, false);
		// set the view's size, margins, paddings and layout parameters

		ViewHolder vh = new ViewHolder(v);

		return vh;
	}

	// Replace the contents of a view (invoked by the layout manager)
	@Override
	public void onBindViewHolder(ViewHolder holder, final int position) {
		// - get element from your dataset at this position
		// - replace the contents of the view with that element
		holder.setText("["+mDataset.get(position).getTimestamp()+"]: "+mDataset.get(position).getData());

	}

	// Return the size of your dataset (invoked by the layout manager)
	@Override
	public int getItemCount() {
		return mDataset.size();
	}

	public List<LogDescriptor> getmDataset() {
		return mDataset;
	}

	public void setDataset(List<LogDescriptor> mDataset) {
		this.mDataset = mDataset;
	}
}