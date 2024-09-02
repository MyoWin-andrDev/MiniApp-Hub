package it.ezzie.myapplist.todolist;

import android.os.Bundle;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import it.ezzie.myapplist.databinding.ActivityMainTodoBinding;
import it.ezzie.myapplist.databinding.DialogTodoBinding;

public class MainActivityTodo extends AppCompatActivity {

    private ActivityMainTodoBinding binding;
    private TodoAdapter todoAdapter;
    private List<Todo> todoList;
    private AlertDialog addTodoDialog;
    private DialogTodoBinding dialogBinding;
    private Todo todo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainTodoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initRecyclerView();
        initListener();
    }

    private void initRecyclerView() {
        todoList = new ArrayList<>(List.of(new Todo(5,"Learning Android Development",false)));
        todoAdapter = new TodoAdapter(todoList, new TodoListener() {
            @Override
            public void onEditTodo(Todo todo) {
                showAddTodoDialog(todo);
            }

            @Override
            public void onDeleteTodo(int id) {
                todoList.removeIf(todo -> todo.getId() == id);
                todoAdapter.setTodoList(todoList);
            }
        });
        binding.rvList.setAdapter(todoAdapter);
        binding.rvList.setLayoutManager(new LinearLayoutManager(this));

    }
    private void initListener(){
        initTodoDialog();
        binding.floatingBtn.setOnClickListener(v -> {
            showTodoDialog();
        });
    }

    private void initTodoDialog() {
        dialogBinding = DialogTodoBinding.inflate(getLayoutInflater());
        addTodoDialog = new AlertDialog.Builder(this)
                .setTitle("Add Todo Dialog")
                .setView(dialogBinding.getRoot())
                .setPositiveButton("ADD", (dialog, which) -> {
                    if (dialogBinding.etTitle.getTag() == null) {
                        //ADD
                        String title = dialogBinding.etTitle.getText().toString();
                        if (!title.isBlank()) {
                            todoList.add(new Todo(new Random().nextInt(), title, dialogBinding.cbCompleted.isChecked()));
                            todoAdapter.setTodoList(todoList);
                            dialog.cancel();
                        } else {
                            Toast.makeText(this, "Cannot Add!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        //UPDATE
                        int id = (int) dialogBinding.etTitle.getTag();
                        String title = dialogBinding.etTitle.getText().toString();
                        if (!title.isBlank()) {
                            var existingTodo = todoList.stream().filter(td -> td.getId() == id).findFirst().get();
                            var updateTodo = new Todo(id, title, dialogBinding.cbCompleted.isChecked());
                            if(!existingTodo.equals(updateTodo)){
                                var index = todoList.indexOf(existingTodo);
                                todoList.set(index,updateTodo);
                                todoAdapter.setTodoList(todoList);
                                dialog.cancel();
                            }
                            else{
                                Toast.makeText(this, "Same Todo", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(this, "Cannot Update!", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    dialog.cancel();
                })
                .create();
    }

    private void showTodoDialog(){
        addTodoDialog.show();
    }

    private void showAddTodoDialog(Todo todo) {
        dialogBinding.etTitle.setTag(todo.getId());
        dialogBinding.etTitle.setText(todo.getTitle());
        dialogBinding.cbCompleted.setChecked(todo.isCompleted());
        addTodoDialog.show();
    }
}