package nl.rocva.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import nl.rocva.project.model.Note;
import nl.rocva.project.model.User;
import nl.rocva.project.repository.AuthenticationRepository;
import nl.rocva.project.repository.NoteRepository;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private AuthenticationRepository userRepository;

    @GetMapping
    public List<Note> getNotes(Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName()).orElseThrow();
        return noteRepository.findByUserId(user.getId());
    }

    @PostMapping
    public Note createNote(@RequestBody Note note, Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName()).orElseThrow();
        note.setUser(user);
        return noteRepository.save(note);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNote(@PathVariable Long id, Authentication authentication) {
        Note note = noteRepository.findById(id).orElseThrow();
        User user = userRepository.findByUsername(authentication.getName()).orElseThrow();
        if (!note.getUser().getId().equals(user.getId())) {
            throw new org.springframework.web.server.ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        noteRepository.delete(note);
    }
}
