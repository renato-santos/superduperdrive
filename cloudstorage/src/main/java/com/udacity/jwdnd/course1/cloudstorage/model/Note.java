package com.udacity.jwdnd.course1.cloudstorage.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class Note {

    private Integer noteId;

    private String noteTitle;

    private String noteDescription;

    private Integer userId;

}
