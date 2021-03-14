package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.isNull;


@AllArgsConstructor
@Service
public class NoteService {

    private NoteMapper noteMapper;

    public void submitNote(Note note){
        if(isNull(note.getNoteId())) {
            noteMapper.insertNote(note);
        } else {
            noteMapper.updateNote(note);
        }
    }

    public void deleteNote(Integer noteId, Integer userId){
        noteMapper.deleteNote(noteId, userId);
    }

    public List<Note> getAllNotesByUserId(Integer userId){
        return noteMapper.findAllNotesByUserId(userId);
    }


}
