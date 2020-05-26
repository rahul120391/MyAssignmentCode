package com.example.gojek.model

data class GithubAccountDataModelItem(
    val author: String,
    val avatar: String,
    val builtBy: List<BuiltBy>?=null,
    val currentPeriodStars: Int,
    val description: String,
    val forks: Int,
    val language: String,
    val languageColor: String,
    val name: String,
    val stars: Int,
    val url: String,
    var isExpanded:Boolean=false
)