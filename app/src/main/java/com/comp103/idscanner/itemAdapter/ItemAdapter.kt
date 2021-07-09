/**
 * Author: Isaac Young
 */
package com.comp103.idscanner.itemAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.comp103.idscanner.databinding.MainRvItemBinding
import java.util.*
import java.util.function.Consumer

/**
 * Empty item adapter.
 *
 * @return the item adapter
 */
fun emptyItemAdapter(): ItemAdapter {
    return ItemAdapter(ArrayList())
}

/**
 * Item Adapter for Items RecyclerView
 */
class ItemAdapter(items: ArrayList<String>) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {
    private var items: ArrayList<String>
    private var itemSet: HashSet<String>

    /**
     * Read only property for itemList
     */
    val itemList: List<String>
        get() = items.toList()


    /**
     * Provide a direct reference to each of the views within a data item
     */
    inner class ViewHolder(var g: MainRvItemBinding) : RecyclerView.ViewHolder(g.root), View.OnClickListener {
        lateinit var item: String

        init {
            g.root.setOnClickListener(this)
        }

        /**
         * Handles the row being clicked
         *
         * @param view the itemView
         */
        override fun onClick(view: View) {
            // TODO use MaterialDialogBuilder for editing/deleting entries
        }

    }

    /**
     * Reset.
     */
    fun reset() {
        items.forEach(Consumer<String> { notifyItemRemoved(0) })
        items = ArrayList()
    }

    /**
     * Add a item
     *
     * @param context  the context
     * @param item     the Item to add
     */
    fun addItem(item: String) {
        if (itemSet.contains(item) == false) {
            itemSet.add(item)
            items.add(0, item)
            notifyItemInserted(0)
        }
    }

    /**
     * Remove an item.
     *
     * @param item     the item to remove
     * @param position the position of the Item in the List
     */
    fun removeItem(item: String, position: Int) {
        itemSet.remove(item)
        items.remove(item)
    }

    /**
     * Usually involves inflating a layout from XML and returning the holder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        return ViewHolder(MainRvItemBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    /**
     * Populate the data into the ViewHolder
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Get the data model based on position
        holder.item = items[position]
        val g: MainRvItemBinding = holder.g
        g.idTV.text = holder.item
    }

    /**
     * Returns the total item count
     */
    override fun getItemCount(): Int {
        return items.size
    }

    /**
     * Pass in the tasks array into the Adapter
     *
     * @param items the items
     */
    init {
        // Try to preserve order if there are no duplicates
        this.items = ArrayList(items)
        this.itemSet = HashSet(items)
        // Otherwise make a new ArrayList from the HashSet
        if (this.items.size != this.itemSet.size) {
            this.items = ArrayList(itemSet)
        }
    }
}
