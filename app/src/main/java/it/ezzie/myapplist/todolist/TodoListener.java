package it.ezzie.myapplist.todolist;

public interface TodoListener {
    void onEditTodo(Todo todo);
    void onDeleteTodo(int id);
}
