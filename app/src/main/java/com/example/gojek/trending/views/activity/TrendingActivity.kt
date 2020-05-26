package com.example.gojek.trending.views.activity

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gojek.BR
import com.example.gojek.R
import com.example.gojek.base.BaseActivity
import com.example.gojek.databinding.ActivityTrendingBinding
import com.example.gojek.extensions.gone
import com.example.gojek.extensions.isGonee
import com.example.gojek.extensions.isVisible
import com.example.gojek.extensions.visible
import com.example.gojek.trending.viewmodel.TrendingActivityViewModel
import com.example.gojek.trending.views.adapter.TrendingDataAdapter
import kotlinx.android.synthetic.main.activity_trending.*
import kotlinx.android.synthetic.main.layout_on_error.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class TrendingActivity : BaseActivity<ActivityTrendingBinding, TrendingActivityViewModel>() {

    override val bindingVariable: Int
        get() = BR._all
    override val layoutId: Int
        get() = R.layout.activity_trending

    @Inject
    override lateinit var viewModel: TrendingActivityViewModel
        internal set
    var activityTrendingBinding: ActivityTrendingBinding? = null
    private lateinit var trendingDataAdapter: TrendingDataAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityTrendingBinding = viewDataBinding
        init()
    }

    private fun init() {
        initToolBar()
        setSwipeToRefreshLayout()
        setAdapter()
        getData(false)
        setLiveDataListeners()
        handleClickListeners()
    }

    private fun initToolBar() {
        setSupportActionBar(toolBar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
        toolBar?.setNavigationOnClickListener {
            finish()
        }
    }

    private fun setSwipeToRefreshLayout() {
        swipeToRefreshLayout?.apply {
            setColorSchemeResources(R.color.tunDora)
            setOnRefreshListener {
                getData(true)
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.trending_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.txtSortByStar -> {
            viewModel.sortByStar(swipeToRefreshLayout?.isRefreshing ?: false)
            true
        }
        R.id.txtSortByName -> {
            viewModel.sortByName(swipeToRefreshLayout?.isRefreshing ?: false)
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private fun setAdapter() {
        trendingDataAdapter = TrendingDataAdapter(this@TrendingActivity)
        rvTrends?.apply {
            layoutManager = LinearLayoutManager(this@TrendingActivity)
            adapter = trendingDataAdapter
            setHasFixedSize(true)
        }
    }

    private fun getData(isPullToRefresh: Boolean) {
        if (checkForInternetAvailability()) {
            if (!isPullToRefresh) {
                sfLayout?.apply {
                    visible()
                    startShimmer()
                }
                if (rvTrends?.isVisible == true) {
                    rvTrends?.gone()
                }
            } else {
                disableRecyclerViewFrame?.visible()
            }
            viewModel.getGithubRepositoryData(isPullToRefresh)
        } else {
            if (!isPullToRefresh) {
                if (layoutOnError?.isGonee() == true) {
                    layoutOnError?.visible()
                }
            } else {
                swipeToRefreshLayout?.isRefreshing = false
                showInternetAvailableError()
            }
        }
    }


    private fun handleClickListeners() {
        layoutOnError.btnRetry?.setOnClickListener {
            getData(false)
        }
    }

    private fun setLiveDataListeners() {
        viewModel.githubRepositoryLiveData.observe(this, Observer {
            GlobalScope.launch(Dispatchers.Main) {
                swipeToRefreshLayout?.apply {
                    visible()
                    isRefreshing = false
                }
                if (layoutOnError?.isVisible() == true) {
                    layoutOnError?.gone()
                }
                if (disableRecyclerViewFrame?.isVisible == true) {
                    disableRecyclerViewFrame?.gone()
                }
                sfLayout?.apply {
                    stopShimmer()
                    hideShimmer()
                    gone()
                }
                rvTrends?.visible()
                if (it.isNotEmpty()) {
                    trendingDataAdapter.updateData(it)
                } else {
                    txtNoDataFound?.visible()
                }
            }

        })
        viewModel.errorString.observe(this, Observer {
            Log.e("error", it)
            GlobalScope.launch(Dispatchers.Main) {
                if (disableRecyclerViewFrame?.isVisible == true) {
                    disableRecyclerViewFrame?.gone()
                }
                swipeToRefreshLayout?.apply {
                    isRefreshing = false
                    gone()
                }

                sfLayout?.apply {
                    stopShimmer()
                    hideShimmer()
                    gone()
                }
                if (rvTrends?.isVisible() == true) {
                    rvTrends?.gone()
                }
                if (layoutOnError?.isGonee() == true) {
                    layoutOnError?.visible()
                }

            }

        })
    }

}
