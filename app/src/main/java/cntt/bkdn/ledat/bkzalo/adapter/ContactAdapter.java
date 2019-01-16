package cntt.bkdn.ledat.bkzalo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cntt.bkdn.ledat.bkzalo.R;
import cntt.bkdn.ledat.bkzalo.model.User;

public class ContactAdapter extends BaseAdapter {
    private List<User> users;
    private LayoutInflater inflater;
    private Context context;

    public ContactAdapter(List<User> users, Context context) {
        this.users = users;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if (users.isEmpty() || users == null) {
            return 0;
        }
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final User user = users.get(position);
        final ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_contact, null);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.image = (ImageView) convertView.findViewById(R.id.avatar);
            holder.status = (TextView) convertView.findViewById(R.id.status);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.name.setText(user.getName());
        //holder.image.setImageResource(user.ge());
        holder.status.setText(user.getStatus());
        return convertView;
    }

    static class ViewHolder {
        TextView name;
        ImageView image;
        TextView status;
    }
}
