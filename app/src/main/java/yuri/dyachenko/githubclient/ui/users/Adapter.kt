package yuri.dyachenko.githubclient.ui.users

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import yuri.dyachenko.githubclient.R
import yuri.dyachenko.githubclient.databinding.UserItemLayoutBinding
import yuri.dyachenko.githubclient.model.User

class Adapter(private val presenter: Presenter) : RecyclerView.Adapter<Adapter.ViewHolder>() {

    private var users: List<User> = listOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setUsers(list: List<User>) {
        users = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.user_item_layout, parent, false) as View
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(users[position].login)
    }

    override fun getItemCount() = users.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = UserItemLayoutBinding.bind(view)

        fun bind(login: String) = with(binding) {
            itemView.apply {
                userLoginTextView.text = login
                setOnClickListener { presenter.onItemClicked(login) }
            }
        }
    }
}