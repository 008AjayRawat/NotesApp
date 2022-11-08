package com.app.notesapp.persistence

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "tbl_note")
class Note(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var title: String?,
    var description: String?,
    var tag: String?,
    var createdDate:String?,
    var updatedDate:String?
) : Parcelable {


    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
    )


    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(tag)
        parcel.writeString(createdDate)
        parcel.writeString(updatedDate)
    }

    // Method #3
    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Note> {
        override fun createFromParcel(parcel: Parcel): Note {
            return Note(parcel)
        }

        override fun newArray(size: Int): Array<Note?> {
            return arrayOfNulls(size)
        }
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + (title?.hashCode() ?: 0)
        result = 31 * result + (description?.hashCode() ?: 0)
        result = 31 * result + (tag?.hashCode() ?: 0)
        result = 31 * result + (createdDate?.hashCode() ?: 0)
        result = 31 * result + (updatedDate?.hashCode() ?: 0)
        return result
    }


}