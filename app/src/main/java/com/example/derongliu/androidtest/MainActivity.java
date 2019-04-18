package com.example.derongliu.androidtest;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.derongliu.androidtest.camera.CameraActivity;
import com.example.derongliu.androidtest.dowloadpicture.listener.OnItemClickListener;
import com.example.derongliu.androidtest.dowloadpicture.PictureActivity;
import com.example.derongliu.androidtest.scrollmenu.ScrollMenuActivity;
import com.example.derongliu.androidtest.share.ShareActivity;
import com.example.derongliu.ndk.NDKTestActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements OnItemClickListener {

    private List<String> itemNameList;
    private List<Class> classList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.main_recyclerview);
        initData();
        initClass();
        MainAdapter adapter = new MainAdapter(itemNameList);
        adapter.setListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
    }


    private void initData() {
        itemNameList = new ArrayList<>();
        //itemNameList.add("下载图片");
        //itemNameList.add("NDKTest");
        //itemNameList.add("Camera_lib");
        //itemNameList.add("ScrollMenu");
        itemNameList.add("分享回调");
    }

    private void initClass() {
        classList = new ArrayList<>();
        //classList.add(PictureActivity.class);
        //classList.add(NDKTestActivity.class);
       // classList.add(CameraActivity.class);
        //classList.add(ScrollMenuActivity.class);
        classList.add(ShareActivity.class);
    }


    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(MainActivity.this, classList.get(position));
        startActivity(intent);

    }


    private class MainAdapter extends RecyclerView.Adapter<MainViewHolder> {
        List list;
        private OnItemClickListener listener;

        MainAdapter(List list) {
            this.list = list;
        }

        @Override
        public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item, parent, false);
            return new MainViewHolder(v);
        }

        @Override
        public void onBindViewHolder(MainViewHolder holder, @SuppressLint("RecyclerView") final int position) {
            holder.textView.setText(list.get(position).toString());
            holder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onItemClick(position);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return list == null ? 0 : list.size();
        }

        void setListener(OnItemClickListener listener) {
            this.listener = listener;
        }
    }


    private static class MainViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        MainViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.item_textView);
        }
    }

}
//    /**
//     * 默认分隔线实现类只支持布局管理器为 LinearLayoutManager
//     */
//    private class CommonItemDecoration extends RecyclerView.ItemDecoration {
//        public static final int HORIZONTAL = LinearLayout.HORIZONTAL;
//        public static final int VERTICAL = LinearLayout.VERTICAL;
//
//        //使用系统主题中的R.attr.listDivider作为Item间的分割线
//        private final int[] ATTRS = new int[]{android.R.attr.listDivider};
//
//        private Drawable mDivider;
//
//        private int mOrientation;//布局方向，决定绘制水平分隔线还是竖直分隔线
//
//        private final Rect mBounds = new Rect();
//
//
//        public CommonItemDecoration(Context context, int orientation) {
//            final TypedArray a = context.obtainStyledAttributes(ATTRS);
//            mDivider = a.getDrawable(0);
//            a.recycle();
//            setOrientation(orientation);
//        }
//
//        public void setOrientation(int orientation) {
//            if (orientation != HORIZONTAL && orientation != VERTICAL) {
//                throw new IllegalArgumentException(
//                        "Invalid orientation. It should be either HORIZONTAL or VERTICAL");
//            }
//            mOrientation = orientation;
//        }
//
//        /**
//         * 一个app中分隔线不可能完全一样，你可以通过这个方法传递一个Drawable 对象来定制分隔线
//         */
//        public void setDrawable(@NonNull Drawable drawable) {
//            if (drawable == null) {
//                throw new IllegalArgumentException("Drawable cannot be null.");
//            }
//            mDivider = drawable;
//        }
//
//        /**
//         * 画分隔线
//         */
//        @Override
//        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
//            if (parent.getLayoutManager() == null) {
//                return;
//            }
//            if (mOrientation == VERTICAL) {
//                drawVertical(c, parent);
//            } else {
//                drawHorizontal(c, parent);
//            }
//        }
//
//        /**
//         * 在LinearLayoutManager方向为Vertical时，画分隔线
//         */
//        public void drawVertical(Canvas canvas, RecyclerView parent) {
//            final int left = parent.getPaddingLeft();//★分隔线的左边 = paddingLeft值
//            final int right = parent.getWidth() - parent.getPaddingRight();//★分隔线的右边 = RecyclerView 宽度－paddingRight值
//            //分隔线不在RecyclerView的padding那一部分绘制
//
//            final int childCount = parent.getChildCount();//★分隔线数量=item数量
//            for (int i = 0; i < childCount; i++) {
//                final View child = parent.getChildAt(i);//确定是第几个item
//                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
//                final int top = child.getBottom() + params.bottomMargin;//★分隔线的上边 = item的底部 + item根标签的bottomMargin值
//                final int bottom = top + mDivider.getIntrinsicHeight();//★分隔线的下边 = 分隔线的上边 + 分隔线本身高度
//                mDivider.setBounds(left, top, right, bottom);
//                mDivider.draw(canvas);
//            }
//        }
//
//
//        /**
//         * 在LinearLayoutManager方向为Horizontal时，画分隔线
//         * 理解了上面drawVertical()方法这个方法也就理解了
//         */
//        public void drawHorizontal(Canvas canvas, RecyclerView parent) {
//            final int top = parent.getPaddingTop();
//            final int bottom = parent.getHeight() - parent.getPaddingBottom();
//
//            final int childCount = parent.getChildCount();
//            for (int i = 0; i < childCount; i++) {
//                final View child = parent.getChildAt(i);
//                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
//                final int left = child.getRight() + params.rightMargin;
//                final int right = left + mDivider.getIntrinsicHeight();
//                mDivider.setBounds(left, top, right, bottom);
//                mDivider.draw(canvas);
//            }
//        }
//
//        /**
//         * 获取Item偏移量
//         * 此方法是为每个Item四周预留出空间，从而让分隔线的绘制在预留的空间内
//         */
//        @Override
//        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
//                                   RecyclerView.State state) {
//            if (mOrientation == VERTICAL) {//竖直方向的分隔线：item向下偏移一个分隔线的高度
//                outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
//            } else {//水平方向的分隔线：item向右偏移一个分隔线的宽度
//                outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
//            }
//        }
//    }
