package ca.nait.dmit2504.nmlab1oscarreviews;

import android.content.Context;
import android.system.Os;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import org.w3c.dom.Text;

import java.util.List;

public class OscarListViewAdapter extends BaseAdapter {

    private Context context;
    private List<Oscar> oscarList;
    private LayoutInflater layoutInflater;

    public OscarListViewAdapter(Context context, List<Oscar> oscarList) {
        this.context = context;
        this.oscarList = oscarList;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return oscarList.size();
    }

    @Override
    public Object getItem(int position) {
        return oscarList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Inflate XML file
        View rowView = layoutInflater.inflate(R.layout.custom_listview_item, null);

        TextView dateTextView = rowView.findViewById(R.id.custom_listview_item_date);
        TextView reviewerTextView = rowView.findViewById(R.id.custom_listview_item_reviewer);
        TextView categoryTextView = rowView.findViewById(R.id.custom_listview_item_category);
        TextView nomineeTextView = rowView.findViewById(R.id.custom_listview_item_nominee);
        TextView reviewTextView = rowView.findViewById(R.id.custom_listview_item_review);

        Oscar currentOscar = oscarList.get(position);
        dateTextView.setText(currentOscar.getDate());
        reviewerTextView.setText(currentOscar.getReviewer());
        categoryTextView.setText(currentOscar.getCategory());
        nomineeTextView.setText(currentOscar.getNominee());
        reviewTextView.setText(currentOscar.getReview());
        return rowView;
    }
}
