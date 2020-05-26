package com.example.gojek.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.gojek.R
import com.example.gojek.utils.NetworkConnectivityStatus
import com.google.android.material.snackbar.Snackbar
import dagger.android.AndroidInjection

abstract class BaseActivity<T : ViewDataBinding, V : BaseViewModel> : AppCompatActivity() {
    var viewDataBinding: T? = null
    private var mViewModel: V? = null

    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    abstract val bindingVariable: Int

    /**
     * @return layout resource id
     */
    @get:LayoutRes
    abstract val layoutId: Int

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    abstract val viewModel: V

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        performDataBinding()
    }

    private fun performDataBinding() {
        viewDataBinding = DataBindingUtil.setContentView(this, layoutId)
        this.mViewModel = if (mViewModel == null) viewModel else mViewModel
        viewDataBinding?.setVariable(bindingVariable, mViewModel)
        viewDataBinding?.executePendingBindings()
    }

    fun checkForInternetAvailability(): Boolean {
        return NetworkConnectivityStatus.getInstance(this).isNetworkAvailable()
    }

    fun showInternetAvailableError() {
        Snackbar.make(
            findViewById(android.R.id.content),
            getString(R.string.noInternetAvailable),
            Snackbar.LENGTH_LONG
        ).show()
    }


}