package info.meizi_retrofit.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.concurrent.CopyOnWriteArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import info.meizi_retrofit.R;
import info.meizi_retrofit.model.WrapGroup;
import info.meizi_retrofit.widget.RadioImageView;

/**
 * Created by Mr_Wrong on 15/12/1.
 */
public abstract class CollectedAdapter extends ArrayRecyclerAdapter<WrapGroup, CollectedAdapter.ViewHolder> {

    private final Context context;
    private final LayoutInflater inflater;

    public CollectedAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        setHasStableIds(true);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(R.layout.main_item, parent);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        WrapGroup bean = get(position);
        holder.imageView.setOriginalSize(bean.getGroup().getWidth(), bean.getGroup().getHeight());
        Picasso.with(context).load(bean.getGroup().getImageurl()).tag("1").//config(Bitmap.Config.RGB_565).
                transform(new CopyOnWriteArrayList<Transformation>()).
                into(holder.imageView);
        holder.title.setText(bean.getGroup().getTitle());
        ViewCompat.setTransitionName(holder.imageView, bean.getGroup().getUrl());
    }

    @Override
    public long getItemId(int position) {
        return get(position).getGroup().getUrl().hashCode();
    }

    protected abstract void onItemClick(View v, int position);

    public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.iv_main_item)
        public RadioImageView imageView;

        @Bind(R.id.text_title)
        public TextView title;

        public ViewHolder(@LayoutRes int resource, ViewGroup parent) {
            super(inflater.inflate(resource, parent, false));
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick(v, getAdapterPosition());
                }
            });
        }

    }

}

