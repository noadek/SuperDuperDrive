package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {
    @Select("SELECT * FROM FILES WHERE fileId = #{fileId}")
    File getById(Integer fileId);

    @Select("SELECT * FROM FILES WHERE filename = #{filename} AND userid = #{userId}")
    File getByFilename(Integer userId, String filename);

    @Select("SELECT * FROM FILES WHERE userid = #{userId}")
    List<File> getAllByUserId(Integer userId);

    @Insert("INSERT INTO FILES (fileId , filename, contenttype, filesize, userid, filedata) " +
            "VALUES(#{fileId}, #{filename}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insert(File file);

    @Delete("Delete FROM FILES WHERE fileId = #{fileId}")
    int delete(File file);
}
