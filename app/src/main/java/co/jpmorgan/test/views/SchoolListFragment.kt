package co.jpmorgan.test.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import co.jpmorgan.test.R
import co.jpmorgan.test.adapters.SchoolListAdapter
import co.jpmorgan.test.databinding.FragmentSchoolListBinding
import co.jpmorgan.test.models.School
import co.jpmorgan.test.viewmodels.SchoolViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SchoolListFragment : Fragment() {

    private val viewModel: SchoolViewModel by viewModels()

    private var _binding: FragmentSchoolListBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSchoolListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            val schoolListAdapter = SchoolListAdapter { school -> adapterOnClick(school) }
            recyclerView.adapter = schoolListAdapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext())

            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    launch {
                        viewModel.schoolState.collectLatest { state ->
                            if (state.data?.isNotEmpty() == true) schoolListAdapter.submitList(state.data)
                            if (state.error.isNotEmpty()) showError(state.error)
                            progressCircular.isGone = !state.isLoading
                            refresh.isRefreshing = false
                        }
                    }
                }
            }
            refresh.setOnRefreshListener {
                viewModel.getSchoolList()
            }
        }
    }

    private fun showError(string: String) {
        val snackBar = Snackbar.make(
            binding.root, string, Snackbar.LENGTH_LONG
        ).setAction(getString(R.string.retry)) {
            viewModel.getSchoolList()
        }
        snackBar.show()
    }

    /**
     * Opens SchoolDetailFragment when RecyclerView item is clicked.
     */
    private fun adapterOnClick(school: School) {
        val action =
            SchoolListFragmentDirections.actionSchoolListFragmentToSchoolDetailFragment(school.dbn)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}