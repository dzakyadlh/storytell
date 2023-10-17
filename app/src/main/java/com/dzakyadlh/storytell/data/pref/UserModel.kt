package com.dzakyadlh.storytell.data.pref

data class UserModel(
    var email: String,
    var token: String,
    var isLogin: Boolean = false
)
