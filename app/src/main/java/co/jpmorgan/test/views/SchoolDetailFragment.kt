package co.jpmorgan.test.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import co.jpmorgan.test.R
import co.jpmorgan.test.databinding.FragmentSchoolDetailBinding
import co.jpmorgan.test.models.SchoolDetail
import co.jpmorgan.test.viewmodels.SchoolListViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SchoolDetailFragment : Fragment() {

    private val viewModel: SchoolListViewModel by viewModels()
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
            viewModel.schoolDetailLiveData.observe(viewLifecycleOwner) { schoolDetail: SchoolDetail? ->
                schoolDetail?.let {
                    val schoolName = getString(R.string.name) + schoolDetail.schoolName
                    val avgReadScore = getString(R.string.sat_avg_reading) + schoolDetail.readingSATScore
                    val avgMathScore = getString(R.string.sat_avg_math) + schoolDetail.mathSATScore
                    val avgWriteScore = getString(R.string.sat_avg_writing) + schoolDetail.writingSATScore
                    binding.schoolNameTV.text = schoolName
                    binding.satAvgReadScoreTV.text = avgReadScore
                    binding.satAvgMathScoreTV.text = avgMathScore
                    binding.satAvgWritingScoreTV.text = avgWriteScore
                }
            }

            viewModel.showProgress.observe(viewLifecycleOwner) { isInProgress ->
                progressCircular.isGone = !isInProgress
            }
            viewModel.errorMessage.observe(viewLifecycleOwner) { error ->
                val snackBar = Snackbar.make(
                    binding.root,
                    error,
                    Snackbar.LENGTH_LONG
                )
                snackBar.show()
            }

            viewModel.getSchoolDetail(args.dbn)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}