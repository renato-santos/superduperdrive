package com.udacity.jwdnd.course1.cloudstorage.mapper;


import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    @Insert("INSERT INTO notes(notetitle, notedescription, userid) VALUES (#{noteTitle},#{noteDescription},#{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    public Integer insertNote(Note note);

    @Update("UPDATE notes set notetitle = #{noteTitle}, notedescription = #{noteDescription} where noteid = #{noteId} and userid = #{userId}")
    public Integer updateNote(Note note);

    @Delete("DELETE notes where noteid = #{noteId} and userid = #{userId}")
    public Integer deleteNote(Integer noteId, Integer userId);

    @Select("SELECT * FROM notes where userid = #{userId}")
    public List<Note> findAllNotesByUserId(Integer userId);

}
