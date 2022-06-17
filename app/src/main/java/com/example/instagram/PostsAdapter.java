package com.example.instagram;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;

import org.parceler.Parcels;

import java.util.Date;
import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {
    Context context;
    List<Post> posts;

    public PostsAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post);

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    // Clean all elements of the recycler
    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Post> list) {
        posts.addAll(list);
        notifyDataSetChanged();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView ivPost;
        TextView tvUsername;
        TextView tvDescription;
        TextView tvUsername2;
        TextView tvTimeStamp;
        ImageView ivLikes;
        ImageView ivLikesActive;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPost = itemView.findViewById(R.id.ivImage);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvUsername2 = itemView.findViewById(R.id.tvTitle2);
            tvTimeStamp = itemView.findViewById(R.id.tvTimeStamp);
            ivLikes = itemView.findViewById(R.id.ivLike);
            ivLikesActive = itemView.findViewById(R.id.ivLikeActive);
            itemView.setOnClickListener(this);
            ivLikes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ivLikes.setVisibility(View.INVISIBLE);
                    ivLikesActive.setVisibility(View.VISIBLE);

                }
            });
            ivLikesActive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ivLikes.setVisibility(View.VISIBLE);
                    ivLikesActive.setVisibility(View.INVISIBLE);
                }
            });

        }
        public void bind(Post post) {

            tvUsername.setText(post.getUser().getUsername());
            tvUsername2.setText(post.getUser().getUsername());
            tvDescription.setText(post.getDescription());
            tvTimeStamp.setText(calculateTimeAgo(post.getCreatedAt()));

            ParseFile image = post.getImage();
            if (image != null) {
                Glide.with(context).load(image.getUrl()).into(ivPost);
            }

        }


        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if(position!=RecyclerView.NO_POSITION){
                Post post = posts.get(position);
                Log.i("Clicked","clicked!");
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra(Post.class.getSimpleName(), Parcels.wrap(post));
                context.startActivity(intent);
            }
        }
    }



    public static String calculateTimeAgo(Date createdAt) {

        int SECOND_MILLIS = 1000;
        int MINUTE_MILLIS = 60 * SECOND_MILLIS;
        int HOUR_MILLIS = 60 * MINUTE_MILLIS;
        int DAY_MILLIS = 24 * HOUR_MILLIS;

        try {
            createdAt.getTime();
            long time = createdAt.getTime();
            long now = System.currentTimeMillis();

            final long diff = now - time;
            if (diff < MINUTE_MILLIS) {
                return "just now";
            } else if (diff < 2 * MINUTE_MILLIS) {
                return "a minute ago";
            } else if (diff < 50 * MINUTE_MILLIS) {
                return diff / MINUTE_MILLIS + " m";
            } else if (diff < 90 * MINUTE_MILLIS) {
                return "an hour ago";
            } else if (diff < 24 * HOUR_MILLIS) {
                return diff / HOUR_MILLIS + " h";
            } else if (diff < 48 * HOUR_MILLIS) {
                return "yesterday";
            } else {
                return diff / DAY_MILLIS + " d";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }
}
