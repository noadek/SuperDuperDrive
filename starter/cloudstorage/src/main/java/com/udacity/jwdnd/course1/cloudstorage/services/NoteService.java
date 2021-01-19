package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.springframework.stereotype.Service;

@Service
public class NoteService {
    private final NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public Note getNote(Integer noteId) {
        return this.noteMapper.getById(noteId);
    }

    public int createNote(Note note) {
        return this.noteMapper.insert(note);
    }

    public void updateNote(Note note) {
        this.noteMapper.update(note);
    }

    public void deleteNote(Note note) {
        this.noteMapper.delete(note);
    }
}
