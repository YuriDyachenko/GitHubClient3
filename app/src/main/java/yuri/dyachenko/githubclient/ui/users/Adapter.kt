package yuri.dyachenko.githubclient.ui.users

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import yuri.dyachenko.githubclient.R
import yuri.dyachenko.githubclient.databinding.UserItemLayoutBinding
import yuri.dyachenko.githubclient.loadImage
import yuri.dyachenko.githubclient.model.User

class Adapter(private val presenter: Presenter) : RecyclerView.Adapter<Adapter.ViewHolder>() {

    private var users: MutableList<User> = mutableListOf()

    fun submitList(list: List<User>) {
        val diffResult = DiffUtil.calculateDiff(DiffCallback(users, list), false)
        users.clear()
        users.addAll(list)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.user_item_layout, parent, false) as View
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(users[position])
    }

    override fun getItemCount() = users.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = UserItemLayoutBinding.bind(view)

        fun bind(user: User) = with(binding) {
            itemView.apply {
                userLoginTextView.text = user.login
                userAvatarImageView.loadImage(
                    user.avatarUrl,
                    R.drawable.ic_launcher_background,
                    R.drawable.ic_launcher_foreground
                )
                setOnClickListener { presenter.onItemClicked(user.login) }
            }
        }
    }
}