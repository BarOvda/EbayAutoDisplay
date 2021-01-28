package com.example.ebayautodisplayllibrary;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import android.location.Location;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ebayautodisplayllibrary.logic.IProcess;
import com.example.ebayautodisplayllibrary.logic.ebay_plugin.EbayDriver;
import com.example.ebayautodisplayllibrary.logic.ebay_plugin.EbayListAdapter;

public class EbayDisplayFragment extends FrameLayout {
    private static final String TAG = "Scroll";
    private static final int PAGINATION_FLAG = 4;
    private static final int NUMBER_OF_ITEMS_IN_A_ROW = 2;
    private static final int NUMBER_OF_ITEMS_PER_LOAD = 16;
    private int categoryId;
    private Drawable backgroundColor;
    private Drawable cardBackgroundColor;
    private int textColor;
    public enum orientation {vertical,horizontal}
    public enum style {classic,modern}
    private Location location;
    private String keyWord;
    private LinearLayoutManager ebayLinearLayoutManager;
    private boolean isDriverAvailable = true;
    private RecyclerView EbayTitleRecyclerView;
    private EbayDriver driver;
    private ProgressBar progressBar;
    private Context context;
    private String ebayAppID;
    private EbayListAdapter ebayListAdapter;
    private orientation view_direction;
    private style style;
    int pageNumber = 1;
    private NestedScrollView scroller;
    private final IProcess mProcess = new IProcess() {
        @Override
        public void updateAdapter() {
            isDriverAvailable=true;
            if(pageNumber==1) {
                progressBar.setVisibility(View.GONE);
                ebayListAdapter.notifyDataSetChanged();
            }
            else {
                ebayListAdapter.addToExistingList(driver.getTitles());
            }
        }
    };
    public EbayDisplayFragment(Context context) {
        super(context);
        this.context = context;
    }

    public EbayDisplayFragment(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initAttars(attrs);
    }
    public EbayDisplayFragment(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initAttars(attrs);
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public EbayDisplayFragment(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        initAttars(attrs);
    }
    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public void initEbayAutoDisplay(String ebayAppID,String keyWord){
        this.ebayAppID = ebayAppID;
        this.keyWord = keyWord;
        this.categoryId=-1;
        init(this.context);
    }

    public void initEbayAutoDisplay(String ebayAppID,String keyWord,int categoryId){
        this.ebayAppID = ebayAppID;
        this.keyWord = keyWord;
        this.categoryId=categoryId;
        init(this.context);

    }
    public void initEbayAutoDisplay(String ebayAppID, String keyWord, int categoryId, Location location){
        this.ebayAppID = ebayAppID;
        this.keyWord = keyWord;
        this.categoryId=categoryId;
        this.location = location;
        init(this.context);

    }
    public void initEbayAutoDisplay(String ebayAppID,String keyWord,Location location){
        this.ebayAppID = ebayAppID;
        this.keyWord = keyWord;
        this.categoryId=categoryId;
        this.location = location;
        init(this.context);

    }
    void initAttars(AttributeSet attributeSet){
        TypedArray a = getContext().obtainStyledAttributes(attributeSet, R.styleable.EbayDisplayFragment, 0, 0);
        try {
            if (attributeSet != null) {
                this.view_direction =orientation.values()[a.getInt(R.styleable.EbayDisplayFragment_display_view_orientation,0)];
                this.style =style.values()[a.getInt(R.styleable.EbayDisplayFragment_view_style,0)];
                this.backgroundColor = a.getDrawable(R.styleable.EbayDisplayFragment_view_background_color);
                this.cardBackgroundColor = a.getDrawable(R.styleable.EbayDisplayFragment_card_background_color);
                this.textColor = a.getColor(R.styleable.EbayDisplayFragment_text_color,getResources().getColor(R.color.deafult_text_color));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            a.recycle();
        }
    }
    void init(Context context){
        View view = View.inflate(context, R.layout.fragment_page_ebay, this);
        EbayTitleRecyclerView = view.findViewById(R.id.ebay_recyclerview);
        scroller = findViewById(R.id.nested_scroll_view);
        progressBar = view.findViewById(R.id.progressBar2);
        EbayTitleRecyclerView.setHasFixedSize(true);
        scroller.setBackground(backgroundColor);
        setPaginationFormat();

        driver = new EbayDriver(context,ebayAppID,keyWord,categoryId,location,mProcess);
        try {
            driver.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ebayListAdapter = new EbayListAdapter(EbayDisplayFragment.this.getContext(),driver.getTitles(), this.style.ordinal(),textColor,cardBackgroundColor,this.view_direction.ordinal());
        if(this.view_direction==orientation.horizontal) {
            EbayTitleRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false));
        }
        else{
            EbayTitleRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), NUMBER_OF_ITEMS_IN_A_ROW));
        }
        EbayTitleRecyclerView.setAdapter(ebayListAdapter);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.bringToFront();

    }
    private void setPaginationFormat(){
        if(this.view_direction==orientation.horizontal) {
            EbayTitleRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    if (!isDriverAvailable)
                        return;
                    int visibleItemCount = recyclerView.getLayoutManager().getChildCount();
                    int totalItemCount = recyclerView.getLayoutManager().getItemCount();
                    int pastVisibleItems = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                    if (pastVisibleItems + visibleItemCount+PAGINATION_FLAG >= totalItemCount) {
                        pageNumber++;
                        isDriverAvailable = false;
                        driver = new EbayDriver(context, ebayAppID, keyWord, categoryId, location, mProcess);
                        driver.setPage(pageNumber);
                        driver.execute();
                    }
                }

            });
        }else {
            scroller.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
                if (scrollY >= (v.getChildAt(0).getMeasuredHeight()-(v.getMeasuredHeight()*PAGINATION_FLAG))&&isDriverAvailable) {
                    pageNumber++;
                    isDriverAvailable=false;
                        driver = new EbayDriver(context,ebayAppID, keyWord,categoryId,location, mProcess);
                        driver.setPage(pageNumber);
                        driver.execute();
                }
            });
        }
    }

}