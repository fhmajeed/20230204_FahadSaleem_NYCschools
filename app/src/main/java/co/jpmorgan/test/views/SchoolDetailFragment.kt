package co.jpmorgan.test.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import co.jpmorgan.test.R
import co.jpmorgan.test.databinding.FragmentSchoolDetailBinding
import co.jpmorgan.test.viewmodels.SchoolViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SchoolDetailFragment : Fragment() {

    private val viewModel: SchoolViewModel by viewModels()
    private val args: SchoolDetailFragmentArgs by navArgs()

    private var _binding: FragmentSchoolDetailBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSchoolDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    launch {
                        viewModel.schoolDetailSate.collectLatest { state ->
                            if(state.error.isNotEmpty()) showError(state.error)
                            progressCircular.isGone = !state.isLoading
                            state.data?.run {
                                val schoolName = schoolName
                                val avgReadScore =
                                    getString(R.string.sat_avg_reading) + readingSATScore
                                val avgMathScore =
                                    getString(R.string.sat_avg_math) + mathSATScore
                                val avgWriteScore =
                                    getString(R.string.sat_avg_writing) + writingSATScore
                                binding.schoolNameTV.text = schoolName
                                binding.satAvgReadScoreTV.text = avgReadScore
                                binding.satAvgMathScoreTV.text = avgMathScore
                                binding.satAvgWritingScoreTV.text = avgWriteScore
                            }
                        }
                    }
                    launch {
                        viewModel.schoolState.collectLatest {
                            if(savedInstanceState == null) {
                                it.data?.let { list ->
                                    Toast.makeText(context,"Retrieving school list of = ${list.size}",Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                }
            }
            //savedInstanceState cannot be null upon orientation, so check upon call.
            if(savedInstanceState == null ){
                viewModel.getSchoolDetail(args.dbn)
            }
        }
    }

    private fun showError(string: String) {
        val snackBar = Snackbar.make(
            binding.root,
            string,
            Snackbar.LENGTH_LONG
        ).setAction(getString(R.string.retry)) {
            viewModel.getSchoolDetail(args.dbn)
        }
        snackBar.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}