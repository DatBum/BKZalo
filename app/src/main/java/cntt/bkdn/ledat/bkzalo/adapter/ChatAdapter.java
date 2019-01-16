package cntt.bkdn.ledat.bkzalo.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cntt.bkdn.ledat.bkzalo.R;
import cntt.bkdn.ledat.bkzalo.activity.ChatActivity;
import cntt.bkdn.ledat.bkzalo.activity.LoginActivity;
import cntt.bkdn.ledat.bkzalo.model.Message;


public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    private List<Message> sms;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
//    public static boolean isMe;

    public ChatAdapter(Context context, List<Message> sms) {
        this.mContext = context;
        this.sms = sms;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = null;
        itemView = mLayoutInflater.inflate(R.layout.item_sms, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Message message = sms.get(position);
        if (message != null) {
            if (message.getIdUserSend().equals(new LoginActivity().currentUser) && message.getIdUserReceive().equals(new ChatActivity().clientId) && message.isText()) {
                holder.tvSend.setText(message.getSms());
                holder.tvSend.setVisibility(View.VISIBLE);
                holder.tvReceive.setVisibility(View.GONE);
            } else if (message.getIdUserReceive().equals(new LoginActivity().currentUser) && message.getIdUserSend().equals(new ChatActivity().clientId) && message.isText()) {
                holder.tvReceive.setText(message.getSms());
                holder.tvSend.setVisibility(View.GONE);
                holder.tvReceive.setVisibility(View.VISIBLE);
            } else if (message.getIdUserSend().equals(new LoginActivity().currentUser) && message.getIdUserReceive().equals(new ChatActivity().clientId) && message.isPhoto()) {
                holder.imvSend.setImageBitmap(convertStringToBitmap(message.getSms()));
                holder.imvSend.setVisibility(View.VISIBLE);
                holder.imvReceive.setVisibility(View.GONE);
            } else if (message.getIdUserReceive().equals(new LoginActivity().currentUser) && message.getIdUserSend().equals(new ChatActivity().clientId) && message.isPhoto()) {
                holder.imvReceive.setImageBitmap(convertStringToBitmap(message.getSms()));
                holder.imvSend.setVisibility(View.GONE);
                holder.imvReceive.setVisibility(View.VISIBLE);

            } else{
                holder.tvSend.setVisibility(View.GONE);
                holder.tvReceive.setVisibility(View.GONE);
                holder.imvSend.setVisibility(View.GONE);
                holder.imvReceive.setVisibility(View.GONE);
            }
        }


    }

    @Override
    public int getItemCount() {
        return sms.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvSend;
        private TextView tvReceive;
        private ImageView imvSend;
        private ImageView imvReceive;

        public ViewHolder(View itemView) {
            super(itemView);
            tvSend = (TextView) itemView.findViewById(R.id.sms_send);
            tvReceive = (TextView) itemView.findViewById(R.id.sms_receive);
            imvSend = (ImageView) itemView.findViewById(R.id.img_send);
            imvReceive = (ImageView) itemView.findViewById(R.id.img_recieve);

        }
    }

    private Bitmap convertStringToBitmap(String s) {
        byte[] manghinh = Base64.decode(s, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(manghinh, 0, manghinh.length);
    }

}
