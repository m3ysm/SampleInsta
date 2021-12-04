package com.example.sample.ui.usermedia

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sample.databinding.ItemUserMediaBinding

class UserMediaRecyclerViewAdapter(
    private val medias: ArrayList<Media>,
    private val userName: String,
    private val onItemClicked: ((mediaUrl: String) -> Unit)? = null
) :
    RecyclerView.Adapter<UserMediaRecyclerViewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding =
            ItemUserMediaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, onItemClicked)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(medias[position], userName)
    }

    override fun getItemCount() = medias.size

    class ViewHolder(
        binding: ItemUserMediaBinding,
        onItemClicked: ((mediaUrl: String) -> Unit)?,
    ) : RecyclerView.ViewHolder(binding.root) {
        private val topUserName = binding.textViewUserMediaItemTopUserName
        private val bottomUserName = binding.textViewUserMediaItemBottomUserName
        private val caption = binding.textViewUserMediaItemCaption
        private val image = binding.imageViewUserMediaItemImage
        private val more = binding.imageViewUserMediaItemMore
        private var mediaUrl = ""

        init {
            more.setOnClickListener {
                onItemClicked?.invoke(mediaUrl)
            }
        }

        fun bind(media: Media, userName: String) {
            topUserName.text = userName
            bottomUserName.text = userName
            caption.text = media.caption
            mediaUrl = media.mediaUrl
            Glide.with(image.context)
                .load(media.mediaUrl)
                .into(image)
        }
    }
}