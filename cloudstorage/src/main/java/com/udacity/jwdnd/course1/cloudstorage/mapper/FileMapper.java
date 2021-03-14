package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    @Insert("INSERT INTO files(filename, contenttype, filesize, userid, filedata) VALUES (#{filename},#{contenttype},#{filesize},#{userid},#{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    public Integer insertFile(File file);

    @Delete("DELETE files where fileid = #{fileId} and userid = #{userId}")
    public Integer deleteFile(Integer fileId, Integer userId);

    @Select("SELECT * FROM files where userid = #{userid}")
    public List<File> findAllFilesByUserId(Integer userId);

    @Select("SELECT * FROM files where fileid = #{fileId} and userid = #{userId}")
    public File findFileByFileId(Integer fileId, Integer userId);

    @Select("SELECT * FROM files where filename = #{filename} and userid = #{userId}")
    public File findFileByFilename(String filename, Integer userId);
}