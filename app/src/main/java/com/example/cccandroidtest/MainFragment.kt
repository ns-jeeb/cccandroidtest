package com.example.cccandroidtest

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.cccandroidtest.databinding.MainFragmentBinding


class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: MainFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        context?.let {
            viewModel.getEstimateData(it).observe(viewLifecycleOwner, Observer {

                binding.txtCompanyName.text = it?.company
                binding.txtAddress.text = it?.address
                binding.txtEstNum.text = it?.number.toString()
                binding.txtCreated.text = it?.created_date
                binding.txtRevisionNum.text = it?.revision_number.toString()
            })
        }
        context?.let {
            viewModel.getPersonData(it).observe(viewLifecycleOwner, Observer {
                binding.txtRequester.text = "${it.first_name} ${it.last_name}"
                binding.txtCreatedBy.text = "${it.first_name} ${it.last_name}"
                binding.txtContact.text = "${it.first_name} ${it.last_name}"

        }) }

    }
}