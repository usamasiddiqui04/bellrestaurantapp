package com.fyp.biddingapp.dataclass

import android.os.Parcel
import android.os.Parcelable

public class BidListItem(
    val bidCategory: String,
    val bidDiscription: String,
    val bidDuration: String,
    val bidEndDate: String,
    val bidImage: String,
    val bidMinAmount: Int,
    val bidStartDate: String,
    val bidStatus: String,
    val bidTitle: String,
    val bidVerifiedAt: String,
    val id: Int,
    val userId: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString().toString(),
            parcel.readString().toString(),
            parcel.readString().toString(),
            parcel.readString().toString(),
            parcel.readString().toString(),
            parcel.readInt(),
            parcel.readString().toString(),
            parcel.readString().toString(),
            parcel.readString().toString(),
            parcel.readString().toString(),
            parcel.readInt(),
            parcel.readInt()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(bidCategory)
        parcel.writeString(bidDiscription)
        parcel.writeString(bidDuration)
        parcel.writeString(bidEndDate)
        parcel.writeString(bidImage)
        parcel.writeInt(bidMinAmount)
        parcel.writeString(bidStartDate)
        parcel.writeString(bidStatus)
        parcel.writeString(bidTitle)
        parcel.writeString(bidVerifiedAt)
        parcel.writeInt(id)
        parcel.writeInt(userId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BidListItem> {
        override fun createFromParcel(parcel: Parcel): BidListItem {
            return BidListItem(parcel)
        }

        override fun newArray(size: Int): Array<BidListItem?> {
            return arrayOfNulls(size)
        }
    }
}