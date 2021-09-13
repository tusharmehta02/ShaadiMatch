package com.shaadi.assignment.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.shaadi.assignment.data.model.local.ShaadiMatch
import com.shaadi.assignment.data.model.remote.MatchStatus
import com.shaadi.assignment.data.model.remote.ShaadiResultResponse
import com.shaadi.assignment.databinding.FragmentDashboardBinding
import com.shaadi.assignment.di.AppComponentInitializer
import com.shaadi.assignment.ui.adapter.MatchAdapter
import com.shaadi.assignment.ui.viewmodel.ShaadiMatchViewModel
import javax.inject.Inject

class DashboardFragment : Fragment(), MatchAdapter.MatchListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: ShaadiMatchViewModel by viewModels { viewModelFactory }

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: MatchAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AppComponentInitializer.getComponent().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpMatchesRv()
        viewModel.getShaadiMatches()
        observeViewModel()
    }

    private fun setUpMatchesRv() {
        adapter = MatchAdapter(requireContext(), this)
        binding.shaadiMatchViewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.shaadiMatchViewpager.adapter = adapter
        adapter.notifyDataSetChanged()
    }
    private fun observeViewModel() {
        viewModel.errorMessage.observe(
            viewLifecycleOwner,
            { message ->
                Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
            }
        )
        viewModel.isLoading.observe(
            viewLifecycleOwner,
            Observer { isLoading ->
                binding.progressbar.isVisible = isLoading
            }
        )
        viewModel.shaadiMatchList.observe(
            viewLifecycleOwner,
            {
                if (it != null) adapter.setMatches(it)
            }
        )
    }

    override fun scrollToNext(position: Int) {
        if (position < adapter.itemCount-1){
            binding.shaadiMatchViewpager.currentItem = position+1
        }
    }

    override fun onAccept(match: ShaadiResultResponse.Match) {
        val status = ShaadiMatch(match.login.uuid, MatchStatus.ACCEPT.status)
        viewModel.updateMatchStatus(status)
    }

    override fun onDecline(match: ShaadiResultResponse.Match) {
        val status = ShaadiMatch(match.login.uuid, MatchStatus.DECLINE.status)
        viewModel.updateMatchStatus(status)
    }
}
