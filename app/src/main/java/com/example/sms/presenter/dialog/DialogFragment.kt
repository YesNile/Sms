package com.example.sms.presenter.dialog

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.sms.R
import com.example.sms.databinding.FragmentDialogBinding

class DialogFragment : Fragment(R.layout.fragment_dialog) {

    private val args: DialogFragmentArgs by navArgs()
    private val binding: FragmentDialogBinding by viewBinding()
    private val viewModel: DialogViewModel by viewModels()
    private val adapter = DialogAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.dialogMessages.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        initializeUI()
        initializeRecycler()
        viewModel.getDialogueMessages(args.address, args.messages.toList())
    }

    private fun initializeRecycler() = with(binding.smsListRecycler) {
        layoutManager = LinearLayoutManager(requireContext())
        adapter = this@DialogFragment.adapter
        addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                LinearLayoutManager.VERTICAL
            )
        )
    }

    private fun initializeUI() {
        initializeRecycler()
    }
}