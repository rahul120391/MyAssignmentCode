package com.example.gojek.trending.viewmodel

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import com.example.gojek.model.GithubAccountDataModelItem
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class TrendingAdapterViewModel(githubAccountDataModelItem: GithubAccountDataModelItem) {

    val repoAuthorName = MutableLiveData<String>()
    val repDescription = MutableLiveData<String>()
    val repUrl = MutableLiveData<String>()
    val repLanguage = MutableLiveData<String>()
    val repStarCount = MutableLiveData<Int>()
    val repForks = MutableLiveData<Int>()
    val repoAuthorImageUrl = MutableLiveData<String>()
    val dividerVisibility = MutableLiveData<Int>()
    val descriptionRatingLayoutVisibility = MutableLiveData<Int>()
    val backgroundViewVisibility = MutableLiveData<Int>()
    val itemPosition = MutableLiveData<Int>()

    init {
        repoAuthorName.value = githubAccountDataModelItem.name
        repDescription.value = githubAccountDataModelItem.author
        repUrl.value = githubAccountDataModelItem.url
        repLanguage.value = githubAccountDataModelItem.language
        repStarCount.value = githubAccountDataModelItem.stars
        repForks.value = githubAccountDataModelItem.forks
        repoAuthorImageUrl.value = githubAccountDataModelItem.avatar
        if (githubAccountDataModelItem.isExpanded) {
            dividerVisibility.value = View.GONE
            descriptionRatingLayoutVisibility.value = View.VISIBLE
            backgroundViewVisibility.value = View.VISIBLE
        } else {
            dividerVisibility.value = View.VISIBLE
            descriptionRatingLayoutVisibility.value = View.GONE
            backgroundViewVisibility.value = View.GONE
        }
    }

    companion object {
        @BindingAdapter("imageUrl")
        @JvmStatic
        fun setImageUsingUrl(imageView: CircleImageView, url: String?) {
            if(url!=null){
                Picasso.get().load(url).into(imageView)
            }

        }
    }

    fun onCollapsedViewClick(position: Int) {
        itemPosition.value = position
    }

}