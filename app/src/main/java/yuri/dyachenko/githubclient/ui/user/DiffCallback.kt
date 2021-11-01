package yuri.dyachenko.githubclient.ui.user

import androidx.recyclerview.widget.DiffUtil
import yuri.dyachenko.githubclient.model.Repo

class DiffCallback(
    private val oldList: List<Repo>,
    private val newList: List<Repo>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].id == newList[newItemPosition].id
}