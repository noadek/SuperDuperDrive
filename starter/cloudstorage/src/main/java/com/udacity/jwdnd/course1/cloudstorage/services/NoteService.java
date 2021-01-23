package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    private final NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public Note getNote(Integer noteId) {
        return this.noteMapper.getById(noteId);
    }

    public List<Note> getUserNotes(Integer userId) { return this.noteMapper.getAllByUserId(userId); }

    public int createNote(Note note) {
        return this.noteMapper.insert(new Note(
                null,
                note.getNoteTitle(),
                note.getNoteDescription(),
                note.getUserId()
        ));
    }

    public int updateNote(Note note) {
        return this.noteMapper.update(note);
    }

    public int deleteNote(Note note) {
        return this.noteMapper.delete(note);
    }
}
