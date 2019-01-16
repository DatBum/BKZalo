package cntt.bkdn.ledat.bkzalo.model;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class RecycleListener implements RecyclerView.OnItemTouchListener {
    private RecycleTouchListener listener;
    private GestureDetector detector;
    interface RecycleTouchListener{
        public void onClickItem(View v, int position);
        public void onLongClickItem(View v,int position);
    }

    public RecycleListener(Context context, final RecyclerView rv, final RecycleTouchListener listener) {
        this.listener = listener;
        detector = new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
            @Override
            public void onLongPress(MotionEvent e) {
                View v= rv.findChildViewUnder(e.getX(),e.getY());
                listener.onLongClickItem(v,rv.getChildAdapterPosition(v));
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                View v = rv.findChildViewUnder(e.getX(), e.getY());
                listener.onClickItem(v, rv.getChildAdapterPosition(v));
                return true;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View child = rv.findChildViewUnder(e.getX(), e.getY());
        return ( child != null && detector.onTouchEvent(e));

    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}

