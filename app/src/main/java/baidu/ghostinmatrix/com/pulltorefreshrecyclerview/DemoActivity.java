package baidu.ghostinmatrix.com.pulltorefreshrecyclerview;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shanjie on 2017/4/24.
 */


public class DemoActivity extends Activity implements DemoContract.ViewContract, PullToRefreshRecyclerView.OnRefreshListener {

    PullToRefreshRecyclerView mRecyclerView;
    DemoPresenter mPresenter;
    ComRecyclerViewAdapter<String> innerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity_content_list);
        mRecyclerView = (PullToRefreshRecyclerView) findViewById(R.id.recycler_view);
        innerAdapter = new ComRecyclerViewAdapter<String>(this, R.layout.test_item_content_list, initData()) {

            @Override
            public void convert(ComViewHolder holder, String data, int type, int position) {
                TextView titleTv = holder.getView(R.id.title);
                titleTv.setText(data);
            }

        };

        HeaderFooterRecyclerViewAdapter headerFooterRecyclerViewAdapter = new HeaderFooterRecyclerViewAdapter(this, innerAdapter);
        View headerView = View.inflate(this, R.layout.test_header, null);
        headerFooterRecyclerViewAdapter.addHeaderView(headerView);
        mRecyclerView.setAdapter(headerFooterRecyclerViewAdapter);
        mRecyclerView.getPullableRecyclerView().notifyNetState(NetStateView.DATA_STATUS_ERROR);
//        mRecyclerView.setAdapter(innerAdapter);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback((ItemTouchHelperAdapter) mRecyclerView.getAdapter(), true, true);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView.getPullableRecyclerView().getRecyclerView());
        mRecyclerView.setOnRefreshListener(this);
        mPresenter = new DemoPresenter(this);
    }


    @Override
    public void onDataCome(List<String> data, int netState) {
        mRecyclerView.getPullableRecyclerView().notifyNetState(netState);
        mRecyclerView.refreshFinish(PullToRefreshLayout.DONE);
//        innerAdapter.setGroup(data);
//        ((HeaderFooterRecyclerViewAdapter) mRecyclerView.getPullableRecyclerView().getAdapter()).setGroup(data);
    }

    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        mPresenter.getData();
    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {

    }

    @Override
    protected void onDestroy() {
        mRecyclerView.setOnRefreshListener(null);
        super.onDestroy();
    }

    public List<String> initData() {
        List<String> titles = new ArrayList<>();
        titles.add("title1");
        titles.add("title2");
        titles.add("title3");
        titles.add("title4");
        titles.add("title5");
        titles.add("title6");
        titles.add("title7");
        titles.add("title8");
        titles.add("title9");
        titles.add("title10");
        return titles;
    }
}
