package co.jpmorgan.test.views

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import co.jpmorgan.test.adapters.SchoolListAdapter
import co.jpmorgan.test.databinding.FragmentSchoolListBinding
import co.jpmorgan.test.models.School
import co.jpmorgan.test.viewmodels.SchoolListViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SchoolListFragment : Fragment() {

    private val viewModel: SchoolListViewModel by viewModels()

    private var _binding: FragmentSchoolListBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSchoolListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            val schoolListAdapter = SchoolListAdapter {school -> adapterOnClick(school) }
            recyclerView.adapter = schoolListAdapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext())

            viewModel.listOfSchoolLiveData.observe(viewLifecycleOwner) { list ->
                schoolListAdapter.submitList(list)
            }

            viewModel.showProgress.observe(viewLifecycleOwner){ isInProgress ->
                progressCircular.isGone = !isInProgress
            }
            viewModel.errorMessage.observe(viewLifecycleOwner){ error ->
                val snackBar = Snackbar.make(
                    binding.root,
                    error,
                    Snackbar.LENGTH_LONG
                )
                snackBar.show()
            }

            refresh.setOnRefreshListener{
                viewModel.getSchoolList()
                refresh.isRefreshing = false
            }

        }
    }

    /* Opens SchoolItemFragment when RecyclerView item is clicked. */
    private fun adapterOnClick(school: School) {
        /*findNavController().navigate(R.id.action_myHomeFragment_to_mySecondFragment)})*/
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}