package breakingbbaddemoapp.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import breakingbbaddemoapp.R
import breakingbbaddemoapp.models.SimplifiedCharacterObject
import breakingbbaddemoapp.utils.StringFormatter
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.main_feed_list_item.view.*

// Main adapter used for managing main feed list within the main Recycler View
class CharactersListAdapter(val context: Context,
                            val stringFormatter: StringFormatter = StringFormatter(),
                            val clickListener: (Int) -> Unit
) : RecyclerView.Adapter<CharactersListAdapter.ViewHolder>() {

    private var items: List<SimplifiedCharacterObject> = ArrayList()

    fun setItems(sections: List<SimplifiedCharacterObject>) {
        this.items = sections
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val sectionView = inflater.inflate(R.layout.main_feed_list_item, parent, false)
        return ItemViewHolder(sectionView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        // Prepare fetched data
        val pictureUrl = items[position].imageUrl
        val name = items[position].name
        val nickname = items[position].nickname
        val breakingBadSeasonAppearance = stringFormatter.formatListString(
            context.getString(R.string.breaking_bad_seasons_appearance),
            context.getString(R.string.none),
            items[position].breakingBadSeasonAppearance
        )
        val betterCallSaulSeasonAppearance = stringFormatter.formatListString(
            context.getString(R.string.better_call_saul_seasons_appearance),
            context.getString(R.string.none),
            items[position].betterCallSaulSeasonAppearance
        )

        // Set the data within the view
        (holder as? ItemViewHolder)?.name?.text = name
        (holder as? ItemViewHolder)?.nickname?.text = nickname
        (holder as? ItemViewHolder)?.breakingBadSeasonAppearance?.text = breakingBadSeasonAppearance
        (holder as? ItemViewHolder)?.betterCallSaulSeasonAppearance?.text = betterCallSaulSeasonAppearance

        // Load the picture
        (holder as? ItemViewHolder)?.let {
            Glide.with(context)
                .load(pictureUrl)
                .into(it.picture)
        }

        // Set the onClickListener
        (holder as? ItemViewHolder)?.container?.setOnClickListener{
            val itemId = items[position].id
            clickListener(itemId)
        }
    }

    abstract class ViewHolder (view: View) : RecyclerView.ViewHolder(view)

    inner class ItemViewHolder (view: View) : ViewHolder(view) {
        val container = view.row_container
        val picture = view.imageView_picture
        val name = view.textView_name
        val nickname = view.textView_nickname
        val breakingBadSeasonAppearance = view.textView_breakingBadSeasonAppearance
        val betterCallSaulSeasonAppearance = view.textView_betterCallSaulSeasonAppearance
    }
}