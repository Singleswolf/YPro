package com.zy.ypro.model.entity

/**
 * @Description: Created by yong on 2019/3/22 11:42.
 */
data class UserInfo(
    val isAdmin: Boolean,
    val email: String,
    val icon: String,
    val id: Int,
    val nickname: String,
    val password: String,
    val publicName: String,
    val token: String,
    val type: Int,
    val username: String,
    val chapterTops: List<*>,
    val collectIds: List<*>
)
//{
//    /**
//     * admin : false
//     * chapterTops : []
//     * collectIds : []
//     * email :
//     * icon :
//     * id : 39872
//     * nickname : yong
//     * password :
//     * publicName : yong
//     * token :
//     * type : 0
//     * username : yong
//     */
//    var isAdmin = false
//    var email: String? = null
//    var icon: String? = null
//    val id = 0,
//    val nickname: String,
//    val password: String,
//    val publicName: String,
//    val token: String,
//    val type = 0
//    val username: String,
//    val chapterTops: List<*>,
//    val collectIds: List<*>,
//}