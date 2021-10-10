package yuri.dyachenko.githubclient.ui.users

import androidx.recyclerview.widget.DiffUtil
import yuri.dyachenko.githubclient.model.User

class DiffCallback(
    private val oldList: List<User>,
    private val newList: List<User>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].login == newList[newItemPosition].login

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].login == newList[newItemPosition].login
}