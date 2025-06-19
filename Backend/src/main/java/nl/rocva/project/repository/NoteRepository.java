package nl.rocva.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import nl.rocva.project.model.Note;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByUserId(Long userId);
}
