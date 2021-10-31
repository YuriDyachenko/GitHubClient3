package yuri.dyachenko.githubclient.ui.user

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import yuri.dyachenko.githubclient.R
import yuri.dyachenko.githubclient.databinding.RepoItemLayoutBinding
import yuri.dyachenko.githubclient.model.Repo

class Adapter(private val presenter: Presenter) : RecyclerView.Adapter<Adapter.ViewHolder>() {

    private var repos: MutableList<Repo> = mutableListOf()

    fun submitList(list: List<Repo>) {
        val diffResult = DiffUtil.calculateDiff(DiffCallback(repos, list), false)
        repos.clear()
        repos.addAll(list)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.repo_item_layout, parent, false) as View
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(repos[position])
    }

    override fun getItemCount() = repos.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = RepoItemLayoutBinding.bind(view)

        fun bind(repo: Repo) = with(binding) {
            itemView.apply {
                repoNameTextView.text = repo.name
                setOnClickListener { presenter.onItemClicked(repo.id) }
            }
        }
    }
}