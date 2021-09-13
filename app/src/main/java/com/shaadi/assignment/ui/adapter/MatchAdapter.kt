package com.shaadi.assignment.ui.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shaadi.assignment.R
import com.shaadi.assignment.data.model.remote.MatchStatus
import com.shaadi.assignment.data.model.remote.ShaadiResultResponse
import com.shaadi.assignment.databinding.ShaadiMatchCardItemLayoutBinding

class MatchAdapter(private val context: Context, private val listener: MatchListener) : RecyclerView.Adapter<MatchAdapter.ViewHolder>() {

    private val inflater = LayoutInflater.from(context)
    private val matchList = ArrayList<ShaadiResultResponse.Match>()

    fun setMatches(list: ArrayList<ShaadiResultResponse.Match>) {
        matchList.clear()
        matchList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchAdapter.ViewHolder {
        val binding = ShaadiMatchCardItemLayoutBinding.inflate(inflater, parent, false)
        return ViewHolder(listener, binding)
    }

    override fun onBindViewHolder(holder: MatchAdapter.ViewHolder, position: Int) {
        val match = matchList[position]
        holder.bind(match)
    }

    override fun getItemCount(): Int {
        return matchList.size
    }

    inner class ViewHolder(private val listener: MatchListener, private val binding: ShaadiMatchCardItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(match: ShaadiResultResponse.Match) {
            Glide.with(binding.profileImage).load(match.picture.large).into(binding.profileImage)
            val name = match.name
            binding.nameTv.text = "${name.title} ${name.first} ${name.last}"
            binding.emailTv.text = match.email
            binding.phoneTv.text = match.phone
            binding.genderTv.text = "${match.dob.age}, ${match.gender}"
            val location = match.location
            binding.addressTv.text = "${location.street.name},${location.city}, ${location.state}, ${location.country}"

            when (match.matchStatus) {
                MatchStatus.ACCEPT -> {
                    binding.acceptLl.isVisible = false
                    binding.declineLl.isVisible = false
                    binding.status.isVisible = true

                    binding.status.text = binding.status.context.getString(R.string.text_accepted)
                    binding.status.setTextColor(Color.GREEN)
                }
                MatchStatus.DECLINE -> {
                    binding.acceptLl.isVisible = false
                    binding.declineLl.isVisible = false
                    binding.status.isVisible = true

                    binding.status.text = binding.status.context.getString(R.string.text_declined)
                    binding.status.setTextColor(Color.RED)
                }
                MatchStatus.NONE -> {
                    binding.acceptLl.isVisible = true
                    binding.declineLl.isVisible = true
                    binding.status.isVisible = false
                }
            }
            binding.acceptLl.setOnClickListener {
                listener.onAccept(match)
                val anim = AnimationUtils.loadAnimation(binding.root.context, R.anim.anim_left_to_right)
                startAnimation(MatchStatus.ACCEPT, match, anim)
            }
            binding.declineLl.setOnClickListener {
                listener.onDecline(match)
                val anim = AnimationUtils.loadAnimation(binding.root.context, R.anim.anim_right_to_left)
                startAnimation(MatchStatus.DECLINE, match, anim)
            }
        }

        private fun startAnimation(status: MatchStatus, match: ShaadiResultResponse.Match, anim: Animation) {
            binding.root.startAnimation(anim)
            anim.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {}

                override fun onAnimationEnd(animation: Animation?) {
                    match.matchStatus = status
                    notifyItemChanged(adapterPosition)
                    listener.scrollToNext(adapterPosition)
                }

                override fun onAnimationRepeat(animation: Animation?) {}
            })
        }
    }

    interface MatchListener {
        fun scrollToNext(position: Int)
        fun onAccept(match: ShaadiResultResponse.Match)
        fun onDecline(match: ShaadiResultResponse.Match)
    }
}
