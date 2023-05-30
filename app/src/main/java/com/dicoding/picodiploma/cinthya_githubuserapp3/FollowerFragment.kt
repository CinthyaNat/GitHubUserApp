package com.dicoding.picodiploma.cinthya_githubuserapp3

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.cinthya_githubuserapp3.databinding.FragmentFollowerBinding
import com.dicoding.picodiploma.cinthya_githubuserapp3.ui.main.DetailActivity
import com.google.android.material.snackbar.Snackbar


class FollowerFragment : Fragment() {
    private lateinit var binding: FragmentFollowerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowerBinding.inflate(inflater, container, false)
        return binding.root    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = arguments?.getString(ARG_NAME)
        val fragmentViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowFragmentViewModel::class.java)
        fragmentViewModel.getFollower(username.toString())

        if (this.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.recyclerUser.layoutManager = GridLayoutManager(requireActivity(), 2)
        } else {
            binding.recyclerUser.layoutManager = LinearLayoutManager(requireActivity())
        }

        fragmentViewModel.listFollower.observe(requireActivity(), { currentUser ->
            setReviewData(currentUser)
        })

        fragmentViewModel.isLoading.observe(requireActivity(),{
            showLoading(it)
        })

        fragmentViewModel.snackbarFollowerError.observe(requireActivity(), {
            it.getContentIfNotHandled()?.let { snackBarText ->
                Snackbar.make(
                    requireActivity().findViewById(android.R.id.content),
                    snackBarText,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun setReviewData(UserList: List<ItemsItem>) {
        val userAdapter = UserAdapter(UserList)
        binding.recyclerUser.adapter = userAdapter

        userAdapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: String) {
                val intentToDetail = Intent(requireActivity(), DetailActivity::class.java)
                intentToDetail.putExtra("DATA", data)
                startActivity(intentToDetail)
            }
        })

    }

    companion object{
        const val ARG_NAME="DATA_FOLLOW"
    }

}