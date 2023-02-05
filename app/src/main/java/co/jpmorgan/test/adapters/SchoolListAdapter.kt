package co.jpmorgan.test.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import co.jpmorgan.test.databinding.SchoolItemBinding
import co.jpmorgan.test.models.School

class SchoolListAdapter(private val onClick: (School) -> Unit) :
    ListAdapter<School, SchoolListAdapter.SchoolViewHolder>(SchoolDffCallBack) {

    /**
     * ViewHolder pattern for smooth scrolling and optimize memory performance.
     */
    class SchoolViewHolder(
        private val binding: SchoolItemBinding, private val onClick: (School) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        var mSchool: School? = null

        init {
            binding.root.setOnClickListener {
                mSchool?.let {
                    onClick(it)
                }
            }
        }

        fun bind(school: School) {
            mSchool = school
            with(binding) {
                schoolNameTV.text = school.schoolName
                emailTV.text = school.schoolEmail
                phoneNumberTV.text = school.phoneNumber
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SchoolViewHolder =
        SchoolViewHolder(
            SchoolItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), onClick
        )

    override fun onBindViewHolder(holder: SchoolViewHolder, position: Int) {
        val school = getItem(position)
        holder.bind(school)
    }

    object SchoolDffCallBack : DiffUtil.ItemCallback<School>() {
        override fun areItemsTheSame(oldItem: School, newItem: School): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: School, newItem: School): Boolean {
            return oldItem.dbn == newItem.dbn
        }

    }
}