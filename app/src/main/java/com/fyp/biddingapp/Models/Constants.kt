package com.fyp.biddingapp.Models

object Constants {
    private const val ROOT_URL = "http://192.168.18.61/Android/v1/"
    const val URL_REGISTER = ROOT_URL + "userRegister.php"
    const val URL_LOGIN = ROOT_URL + "userLogin.php"
    const val URL_DETAILS = ROOT_URL + "userDetails.php"
    const val URL_BID_DETAILS = ROOT_URL + "bidDetails.php"
    const val URL_GET_ALL_BIDS = ROOT_URL + "getAllBids.php"
    const val URL_IMAGES = ROOT_URL + "Images/"
    const val URL_FAVOURITE_BIDS = ROOT_URL + "favouriteBids.php"
    const val URL_GET_ALL_FAVOURITE = ROOT_URL + "getAllFavourite.php"
    const val URL_GET_ALL_USER_DATA = ROOT_URL + "getAllUserInfo.php"
    const val URL_UPDATE_USER_DATA = ROOT_URL + "updateUserInfo.php"
    const val URL_ENTER_BID_AMOUNT = ROOT_URL + "enterBidAmount.php"
    const val URL_GET_BIDDING_DATA = ROOT_URL + "getBiddingData.php"
    const val URL_GET_ALL_USER_BID = ROOT_URL + "getAllUserBids.php"
    const val URL_GET_BID_BY_CATEGORY = ROOT_URL + "getBidByCategory.php"

}