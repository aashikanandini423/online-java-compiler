package com.codelearner.service.impl;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.codelearner.common.Role;
import com.mongodb.client.gridfs.GridFSFindIterable;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.apache.commons.collections.CollectionUtils;
import org.bson.BsonObjectId;
import org.bson.types.ObjectId;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.stereotype.Service;

import com.codelearner.domain.Problem;
import com.codelearner.request.FileSaveRequest;
import com.codelearner.request.UpdateRequest;
import com.codelearner.response.ProblemResponse;
import com.codelearner.service.MongoService;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

@Service
public class MongoServiceImpl implements MongoService {

    @Autowired
    private GridFsOperations gridOperations;

    @Autowired
    private Mapper dozerMapper;

    /**
     * Save the source file and metadata in Mongo using GridFsOperations
     *
     * @param fileSaveRequest
     */
    @Override
    public String saveCodeFile(FileSaveRequest fileSaveRequest, String userRole) {
        ObjectId codeId = null;
        DBObject metaData = new BasicDBObject();
        if (Role.Instructor.name().equals(userRole)) {
            metaData.put("questionId", "");
            metaData.put("description", fileSaveRequest.getProblemDescription());
            metaData.put("title", fileSaveRequest.getProblemTitle());
            metaData.put("participants", fileSaveRequest.getParticipants());
            metaData.put("createdBy", fileSaveRequest.getCreatedBy());
        } else if (Role.Student.name().equals(userRole)) {
            metaData.put("questionId", fileSaveRequest.getQuestionId());
            metaData.put("answeredBy", fileSaveRequest.getAnsweredBy());
        }
        metaData.put("language", fileSaveRequest.getLanguage());
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(fileSaveRequest.getCode());
            codeId = gridOperations.store(inputStream, fileSaveRequest.getCode().getName(), "application/octet-stream", metaData);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return codeId.toString();
    }

    


}
