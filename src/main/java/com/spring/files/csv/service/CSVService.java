package com.spring.files.csv.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.spring.files.csv.helper.CSVHelper;
import com.spring.files.csv.model.ModelData;
import com.spring.files.csv.repository.ModelDataRepository;

@Service
public class CSVService {
  @Autowired
  ModelDataRepository repository;

  public void save(MultipartFile file) {
    try {
      List<ModelData> modelDataList = CSVHelper.csvToModelData(file.getInputStream());
      repository.saveAll(modelDataList);
    } catch (IOException e) {
      throw new RuntimeException("fail to store csv data: " + e.getMessage());
    }
  }

  public ByteArrayInputStream load() {
    List<ModelData> modelDataList = repository.findAll();

    ByteArrayInputStream in = CSVHelper.modelDataToCSV(modelDataList); 
    return in;
  }

  public List<ModelData> getAllModelData() {
    return repository.findAll();
  }
  
  public ModelData getModelDataById(Long id) {
	 
	 if(repository.existsById(id))  return repository.findById(id).get();
     else return null;
  }  
}
