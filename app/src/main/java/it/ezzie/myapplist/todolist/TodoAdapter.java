package it.ezzie.myapplist.todolist;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.Objects;

import it.ezzie.myapplist.databinding.AdapterTodoBinding;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {

    private List<Todo> todoList;
    private TodoListener todoListener;

    TodoAdapter(List<Todo> todoList , TodoListener todoListener){
        this.todoList = todoList;
        this.todoListener = todoListener;
    }

    public void setTodoList(List<Todo> todoList){
        this.todoList = todoList;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterTodoBinding binding = AdapterTodoBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new TodoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        Todo todo = todoList.get(position);
        holder.binding.cbTodo.setText(todo.getTitle());
        holder.binding.cbTodo.setChecked(todo.isCompleted());
        holder.binding.delete.setOnClickListener(v -> {
            todoListener.onDeleteTodo(todo.getId());
        });
        holder.binding.edit.setOnClickListener(v -> {
            todoListener.onEditTodo(todo);
        });
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    class TodoViewHolder extends RecyclerView.ViewHolder{
    private AdapterTodoBinding binding;
        TodoViewHolder(AdapterTodoBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TodoAdapter that = (TodoAdapter) o;
        return Objects.equals(todoList, that.todoList) && Objects.equals(todoListener, that.todoListener);
    }

    @Override
    public int hashCode() {
        return Objects.hash(todoList, todoListener);
    }
}
