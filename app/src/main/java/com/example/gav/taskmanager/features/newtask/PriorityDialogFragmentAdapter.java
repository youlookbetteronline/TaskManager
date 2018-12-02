package com.example.gav.taskmanager.features.newtask;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gav.taskmanager.R;

import java.util.List;

public class PriorityDialogFragmentAdapter extends RecyclerView.Adapter<PriorityDialogFragmentAdapter.PriorityDialogViewHolder>{
    private List<Priority> priorityList;
    private OnPriorityClickListener listener;
    private final Context context;

    public PriorityDialogFragmentAdapter(Context context, List<Priority> priorityList, OnPriorityClickListener listener) {
        this.context = context;
        this.priorityList = priorityList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PriorityDialogViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_priority_item, viewGroup, false);
        final PriorityDialogFragmentAdapter.PriorityDialogViewHolder priorityDialogViewHolder = new PriorityDialogFragmentAdapter.PriorityDialogViewHolder(view);
        priorityDialogViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (priorityDialogViewHolder.getAdapterPosition() != RecyclerView.NO_POSITION)
                    listener.onPriorityClick(priorityList.get(priorityDialogViewHolder.getAdapterPosition()));
            }
        });
        return priorityDialogViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PriorityDialogViewHolder priorityDialogViewHolder, int i) {
        if (i != RecyclerView.NO_POSITION)
            priorityDialogViewHolder.bind(priorityList.get(i));
    }

    @Override
    public int getItemCount() {
        return priorityList.size();
    }

    public class PriorityDialogViewHolder extends RecyclerView.ViewHolder {
        private View vMarker;
        private TextView tvTitle;

        public PriorityDialogViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            vMarker = itemView.findViewById(R.id.vMarker);
        }

        public void bind(Priority priority) {
            tvTitle.setText(priority.getTitle());
            vMarker.setBackgroundColor(priority.getColor());
        }
    }

    public interface OnPriorityClickListener {
        void onPriorityClick(Priority priority);
    }
}
