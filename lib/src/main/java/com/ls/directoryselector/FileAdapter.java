package com.ls.directoryselector;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ls.directoryselector.utils.DirectoryFileFilter;

import java.io.File;
import java.util.List;
import java.util.Locale;

class FileAdapter extends ArrayAdapter<File> {

	private Context context;
	private final LayoutInflater inflater;

	public FileAdapter(Context context, List<File> files) {
		super(context, 0, files);
		this.context = context;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.list_item_folder, parent, false);
			viewHolder.folderName = (TextView) convertView.findViewById(R.id.textview_folder_name);
			viewHolder.dirsInside = (TextView) convertView.findViewById(R.id.textview_dirs_inside);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		File fileItem = getItem(position);

		int dirsInside = fileItem.listFiles(new DirectoryFileFilter()).length;
		String dirPlural = context.getResources().getQuantityString(R.plurals.plural_dirs, dirsInside);

		viewHolder.folderName.setText(fileItem.getName());
		viewHolder.dirsInside.setText(String.format(Locale.getDefault(), "%d %s", dirsInside, dirPlural));
		return convertView;
	}

	private static class ViewHolder {
		TextView folderName;
		TextView dirsInside;
	}
}
