package com.example.sms.presenter.sms

import android.content.ContentResolver
import android.provider.Telephony
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sms.data.DataMessage

class SmsViewModel: ViewModel() {

    private val _chatMessageEntries = MutableLiveData<List<DataMessage>>()
    val chatMessageEntries: LiveData<List<DataMessage>>
        get() = _chatMessageEntries


    fun loadSmsMessages(contentResolver: ContentResolver) {
        val cursor = contentResolver.query(
            Telephony.Sms.CONTENT_URI,
            null,
            null,
            null,
            null
        )

        val result = AddressAndMessageList(mutableListOf())

        if (cursor?.moveToFirst() == true) {
            do {
                val address = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.ADDRESS))
                val body = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.BODY))
                result.list.add(address to body)
            } while (cursor.moveToNext())
        }

        cursor?.close()

        _chatMessageEntries.postValue(result.toSmsChatEntriesList())
    }

}

@JvmInline
value class AddressAndMessageList(
    val list: MutableList<Pair<String, String>>
) {
    fun toSmsChatEntriesList(): List<DataMessage> =
        list
            .groupBy { it.first }
            .map {
                val messages = it.value.map { it.second }
                DataMessage(it.key, messages)
            }
}