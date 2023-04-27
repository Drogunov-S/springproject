package ru.drogunov.springproject.controller;

import jdk.jfr.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.drogunov.springproject.model.entity.Task;
import ru.drogunov.springproject.services.TaskService;

@Controller
@RequestMapping
public class IndexController {
    private final TaskService taskService;

    @Autowired
    public IndexController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/")
    public String getIndex(@ModelAttribute("task") Task task,
//                           @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
//                           @RequestParam(value = "pageSize", defaultValue = "3") Integer pageSize,
                           @PageableDefault(size = 3) Pageable pageable,
                           Model model) {
        Page<Task> pageTask = taskService.getAll(pageable);
        model.addAttribute("page", pageTask);
        return "tasks";
    }

    @GetMapping("/crete")
    public String createNote() {
        return "create";
    }

    @DeleteMapping("/{id}")
    public String deleteNote(@PathVariable("id") Long id) {
        taskService.delete(id);
        return "redirect:/";
    }

    @PatchMapping("/{id}")
    @ResponseBody
    public String updateNote(@RequestBody Task task) {
        taskService.update(task);
        return "redirect:/";
    }

}
