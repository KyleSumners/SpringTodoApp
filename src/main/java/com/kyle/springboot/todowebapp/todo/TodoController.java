package com.kyle.springboot.todowebapp.todo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import jakarta.validation.Valid;

@Controller
@SessionAttributes("name")
public class TodoController {
	
	private TodoService todoService;
	
	public TodoController(TodoService todoService) {
		super();
		this.todoService = todoService;
	}

	@RequestMapping("list-todos")
	public String listAllTodos(ModelMap model) {
		List<Todo> todos = todoService.findByUsername("Kyle");
		model.addAttribute("todos", todos);
		
		return "listTodos";
	}
	
	@GetMapping("add-todo")
    public String showNewTodoPage(ModelMap model) {
	    String username = (String)model.get("name");
	    Todo todo = new Todo(0, username, "", LocalDate.now().plusYears(1), false);
	    model.put("todo", todo);
        
        return "todo";
    }
	
	@PostMapping("add-todo")
    public String addNewTodo(ModelMap model, @Valid Todo todo, BindingResult result) {
	    
	    if(result.hasErrors()) {
	        return "todo";
	    }
	    
	    String username = (String)model.get("name");
        todoService.addTodo(username, todo.getDescription(), 
                             LocalDate.now().plusYears(1), false);
        return "redirect:list-todos";
    }
	
	@RequestMapping("delete-todo")
    public String deleteTodo(@RequestParam int id) {
	    // Delete todo
	    todoService.deleteById(id);
        return "redirect:list-todos";
    }

}
