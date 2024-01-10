package com.example.sms.presenter.sms

import android.Manifest.permission.READ_SMS
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.sms.R
import com.example.sms.data.DataMessage
import com.example.sms.databinding.FragmentSmsListBinding
import com.example.sms.requirePermission

class SmsFragment : Fragment(R.layout.fragment_sms_list) {

    private val binding: FragmentSmsListBinding by viewBinding()
    private val viewModel: SmsViewModel by viewModels()
    private val adapter = SmsAdapter(
        ::onChatItemClick,
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.chatMessageEntries.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        initializeUI()
        initializeRecycler()
        requireSmsPermission()
    }

    private fun initializeUI() {
        initializeRecycler()
        showFailureMessage(false)
    }

    private fun requireSmsPermission() {
        requirePermission(
            permission = READ_SMS,
            successDelegate = {
                viewModel.loadSmsMessages(requireContext().contentResolver)
            },
            failureDelegate = {
                showFailureMessage(true)
            }
        )
    }

    private fun showFailureMessage(shown: Boolean) {
        val visibility = if(shown) View.VISIBLE else View.GONE
        binding.failSmsImageView.visibility = visibility
        binding.failSmsTitle.visibility = visibility
        binding.failSmsDescription.visibility = visibility
        binding.smsListRecycler.visibility = if (shown) View.GONE else View.VISIBLE
    }

    private fun onChatItemClick(item: DataMessage) {
        val action = SmsFragmentDirections.actionFragmentOneToFragmentTwo(
            address = item.address,
            messages = item.messages.toTypedArray()
        )
        findNavController().navigate(action)
    }

    private fun initializeRecycler() = with(binding.smsListRecycler) {
        layoutManager = LinearLayoutManager(requireContext())
        adapter = this@SmsFragment.adapter
        addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                LinearLayoutManager.VERTICAL
            )
        )
    }
}