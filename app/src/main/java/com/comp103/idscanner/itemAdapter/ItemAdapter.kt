/**
 * @author Isaac Young
 */
package com.comp103.idscanner.itemAdapter

import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.comp103.idscanner.Id
import com.comp103.idscanner.R
import com.comp103.idscanner.activities.MainActivity
import com.comp103.idscanner.databinding.MainRvItemBinding
import com.comp103.idscanner.databinding.ManualInputBinding
import com.comp103.idscanner.factories.getManualAddTextWatcher
import com.comp103.idscanner.assertions.assert
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.*
import java.util.function.Consumer

/**
 * Item Adapter for Items RecyclerView
 */
class ItemAdapter(private var context: Context, items: ArrayList<Id>) :
    RecyclerView.Adapter<ItemAdapter.ViewHolder>() {
    private var items: ArrayList<Id>
    private var itemSet: HashSet<Id>

    /**
     * Read only property for itemList
     */
    val itemList: List<Id>
        get() = items.toList()


    /**
     * Provide a direct reference to each of the views within a data item
     */
    inner class ViewHolder(var g: MainRvItemBinding) : RecyclerView.ViewHolder(g.root),
        View.OnClickListener {
        lateinit var item: Id

        init {
            g.root.setOnClickListener(this)
        }

        /**
         * Handles the row being clicked
         *
         * @param view the itemView
         */
        override fun onClick(view: View) {
            val builder = MaterialAlertDialogBuilder(context, R.style.MaterialAlertDialog_Rounded)
            val manualG: ManualInputBinding =
                ManualInputBinding.inflate((context as MainActivity).layoutInflater)

            manualG.editText.setText(item.idString)
            manualG.editText.requestFocus()

            builder.setMessage("Student ID:")
                .setView(manualG.root)
                .setPositiveButton(
                    "Ok"
                ) { _: DialogInterface?, _: Int ->
                    item.idString = manualG.editText.text.toString()
                    notifyItemChanged(items.indexOf(item))
                    (context as MainActivity).saveData()
                }
                .setNegativeButton("Cancel") { _: DialogInterface?, _: Int ->
                    // User cancelled
                }
                .setNeutralButton("Delete") { _: DialogInterface?, _: Int ->
                    (context as MainActivity).removeItem(item)
                }

            val dialog = builder.create()
            dialog.show()
            dialog.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
            val okButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            okButton.isEnabled = true
            manualG.editText.addTextChangedListener(
                getManualAddTextWatcher(
                    manualG.editText,
                    okButton
                )
            )
        }

    }

    /**
     * Reset.
     */
    fun reset() {
        items.forEach { _ -> notifyItemRemoved(0) }
        items.clear()
        itemSet.clear()
    }

    /**
     * Add a item
     *
     * @param item     the Item to add
     */
    fun addItem(item: Id) {
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
     */
    fun removeItem(item: Id) {
        notifyItemRemoved(items.indexOf(item))
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
        g.idTV.text = holder.item.idString
    }

    /**
     * Returns the total item count
     */
    override fun getItemCount(): Int {
        assert(items.size == itemSet.size, "Item set size mismatch")
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
