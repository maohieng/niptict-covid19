package com.jommobile.android.jomutils.ui.widget;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by MAO Hieng on 7/26/16.
 */
public class RecyclerConfiguration extends BaseObservable {

    public static class Builder {
        private boolean hasFixedSize = true;
        private RecyclerView.LayoutManager layoutManager;
        private RecyclerView.ItemDecoration itemDecoration;
        private RecyclerView.ItemAnimator itemAnimator;
        private RecyclerView.Adapter adapter;

        public Builder() {
        }

        public Builder setHasFixedSize(boolean hasFixedSize) {
            this.hasFixedSize = hasFixedSize;
            return this;
        }

        public Builder setAdapter(RecyclerView.Adapter adapter) {
            this.adapter = adapter;
            return this;
        }

        public Builder setLayoutManager(RecyclerView.LayoutManager layoutManager) {
            this.layoutManager = layoutManager;
            return this;
        }

        public Builder setItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
            this.itemDecoration = itemDecoration;
            return this;
        }

        public Builder setItemAnimator(RecyclerView.ItemAnimator itemAnimator) {
            this.itemAnimator = itemAnimator;
            return this;
        }

        public RecyclerConfiguration build() {
            return new RecyclerConfiguration(this);
        }
    }

    private boolean hasFixedSize = true;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.ItemDecoration itemDecoration;
    private RecyclerView.ItemAnimator itemAnimator;

    private RecyclerConfiguration(Builder b) {
        this.hasFixedSize = b.hasFixedSize;
        this.adapter = b.adapter;
        this.layoutManager = b.layoutManager;
        this.itemDecoration = b.itemDecoration;
        this.itemAnimator = b.itemAnimator;
    }

    @BindingAdapter("app:recyclerConfig")
    public static void configureRecyclerView(RecyclerView recyclerView, RecyclerConfiguration configuration) {
        recyclerView.setHasFixedSize(configuration.hasFixedSize);
        recyclerView.setAdapter(configuration.adapter);
        recyclerView.setLayoutManager(configuration.layoutManager);
        if (configuration.itemAnimator != null)
            recyclerView.setItemAnimator(configuration.itemAnimator);
        if (configuration.itemDecoration != null)
            recyclerView.addItemDecoration(configuration.itemDecoration);
    }

    @NonNull
    @Bindable
    public RecyclerView.LayoutManager getLayoutManager() {
        return layoutManager;
    }

    @NonNull
    @Bindable
    public RecyclerView.ItemDecoration getItemDecoration() {
        return itemDecoration;
    }

    @Nullable
    @Bindable
    public RecyclerView.ItemAnimator getItemAnimator() {
        return itemAnimator;
    }

    @Nullable
    @Bindable
    public RecyclerView.Adapter getAdapter() {
        return adapter;
    }

    @Bindable
    public boolean isHasFixedSize() {
        return hasFixedSize;
    }

//    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
////        this.layoutManager = layoutManager;
////        notifyPropertyChanged(BR.layoutManager); // generated from @Bindable
////    }
////
////    public void setItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
////        this.itemDecoration = itemDecoration;
////        notifyPropertyChanged(BR.itemDecoration); // generated from @Bindable
////    }
////
////    public void setItemAnimator(RecyclerView.ItemAnimator itemAnimator) {
////        this.itemAnimator = itemAnimator;
////        notifyPropertyChanged(BR.itemAnimator); // generated from @Bindable
////    }
////
////    public void setAdapter(RecyclerView.Adapter adapter) {
////        this.adapter = adapter;
////        notifyPropertyChanged(BR.adapter); // generated from @Bindable
////    }
////
////    public void setHasFixedSize(boolean hasFixedSize) {
////        this.hasFixedSize = hasFixedSize;
////        notifyPropertyChanged(BR.hasFixedSize);
////    }
}
