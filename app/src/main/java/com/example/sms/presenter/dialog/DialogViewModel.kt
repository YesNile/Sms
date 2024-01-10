package com.example.sms.presenter.dialog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sms.data.Message

class DialogViewModel: ViewModel() {

    private val _dialogMessages = MutableLiveData<List<Message>>()
    val dialogMessages: LiveData<List<Message>>
        get() = _dialogMessages


    fun getDialogueMessages(address: String, messages: List<String>) {
        val dialogList = messages.map { message ->
            Message(address, message)
        }
        _dialogMessages.postValue(dialogList)
    }
}