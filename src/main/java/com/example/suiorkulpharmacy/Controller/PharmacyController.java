package com.example.suiorkulpharmacy.Controller;

import com.example.suiorkulpharmacy.Models.Post;
import com.example.suiorkulpharmacy.Repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class PharmacyController {

        @Autowired
        private PostRepository postRepository;
        @GetMapping("/")
        public String main(Model model) {
            Iterable<Post> posts = postRepository.findAll();
            model.addAttribute("posts", posts);
            return "main";
        }

        @GetMapping("/home")
        public String home(Model model) {
            Iterable<Post> posts = postRepository.findAll();
            model.addAttribute("posts", posts);
            return "home";
        }

        @GetMapping("/drug/add")
        public String drugAdd(Model model){
            return "drug-add";
        }

        @PostMapping("/drug/add")
        public String drugPostAdd(@RequestParam String title, @RequestParam String description, @RequestParam String imageUrl, @RequestParam float price, Model model){
            Post post = new Post(title, description, imageUrl, price );
            postRepository.save(post);
            return "redirect:/home";
        }

        @GetMapping("/drug-details/{id}")
        public String drugDetails(@PathVariable(value = "id") long id, Model model){
            if(!postRepository.existsById(id)){
                return "redirect:/home";
            }
            Optional<Post> post=postRepository.findById(id);
            ArrayList<Post> res = new ArrayList<>();
            post.ifPresent(res::add);
            model.addAttribute("post", res);
            return "drug-details";
        }

        @GetMapping("/drug/{id}/update")
        public String devEdit(@PathVariable(value = "id") long id, Model model){
            if(!postRepository.existsById(id)){
                return "redirect:/home";
            }
            Optional<Post> post=postRepository.findById(id);
            ArrayList<Post> res = new ArrayList<>();
            post.ifPresent(res::add);
            model.addAttribute("post", res);
            return "drug-update";
        }

        @PostMapping("/drug/{id}/update")
        public String devPostEdit(@PathVariable(value = "id") long id, @RequestParam String title, @RequestParam String description, @RequestParam String imageUrl, @RequestParam float price, Model model){
            Post post = postRepository.findById(id).orElseThrow(IllegalStateException::new);
            post.setTitle(title);
            post.setDescription(description);
            post.setImageUrl(imageUrl);
            post.setPrice(price);
            postRepository.save(post);
            return "redirect:/home";
        }

        @PostMapping("/drug/{id}/delete")
        public String devPostDelete(@PathVariable(value = "id") long id, Model model){
            Post post = postRepository.findById(id).orElseThrow(IllegalStateException::new);

            postRepository.delete(post);
            return "redirect:/home";
        }

        @GetMapping("/about")
        public String about(Model model) {
            return "about";
        }
}
