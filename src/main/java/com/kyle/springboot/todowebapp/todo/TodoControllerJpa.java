package com.kyle.springboot.todowebapp.todo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import jakarta.servlet.jsp.jstl.sql.Result;
import jakarta.validation.Valid;

@Controller
@SessionAttributes("name")
public class TodoControllerJpa {
	
	private TodoService todoService;
	private TodoRepository todoRepository;
	
	public TodoControllerJpa(TodoService todoService, TodoRepository todoRepository) {
		super();
		this.todoService = todoService;
		this.todoRepository = todoRepository;
	}

	@RequestMapping("list-todos")
	public String listAllTodos(ModelMap model) {
	    String username = getLoggedInUsername();
	    
		List<Todo> todos = todoRepository.findByUsername(username);
		model.addAttribute("todos", todos);
		
		return "listTodos";
	}
	
	@GetMapping("add-todo")
    public String showNewTodoPage(ModelMap model) {
	    String username = getLoggedInUsername();
	    Todo todo = new Todo(0, username, "", LocalDate.now().plusYears(1), false);
	    model.put("todo", todo);
        
        return "todo";
    }
	
	@PostMapping("add-todo")
    public String addNewTodo(ModelMap model, @Valid Todo todo, BindingResult result) {
	    
	    if(result.hasErrors()) {
	        return "todo";
	    }
	    
	    String username = getLoggedInUsername();
        todoService.addTodo(username, todo.getDescription(), 
                             todo.getTargetDate(), false);
        return "redirect:list-todos";
    }
	
	@RequestMapping("delete-todo")
    public String deleteTodo(@RequestParam int id) {
	    // Delete todo
	    todoService.deleteById(id);
        return "redirect:list-todos";
    }
	
	@GetMapping("update-todo")
    public String showUpdateTodoPage(@RequestParam int id, ModelMap model) {
        Todo todo = todoService.findById(id);
        model.addAttribute("todo", todo);
        
        return "todo";
    }
	
	@PostMapping("update-todo")
    public String updateTodo(ModelMap model, @Valid Todo todo, BindingResult result) {
	    if(result.hasErrors()) {
	        return "todo";
	    }
	    
	    String username = getLoggedInUsername();
	    todo.setUsername(username);
        todoService.updateTodo(todo);
        return "redirect:list-todos";
    }

    private String getLoggedInUsername() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

}
