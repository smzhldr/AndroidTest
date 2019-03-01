package com.example.derongliu.androidtest.scrollmenu;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.example.derongliu.androidtest.R;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class ScrollMenuBar extends LinearLayout {


    private final String TAG = this.getClass().getSimpleName();

    private Context context;

    private RecyclerView recyclerView;
    private ScrollMenuAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private Scroller scroller;
    private boolean pullOverlay;
    private boolean pushOverlay;
    private int startY;
    private float overlayAlpha = -1;
    /**
     * 滚动结束
     */
    private int endY;
    /**
     * 上一次滑动的坐标
     */
    private int lastY;


    private int barWidth;

    private int itemDecoration;

    public ScrollMenuBar(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public ScrollMenuBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
        this.context = context;
        init();
    }

    public ScrollMenuBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ScrollMenuBar, defStyleAttr, 0);
        barWidth = a.getDimensionPixelSize(R.styleable.ScrollMenuBar_bar_width, 0);
        itemDecoration = a.getDimensionPixelSize(R.styleable.ScrollMenuBar_bar_item_decoration, 0);
        a.recycle();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void init() {
        if (getChildCount() > 0) {
            return;
        }
        scroller = new Scroller(context);
        recyclerView = new RecyclerView(context);
        linearLayoutManager = new SliderLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayout.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setOverScrollMode(OVER_SCROLL_NEVER);
        recyclerView.addItemDecoration(new MenuItemDecoration(itemDecoration));

        adapter = new ScrollMenuAdapter();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(barWidth != 0 ? barWidth : LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        addView(recyclerView, params);
        recyclerView.setAdapter(adapter);
        bringToFront();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = getScrollY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (!scroller.isFinished()) {
                    scroller.abortAnimation();  //终止动画
                }
                scrollTo(0, (int) ((lastY - y) * 0.1));


                if (linearLayoutManager.getItemCount() < 3 || linearLayoutManager.getChildCount() < 3) {
                    break;
                }

                View childView;
                if (pullOverlay) {
                    childView = linearLayoutManager.getChildAt(linearLayoutManager.getChildCount() - 2);
                } else if (pushOverlay) {
                    childView = linearLayoutManager.getChildAt(1);
                } else {
                    childView = null;
                }

                if (childView == null) {
                    break;
                }

                if(overlayAlpha == -1){
                    overlayAlpha = childView.getAlpha();
                }

                float scale = (childView.getHeight() - Math.abs((lastY - y) * 0.1f)) / (childView.getHeight() + itemDecoration);
                float alpha = scale;
                scale = 0.5f * scale + 0.5f;
                childView.setScaleX(scale);
                childView.setScaleY(scale);
                childView.setAlpha(alpha);

                break;
            case MotionEvent.ACTION_UP:
                endY = getScrollY();
                int dScrollY = endY - startY;
                /*
                 * 回弹动画，第一二个参数为开始的x，y
                 * 第三个和第四个参数为滚动的距离（注意方向问题）
                 * 第五个参数是回弹时间
                 */
                scroller.startScroll(0, endY, 0, -dScrollY, 500);


                if (linearLayoutManager.getItemCount() < 3 || linearLayoutManager.getChildCount() < 3) {
                    break;
                }
                final View lastChildView;
                if (pullOverlay) {
                    lastChildView = linearLayoutManager.getChildAt(linearLayoutManager.getChildCount() - 2);
                } else if (pushOverlay) {
                    lastChildView = linearLayoutManager.getChildAt(1);
                } else {
                    lastChildView = null;
                }

                if (lastChildView == null) {
                    break;
                }
                ValueAnimator valueAnimator = ValueAnimator.ofFloat(lastChildView.getAlpha(), overlayAlpha);
                valueAnimator.setDuration(200);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                                    @Override
                                                    public void onAnimationUpdate(ValueAnimator animation) {
                                                        float scale = (float) animation.getAnimatedValue();
                                                        float alpha = scale;
                                                        scale = 0.5f * scale + 0.5f;
                                                        lastChildView.setScaleX(scale);
                                                        lastChildView.setScaleY(scale);
                                                        lastChildView.setAlpha(alpha);

                                                        if (scale == overlayAlpha) {
                                                            pullOverlay = false;
                                                            pushOverlay = false;
                                                            overlayAlpha = -1;
                                                        }
                                                    }
                                                }
                );
                valueAnimator.start();
                break;
        }
        postInvalidate();
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int y = (int) ev.getY();
        Log.d(TAG, "相对于组件滑过的距离==getY()：" + y);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                /*
                 * 下面两个判断来自于 BGARefreshLayout 框架中的判断，github 上搜索 BGARefreshLayout
                 */
                if (y - lastY > 0) {
                    if (isRecyclerViewToTop(recyclerView)) {
                        pullOverlay = true;
                        return true;
                    }
                }

                if (y - lastY < 0) {
                    if (isRecyclerViewToBottom(recyclerView)) {
                        pushOverlay = true;
                        return true;
                    }
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                break;
        }

        return false;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()) {
            scrollTo(0, scroller.getCurrY());
            postInvalidate();
        }
    }

    public void setMenuItems(@NonNull ArrayList arrayList) {
        adapter.setItems(arrayList);
    }

    private class ScrollMenuAdapter extends RecyclerView.Adapter {

        private final int itemId = generateViewId();
        private ArrayList items;

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ImageView imageView = new ImageView(parent.getContext());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(barWidth, barWidth);
            imageView.setLayoutParams(layoutParams);
            imageView.setId(itemId);
            return new ScrollMenuViewHolder(imageView);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (position == 0 || position == items.size() + 1) {
                return;
            }
            int color = (int) items.get(position - 1);
            holder.itemView.setBackgroundColor(color);
        }

        @Override
        public int getItemCount() {
            return items.size() + 2;
        }

        public void setItems(ArrayList items) {
            this.items = items;
        }

        class ScrollMenuViewHolder extends RecyclerView.ViewHolder {

            ImageView imageView;

            ScrollMenuViewHolder(View itemView) {
                super(itemView);
                imageView = (ImageView) itemView.findViewById(itemId);
            }
        }
    }

    private class SliderLayoutManager extends LinearLayoutManager {

        SliderLayoutManager(Context context) {
            super(context);
        }

        @Override
        public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
            super.onLayoutChildren(recycler, state);
            scaleDownView(this);
        }

        @Override
        public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
            int scroll = super.scrollVerticallyBy(dy, recycler, state);
            scaleDownView(this);
            return scroll;
        }
    }

    private void scaleDownView(LinearLayoutManager linearLayoutManager) {
        for (int i = 0; i < linearLayoutManager.getChildCount(); i++) {
            View childView = linearLayoutManager.getChildAt(i);
            int childrenCount = linearLayoutManager.getChildCount();
            if (childView == null) {
                break;
            }
            float scale;
            if (linearLayoutManager.getItemCount() < 3 || childrenCount < 3) {
                break;
            }
            if (i == 1) {
                scale = (childView.getTop() - ((RecyclerView) childView.getParent()).getTop() - itemDecoration) / ((float) childView.getHeight() + itemDecoration);
            } else if (i == childrenCount - 2) {
                scale = (childView.getBottom() - ((RecyclerView) childView.getParent()).getBottom()) / ((float) childView.getHeight() + itemDecoration);
            } else {
                scale = 1f;
            }
            scale = Math.abs(scale);
            float alpha = scale;
            if (alpha < 0.1f) {
                alpha = 0f;
            }
            if (scale < 1f) {
                scale = 0.5f * scale + 0.5f;
            }
            childView.setScaleX(scale);
            childView.setScaleY(scale);
            if (i == 0 || i == childrenCount - 1) {
                childView.setAlpha(0f);
            } else {
                childView.setAlpha(alpha);
            }
        }
    }


    private boolean isRecyclerViewToTop(RecyclerView recyclerView) {
        if (recyclerView != null) {
            RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
            if (manager == null) {
                return true;
            }
            if (manager.getItemCount() == 0) {
                return true;
            }

            if (manager instanceof LinearLayoutManager) {
                LinearLayoutManager layoutManager = (LinearLayoutManager) manager;

                int firstChildTop = 0;
                if (recyclerView.getChildCount() > 0) {
                    // 处理item高度超过一屏幕时的情况
                    View firstVisibleChild = recyclerView.getChildAt(0);
                    if (firstVisibleChild != null && firstVisibleChild.getMeasuredHeight() >= recyclerView.getMeasuredHeight()) {
                        if (android.os.Build.VERSION.SDK_INT < 14) {
                            return !(ViewCompat.canScrollVertically(recyclerView, -1) || recyclerView.getScrollY() > 0);
                        } else {
                            return !ViewCompat.canScrollVertically(recyclerView, -1);
                        }
                    }

                    // 如果RecyclerView的子控件数量不为0，获取第一个子控件的top

                    // 解决item的topMargin不为0时不能触发下拉刷新
                    View firstChild = recyclerView.getChildAt(0);
                    RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) firstChild.getLayoutParams();
                    firstChildTop = firstChild.getTop() - layoutParams.topMargin - getRecyclerViewItemTopInset(layoutParams) - recyclerView.getPaddingTop();
                }

                if (layoutManager.findFirstCompletelyVisibleItemPosition() < 1 && firstChildTop == 0) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 通过反射获取RecyclerView的item的topInset
     *
     * @param layoutParams
     * @return
     */
    private int getRecyclerViewItemTopInset(RecyclerView.LayoutParams layoutParams) {
        try {
            Field field = RecyclerView.LayoutParams.class.getDeclaredField("mDecorInsets");
            field.setAccessible(true);
            // 开发者自定义的滚动监听器
            Rect decorInsets = (Rect) field.get(layoutParams);
            return decorInsets.top;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private boolean isRecyclerViewToBottom(RecyclerView recyclerView) {
        if (recyclerView != null) {
            RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
            if (manager == null || manager.getItemCount() == 0) {
                return false;
            }

            if (manager instanceof LinearLayoutManager) {
                // 处理高度超过一屏幕时的情况
                View lastVisibleChild = recyclerView.getChildAt(recyclerView.getChildCount() - 1);
                if (lastVisibleChild != null && lastVisibleChild.getMeasuredHeight() >= recyclerView.getMeasuredHeight()) {
                    if (android.os.Build.VERSION.SDK_INT < 14) {
                        return !(ViewCompat.canScrollVertically(recyclerView, 1) || recyclerView.getScrollY() < 0);
                    } else {
                        return !ViewCompat.canScrollVertically(recyclerView, 1);
                    }
                }

                LinearLayoutManager layoutManager = (LinearLayoutManager) manager;
                if (layoutManager.findLastCompletelyVisibleItemPosition() == layoutManager.getItemCount() - 1) {
                    // 处理BGAStickyNavLayout中findLastCompletelyVisibleItemPosition失效问题
                    View lastCompletelyVisibleChild = layoutManager.getChildAt(layoutManager.findLastCompletelyVisibleItemPosition());
                    if (lastCompletelyVisibleChild == null) {
                        return true;
                    } else {
                        // 0表示x，1表示y
                        int[] location = new int[2];
                        lastCompletelyVisibleChild.getLocationOnScreen(location);
                        int lastChildBottomOnScreen = location[1] + lastCompletelyVisibleChild.getMeasuredHeight();
                        this.getLocationOnScreen(location);
                        int stickyNavLayoutBottomOnScreen = location[1] + getMeasuredHeight();
                        return lastChildBottomOnScreen <= stickyNavLayoutBottomOnScreen;

                    }
                }
            } else if (manager instanceof StaggeredGridLayoutManager) {
                StaggeredGridLayoutManager gridLayoutManager = (StaggeredGridLayoutManager) manager;

                int[] out = gridLayoutManager.findLastCompletelyVisibleItemPositions(null);
                int lastPosition = gridLayoutManager.getItemCount() - 1;
                for (int position : out) {
                    if (position == lastPosition) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private class MenuItemDecoration extends RecyclerView.ItemDecoration {
        private int itemSpaceVertical;

        MenuItemDecoration(int itemSpaceVertical) {
            this.itemSpaceVertical = itemSpaceVertical;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            if (parent.getChildLayoutPosition(view) == 0) {
                outRect.top = 0;
            } else {
                outRect.top = itemSpaceVertical;
            }
        }
    }
}
